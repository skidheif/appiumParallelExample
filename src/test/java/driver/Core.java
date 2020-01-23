package driver;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;

import java.io.File;
import java.net.URL;

public class Core {

    private static final String PLATFORM_IOS = "ios";
    private static final String PLATFORM_Android = "android";
    private static final String URL = "http://192.168.50.246:4444/wd/hub";
    protected WebDriver driver;

    public String getPlatformVar() {
        return System.getenv("platform");
    }

    private boolean isPlatform(String my_platform) {
        String platform = this.getPlatformVar();
        return my_platform.equals(platform);
    }

    public boolean isAndroid() {
        return isPlatform(PLATFORM_Android);
    }

    public boolean isIOS() {
        return isPlatform(PLATFORM_IOS);
    }


    @BeforeMethod(alwaysRun = true)
    @Parameters({"deviceName", "platformVersion", "udid"})
    public void setup(String deviceName, String platformVersion, String udid) throws Exception {

        if (this.isAndroid()) {
            DesiredCapabilities capabilities = new DesiredCapabilities();

            capabilities.setCapability("deviceName", deviceName);
            System.out.println(deviceName);
            capabilities.setCapability("platformVersion", platformVersion);
            System.out.println(platformVersion);
            capabilities.setCapability("udid", udid);
            System.out.println(udid);
            capabilities.setCapability("automationName", "Appium");
            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("appPackage", "com.allgoritm.youla");
            capabilities.setCapability("appActivity", "com.allgoritm.youla.AppInitActivity");
            String apk = "apks/com.allgoritm.youla.apk";
            File f = new File(".");
            String[] list = f.list();
            for (String file : list) {
                if (file.contains("com") && file.contains("allgoritm") && file.contains("youla") && file.contains(".apk")) {
                    apk = file;
                    break;
                }
            }
            String path = new File(apk).getAbsolutePath();
            capabilities.setCapability("app", path);
            System.out.println(path);
            capabilities.setCapability("autoGrantPermissions", true);
            capabilities.setCapability("fullReset", "true"); //вкл если нужно чтобы apk устанавливался с 0 каждый раз, http://appium.io/docs/en/writing-running-appium/other/reset-strategies/index.html
            capabilities.setCapability("clearSystemFiles", "true");

            System.out.println(new File(".").getAbsolutePath());
            driver = new AndroidDriver(new URL(URL), capabilities);
        } else if (this.isIOS()) {
            DesiredCapabilities capabilities = new DesiredCapabilities();

            capabilities.setCapability("platformName", "iOS");
            capabilities.setCapability("deviceName", deviceName);
            System.out.println(deviceName);
            capabilities.setCapability("platformVersion", platformVersion);
            System.out.println(platformVersion);
            capabilities.setCapability("udid", udid);
            System.out.println(udid);
            capabilities.setCapability("automationName", "XCUITest");
            capabilities.setCapability("xcodeOrgId", "1234567890");
            capabilities.setCapability("xcodeSigningId", "iPhone Developer");
            capabilities.setCapability("newCommandTimeout", 60);
            String app = "apks/123.app";
            File f = new File(".");
            String[] list = f.list();
            for (String file : list) {
                if (file.contains("123") && file.contains(".app")) {
                    app = file;
                    break;
                }
            }
            String path = new File(app).getAbsolutePath();
            capabilities.setCapability("app", path);
            System.out.println(path);
            capabilities.setCapability("fullReset", "false");
            driver = new IOSDriver(new URL(URL), capabilities);
            ((IOSDriver) driver).unlockDevice();
        }
    }

    @AfterMethod
    public synchronized void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}