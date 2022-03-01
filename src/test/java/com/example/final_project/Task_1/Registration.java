package com.example.final_project.Task_1;

import com.example.final_project.ResponseStatus;
import com.github.tsohr.JSONObject;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

public class Registration {
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
        registerUser(request);
    }

    public static void registerUser(RequestSpecification request){
        response = request.post("/Account/v1/User");
        int responseStatus = response.getStatusCode();

        Assert.assertEquals(responseStatus, 200 | 201);

//        System.out.println(responseStatus);
    }
}