package com.example.final_project;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class SelenideElements {

    public SelenideElement username = $("#userName");
    public SelenideElement password = $("#password");

    public SelenideElement loginBtn = $("#login");

    public SelenideElement deleteAccount= $x("/html/body/div[2]/div/div/div[2]/div[2]/div[1]/div[3]/div[2]/button");
    public SelenideElement acceptDeleteAccount= $x("/html/body/div[4]/div/div/div[3]/button[1]");

    public SelenideElement errorText= $x("/html/body/div[2]/div/div/div[2]/div[2]/div[1]/form/div[5]/div/p");

    public SelenideElement searchBox= $("#searchBox");

}