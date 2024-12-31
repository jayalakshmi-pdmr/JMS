package com.qa.jms.factory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class PlaywrightFactory {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext browserContext;
    private Page page;
    private Properties prop;
    
    private String username = "//*[@type='text']";
    private String password = "//*[@type='password']";

    // Constructor to initialize Playwright
    public PlaywrightFactory() {
        try {
            this.playwright = Playwright.create();  // Initialize Playwright instance
            System.out.println("Playwright initialized successfully.");
        } catch (Exception e) {
            System.out.println("Failed to initialize Playwright: " + e.getMessage());
        }
    }

    // Initialize browser with properties and return the page
    public Page initBrowser(Properties prop) {
        String browserName = prop.getProperty("browser").trim().toLowerCase();
        System.out.println("Browser name is: " + browserName);

        // Check if Playwright is initialized properly
        if (playwright == null) {
            System.out.println("Playwright is not initialized.");
            return null;
        }
        
        
       
        
        // Initialize the browser based on the browserName
        switch (browserName) {
            case "chromium":
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            case "firefox":
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            case "safari":
                browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            case "chrome":
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));
                break;
            default:
                System.out.println("Please pass a valid browser type in config.");
                return null;
        }

        if (browser == null) {
            System.out.println("Failed to launch the browser.");
            return null;
        }

        // Initialize browser context and page
        browserContext = browser.newContext();
        page = browserContext.newPage();

        // Evaluate the screen width and height
        int screenWidth = (int) page.evaluate("() => window.screen.width");
        int screenHeight = (int) page.evaluate("() => window.screen.height");
        page.setViewportSize(screenWidth, screenHeight);	

        // Navigate to the URL and perform login actions
        page.navigate(prop.getProperty("loginUrl"));
        page.locator(username).fill(prop.getProperty("username"));
        page.locator(password).fill(prop.getProperty("password"));
        page.keyboard().press("Enter");

        return page;
    }

    // Load properties from the config file
    public Properties initProp() throws IOException {
    	InputStream input = getClass().getClassLoader().getResourceAsStream("config/config.properties");
        if (input == null) {
            throw new IOException("Unable to find config/config.properties in resources folder.");
        }
        prop = new Properties();
        prop.load(input);
        return prop;
    }
}
