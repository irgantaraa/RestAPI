package com.juaracoding;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class TestGetMoviePopular {
    String Mytoken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkMjIzNTJjZjc4NDNhMDBiYTYzM2UyMDI2MWM0M2RkNSIsIm5iZiI6MTczMDAwMDE1OS4xOTQxNjEsInN1YiI6IjY3MTkwOGEzMWVhMzM5MjgyOTdjZDdlZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.8eNcZhR3bpIzuwQM75xxbgbH_YLsL8w9WgAf96MqAhc";

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://api.themoviedb.org/3";
    }

    @Test(priority = 3)
    public void testGetMoviePopular() {
        RequestSpecification request = RestAssured.given();
        request.queryParam("language", "en-US");
        request.queryParam("page", 1);
        request.header("Authorization", Mytoken);
        request.header("Accept", "application/json");
        Response response = request.get("/movie/popular");

        System.out.println("\n===================================");
        System.out.println("Test: Get Movie Popular");
        System.out.println("===================================");

        Assert.assertEquals(response.statusCode(), 200, "Status code is not 200");
        List<String> movieTitles = response.jsonPath().getList("results.title");
        Assert.assertFalse(movieTitles.isEmpty(), "Daftar film populer tidak boleh kosong");

        System.out.println("Popular Movies on Page 1:");
        for (String title : movieTitles) {
            System.out.println(title);
            Assert.assertNotNull(title, "Setiap film harus memiliki title");
        }
    }

    @Test(priority = 4)
    public void testGetMoviePopularNegative() {
        RequestSpecification request = RestAssured.given();
        request.queryParam("language", "en-US");
        request.queryParam("page", 9999);
        request.header("Authorization", Mytoken);
        request.header("Accept", "application/json");
        Response response = request.get("/movie/popular");

        System.out.println("\n===================================");
        System.out.println("Test: Get Movie Popular Negative");
        System.out.println(response.statusCode());
        System.out.println("===================================");

        Assert.assertEquals(response.statusCode(), 400);
    }
}
