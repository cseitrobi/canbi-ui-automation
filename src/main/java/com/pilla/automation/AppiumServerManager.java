package com.pilla.automation;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

/**
 * Singleton class to manage Appium server instances.
 * This class ensures that only one Appium server instance is created per runtime.
 */
@Slf4j
public class AppiumServerManager {

    public static final String LOCAL = "local";
    public static final String BROWSER_STACK = "browser-stack";

    private static AppiumServerManager instance;
    private static final List<String> DEFAULT_LOCAL_SERVER_URLS = List.of(
            "http://127.0.0.1:4723",
            "http://127.0.0.1:4723/wd/hub"
    );
    private AppiumDriverLocalService appiumDriverLocalService;
    private boolean managesLocalService;

    // Private constructor to prevent instantiation
    private AppiumServerManager() {
        // Initialize if needed
    }

    /**
     * Get the singleton instance of AppiumServerManager
     *
     * @return The singleton instance
     */
    public static synchronized AppiumServerManager getInstance() {
        if (instance == null) {
            instance = new AppiumServerManager();
        }
        return instance;
    }

    /**
     * Get the server URL for a specific runtime
     *
     * @param runtimeType The runtime type (local or browser-stack)
     * @return The server URL
     */
    public synchronized URL getServerUrl(String runtimeType) {
        if (LOCAL.equals(runtimeType)) {
            URL configuredOrRunningServer = resolveConfiguredOrRunningLocalServer();
            if (configuredOrRunningServer != null) {
                return configuredOrRunningServer;
            }
            if (appiumDriverLocalService == null || !appiumDriverLocalService.isRunning()) {
                appiumDriverLocalService = startAppiumService();
            }
            return appiumDriverLocalService.getUrl();
        }
        return createBrowserStackUrl();
    }

    /**
     * Stop a specific Appium service
     */
    public synchronized void stopService() {
        if (managesLocalService && appiumDriverLocalService != null && appiumDriverLocalService.isRunning()) {
            appiumDriverLocalService.stop();
        }
    }

    /**
     * Start a new Appium service
     *
     * @return The started Appium service
     */
    private AppiumDriverLocalService startAppiumService() {
        AppiumServiceBuilder builder = new AppiumServiceBuilder()
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withTimeout(Duration.ofMinutes(60));

        configureExplicitAppiumPaths(builder);

        AppiumDriverLocalService service = builder.build();
        log.info("Starting appium service...");
        service.start();

        if (!service.isRunning()) {
            throw new IllegalStateException("Appium server failed to start!");
        }
        managesLocalService = true;
        log.info("Appium server started at: " + service.getUrl());
        return service;
    }

    private URL resolveConfiguredOrRunningLocalServer() {
        String configuredServerUrl = firstNonBlank(
                System.getProperty("appium.server.url"),
                System.getenv("APPIUM_SERVER_URL")
        );
        if (configuredServerUrl != null) {
            URL serverUrl = toUrl(configuredServerUrl);
            if (isServerReachable(serverUrl)) {
                log.info("Using configured Appium server: {}", serverUrl);
                managesLocalService = false;
                return serverUrl;
            }
            throw new IllegalStateException("Configured Appium server is not reachable: " + serverUrl);
        }

        for (String candidate : DEFAULT_LOCAL_SERVER_URLS) {
            URL serverUrl = toUrl(candidate);
            if (isServerReachable(serverUrl)) {
                log.info("Using already running Appium server: {}", serverUrl);
                managesLocalService = false;
                return serverUrl;
            }
        }
        return null;
    }

    private void configureExplicitAppiumPaths(AppiumServiceBuilder builder) {
        String nodePath = firstNonBlank(
                System.getProperty("appium.node.path"),
                System.getenv("APPIUM_NODE_BINARY"),
                System.getenv("NODE_BINARY"),
                findExecutableOnPath("node")
        );
        if (nodePath != null) {
            builder.usingDriverExecutable(new File(nodePath));
        }

        String appiumJsPath = firstNonBlank(
                System.getProperty("appium.js.path"),
                System.getenv("APPIUM_BINARY"),
                System.getenv("APPIUM_JS_PATH")
        );
        if (appiumJsPath != null) {
            builder.withAppiumJS(new File(appiumJsPath));
        }
    }

    private String findExecutableOnPath(String executableName) {
        String path = System.getenv("PATH");
        if (path == null || path.isBlank()) {
            return null;
        }

        for (String directory : path.split(File.pathSeparator)) {
            File candidate = new File(directory, executableName);
            if (candidate.isFile() && candidate.canExecute()) {
                return candidate.getAbsolutePath();
            }
        }
        return null;
    }

    private boolean isServerReachable(URL serverUrl) {
        try {
            URL statusUrl = URI.create(serverUrl.toString().replaceAll("/$", "") + "/status").toURL();
            HttpURLConnection connection = (HttpURLConnection) statusUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(2000);
            connection.setReadTimeout(2000);
            int statusCode = connection.getResponseCode();
            return statusCode >= 200 && statusCode < 500;
        } catch (IOException e) {
            return false;
        }
    }

    private URL toUrl(String value) {
        try {
            return URI.create(value).toURL();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid Appium server URL: " + value, e);
        }
    }

    private String firstNonBlank(String... values) {
        return Arrays.stream(values)
                .filter(value -> value != null && !value.isBlank())
                .findFirst()
                .orElse(null);
    }

    /**
     * Create a BrowserStack URL
     *
     * @return The BrowserStack URL
     */
    private URL createBrowserStackUrl() {
        String username = System.getenv("BROWSERSTACK_USERNAME");
        String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
        try {
            return URI.create(String.format(
                    "https://%s:%s@hub.browserstack.com/wd/hub",
                    username,
                    accessKey
            )).toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
