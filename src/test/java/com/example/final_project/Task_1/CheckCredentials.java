package com.example.final_project.Task_1;

import com.example.final_project.ResponseStatus;
import com.github.tsohr.JSONObject;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

public class CheckCredentials {

    public static Response response;

    public static String requestParams(Main.User User){
        JSONObject reqParams = new JSONObject();
        reqParams.put("userName", User.UserName);
        reqParams.put("password", User.UserPassword);
        return reqParams.toString();
    }

    public static void setUpRestAssured(String BASE_URL, Main.User User) {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestParams(User));
        validate(request);
    }

    public static void validate(RequestSpecification request){
        response = request.post("/Authorized");
        ResponseStatus responseBody = response.getBody().as(ResponseStatus.class);
        Assert.assertEquals(responseBody.message, "User not found!");

//        System.out.println(response.getBody().asPrettyString());
//        System.out.println(responseBody.code);
//        System.out.println(responseBody.message);
    }
}