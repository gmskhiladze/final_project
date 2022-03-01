package com.example.final_project;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonIgnoreProperties;
import java.math.BigInteger;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookResponse {
    public String code;
    public String message;
    public BigInteger isbn;
    public String title;
    public String subTitle;
    public String author;
    public String publish_date;
    public String publisher;
    public int pages;
    public String description;
    public String website;
}
