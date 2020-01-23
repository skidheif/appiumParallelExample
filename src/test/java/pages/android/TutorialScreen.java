package pages.android;

import io.appium.java_client.android.AndroidDriver;
import stepdefs.MainMethods;

public class TutorialScreen extends MainMethods {

    public TutorialScreen(AndroidDriver driver) {
        super(driver);
    }

    //below is lying xpath

    private static final String
            ok_button = "id:com.allgoritm.youla:id/ok",
            email_field = "xpath://*[@resource-id = 'field_email']",
            pass_field = "xpath://*[@resource-id = 'field_password']",
            sighIn_button = "xpath://android.widget.Button[@text = 'Sign in']",
            requestLocation_button = "id:com.allgoritm.youla:id/requestLocationButton",
            requestLocation_button_whileAppIsOpen = "id:com.android.permissioncontroller:id/permission_allow_foreground_only_button",
            shading_view = "id:com.allgoritm.youla:id/shading_view",
            avatar_button = "id:com.allgoritm.youla:id/avatar_iv",
            menu_button = "id:com.allgoritm.youla:id/menu_profile",
            setting_row = "id:com.allgoritm.youla:id/settings_row",
            username = "id:com.allgoritm.youla:id/user_name_tv";

    public void clickOkButton() {
        waitForElementAndClick(
                ok_button,
                "Couldn't find ok button",
                5);

    }

    public void fillEmailField() {
        waitForElementAndClick(
                email_field,
                "Couldn't find email_field",
                5);
        waitForElementAndSendKeys(
                email_field,
                "1234",
                "Couldn't fill email_field",
                5);
    }

    public void fillPasswordField() {
        waitForElementAndClick(
                pass_field,
                "Couldn't find pass_field",
                5);
        waitForElementAndSendKeys(
                pass_field,
                "12345",
                "Couldn't fill pass_field",
                5);
    }

    public void clickSignInButton() {
        waitForElementAndClick(
                sighIn_button,
                "Couldn't find sign in button",
                5);
    }
}