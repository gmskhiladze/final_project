package com.example.final_project.Task_1;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.example.final_project.SelenideElements;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.*;

import static com.codeborne.selenide.Selenide.*;

public class Main {

    private static final String BASE_URL = "https://bookstore.toolsqa.com/Account/v1";

    SelenideElements elements = new SelenideElements();

    User User;

    class User {
        String UserName;
        String UserPassword;

        public User(String userName, String userPass) {
            UserName = userName;
            UserPassword = userPass;
        }
    }

    @DataProvider(name = "data-provider")
    public Object[][] dpMethod() {
        return new Object[][]{
                {"Giorgi", "Mskhiladze123@"},
        };
    }

    @BeforeClass
    public static void setUpBrowser() {
        Configuration.browser = "chrome";
        Configuration.startMaximized = true;
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeMethod
    public void setUp() {
        open("https://demoqa.com/login");
    }

    @Test(dataProvider = "data-provider", description="register user")
    public void userRegistration(String userName, String userPass) throws InterruptedException {

        User = new User(userName, userPass);

        var url = "https://bookstore.toolsqa.com";

        RestAssured.baseURI = url;

        Registration startRegistration = new Registration();
        startRegistration.setUpRestAssured(url, User);

    }

    @Test(dataProvider = "data-provider", dependsOnMethods = "userRegistration", description="login -> delete account -> accept account delete -> check alert")
    @Step("pass username: {0} and password: {1} to login")
    public void logInUser(String userName, String userPass) throws InterruptedException {

        User = new User(userName, userPass);

        elements.username.setValue(User.UserName);
        elements.password.setValue(User.UserPassword);
        elements.loginBtn.click();
        elements.deleteAccount.click();
        elements.acceptDeleteAccount.click();

        Assert.assertEquals(switchTo().alert().getText(), "User Deleted.");
        switchTo().alert().accept();

//        var alertText = switchTo().alert().getText();
//        switchTo().alert().accept();
//        var check = alertText.equals("User Deleted.");
//        System.out.println(alertText);
//        System.out.println(check);
    }

    @Test(dataProvider = "data-provider", dependsOnMethods = "logInUser", description = "validate if user is deleted")
    @Step("pass username: {0} and password: {1} to login, result should be a string")
    public void validatePopUpMessage(String userName, String userPass) throws InterruptedException {

        User = new User(userName, userPass);

        elements.username.setValue(User.UserName);
        elements.password.setValue(User.UserPassword);
        elements.loginBtn.click();

        Assert.assertEquals(elements.errorText.getText(), "Invalid username or password!");

//        var el = elements.errorText.getText();
//        var check = el.equals("Invalid username or password!");
//        System.out.println(el);
//        System.out.println(check);
    }

    @Test(dataProvider = "data-provider", dependsOnMethods = "validatePopUpMessage")
    public void validateCredentials(String userName, String userPass) throws InterruptedException {

        User = new User(userName, userPass);

        RestAssured.baseURI = BASE_URL;

        CheckCredentials checkCredentials = new CheckCredentials();
        checkCredentials.setUpRestAssured(BASE_URL, User);

//        RestAssured.baseURI = BASE_URL;
//        RequestSpecification request = RestAssured.given();
//
//        JSONObject requestParams = new JSONObject();
//        requestParams.put("userName", User.UserName);
//        requestParams.put("password", User.UserPassword);
//
////        request.header("Content-Type", "application/json");
//        request.body(requestParams.toString());
//        Response response = request.post("/Authorized");

//        ResponseStatus responseBody = response.getBody().as(ResponseStatus.class);

//        System.out.println(response.getBody().asPrettyString());
//        System.out.println(responseBody.code);
//        System.out.println(responseBody.message);
//
//        Assert.assertEquals(responseBody.message, "User not found!");
    }

//    username: Giorgi
//    password: Mskhiladze123@

}
