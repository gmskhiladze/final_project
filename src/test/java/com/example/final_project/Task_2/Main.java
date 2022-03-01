package com.example.final_project.Task_2;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.example.final_project.SelenideElements;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

public class Main {

    public static Response response;

    public static int uiSize;
    public static int apiSize;

    public static String lastUiElTitle;
    public static String lastApiElTitle;
    public static String lastUiElTitleBeforeSearch;

    private static final String BASE_URL = "https://bookstore.toolsqa.com/BookStore/v1";

    SelenideElements elements = new SelenideElements();

    @BeforeClass
    public static void setUpBrowser() {
        Configuration.browser = "chrome";
        Configuration.startMaximized = true;
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeMethod
    public void setUp() {
        open("https://demoqa.com/books");
    }

    @Test
    public void findUiElemSize() throws InterruptedException {

        var uiElemBeforeSearch = $$("#app > div > div > div.row > div.col-12.mt-4.col-md-6 > div.books-wrapper > div.ReactTable.-striped.-highlight > div.rt-table > div.rt-tbody  .rt-tr:not(.-padRow)").stream().toList();

        lastUiElTitleBeforeSearch = String.valueOf(uiElemBeforeSearch.stream().toList().get(uiElemBeforeSearch.size() - 1).getText().contains("Understanding ECMAScript 6"));

        elements.searchBox.setValue("O'Reilly Media");
        Thread.sleep(1000);

        var uiElem = $$("#app > div > div > div.row > div.col-12.mt-4.col-md-6 > div.books-wrapper > div.ReactTable.-striped.-highlight > div.rt-table > div.rt-tbody  .rt-tr:not(.-padRow)").stream().toList();
        uiSize = uiElem.size();
//        filtered version
//        lastUiElTitle = String.valueOf(uiElem.stream().toList().get(uiElem.size() - 1).getText().contains("Understanding ECMAScript 6"));

//        System.out.println(uiSize);
//        System.out.println(lastUiElTitle);
//        System.out.println(lastUiElTitleBeforeSearch);
//        System.out.println(lastUiElTitle);
    }

    @Test(dependsOnMethods = "findUiElemSize")
    public void findApiElementSize() {

        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        response = request.get("/Books");
        BookList responseBody = response.getBody().as(BookList.class);

        var samePublisher = responseBody.books.stream().filter(b -> b.publisher.equals("O'Reilly Media"));
        apiSize = samePublisher.toList().size();

        lastApiElTitle = String.valueOf(responseBody.books.get(apiSize - 1).title.contains("Understanding ECMAScript 6"));

//        System.out.println(apiSize);
//        System.out.println(lastApiElTitle);
    }

    @Test(dependsOnMethods = "findApiElementSize")
    public void validate() {

//        check if api and ui element same publisher amount are same
        Assert.assertEquals(uiSize, apiSize);

//        check if last ui and api elements are same
//        Assert.assertEquals(lastUiElTitle, lastApiElTitle);

//        check if last ui and api elements are same without filter
//        should not pass, 1 is true 2 is false
        Assert.assertEquals(lastUiElTitleBeforeSearch, lastApiElTitle);

    }
}
