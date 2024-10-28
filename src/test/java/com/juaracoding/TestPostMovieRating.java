package com.juaracoding;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestPostMovieRating {
    String Mytoken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkMjIzNTJjZjc4NDNhMDBiYTYzM2UyMDI2MWM0M2RkNSIsIm5iZiI6MTczMDAwMDE1OS4xOTQxNjEsInN1YiI6IjY3MTkwOGEzMWVhMzM5MjgyOTdjZDdlZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.8eNcZhR3bpIzuwQM75xxbgbH_YLsL8w9WgAf96MqAhc";

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://api.themoviedb.org/3";
    }

    @Test(priority = 5)
    public void testPostMovieRating() {
        String movieId = "1184918"; // ID film yang valid
        double rating = 8.5; //  nilai rating yang diinginkan

        JSONObject requestBody = new JSONObject();
        requestBody.put("value", rating);


        RequestSpecification request = RestAssured.given();
        request.contentType(ContentType.JSON);
        request.body(requestBody.toJSONString());
        request.header("Authorization", Mytoken);
        request.header("Accept", "application/json");

        Response response = request.post("/movie/" + movieId + "/rating");

        System.out.println("\n===================================");
        System.out.println("Test: Post Movie Rating");
        System.out.println("===================================");

        // Memeriksa apakah status code respons adalah 201 Created atau 200 OK
        int statusCode = response.statusCode();
        Assert.assertTrue(statusCode == 200 || statusCode == 201, "Status code tidak sesuai. Diharapkan 200 atau 201.");

        String message = response.jsonPath().getString("status_message");
        System.out.println("Status Code: " + statusCode);
        System.out.println("Response Message: " + message);

        // Verifikasi pesan berhasil memberikan rating
        String responseMessage = "The item/record was updated successfully.";
        Assert.assertEquals(message, responseMessage);
    }

    @Test(priority = 6)
    public void testPostMovieRatingNegative() {
        String movieId = "118491821"; // ID film yang valid
        double rating = 99.0; //  nilai rating yang diinginkan

        JSONObject requestBody = new JSONObject();
        requestBody.put("value", rating);

        RequestSpecification request = RestAssured.given();
        request.contentType(ContentType.JSON);
        request.body(requestBody.toJSONString());
        request.header("Authorization", Mytoken);
        request.header("Accept", "application/json");

        Response response = request.post("/movie/" + movieId + "/rating");

        System.out.println("\n===================================");
        System.out.println("Test: Post Movie Rating Negative");
        System.out.println(response.statusCode());
        System.out.println("===================================");

        int statusCode = response.statusCode();
        Assert.assertTrue(statusCode == 400);
    }
}
