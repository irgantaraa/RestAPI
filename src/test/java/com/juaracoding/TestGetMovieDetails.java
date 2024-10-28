package com.juaracoding;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestGetMovieDetails {
    String Mytoken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkMjIzNTJjZjc4NDNhMDBiYTYzM2UyMDI2MWM0M2RkNSIsIm5iZiI6MTczMDAwMDE1OS4xOTQxNjEsInN1YiI6IjY3MTkwOGEzMWVhMzM5MjgyOTdjZDdlZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.8eNcZhR3bpIzuwQM75xxbgbH_YLsL8w9WgAf96MqAhc";

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://api.themoviedb.org/3";
    }
    @Test(priority = 7)
    public void testGetMovieDetails() {
        String movieId = "1184918"; // Ganti dengan ID film yang valid

        RequestSpecification request = RestAssured.given();
        request.queryParam("language", "en-US");
        request.header("Authorization", Mytoken);
        request.header("Accept", "application/json");

        Response response = request.get("/movie/" + movieId);

        System.out.println("\n===================================");
        System.out.println("Test: Get Movie Details");
        System.out.println("===================================");

        Assert.assertEquals(response.statusCode(), 200, "Status code tidak sesuai. Diharapkan 200 OK.");

        String originalTitle = response.jsonPath().getString("original_title");
        String overview = response.jsonPath().getString("overview");
        double popularity = response.jsonPath().getDouble("popularity");
        String releaseDate = response.jsonPath().getString("release_date");
        double voteAverage = response.jsonPath().getDouble("vote_average");
        int voteCount = response.jsonPath().getInt("vote_count");

        // Memeriksa apakah semua informasi di atas ada
        Assert.assertNotNull(originalTitle, "Judul asli tidak ada.");
        Assert.assertNotNull(overview, "Overview tidak ada.");
        Assert.assertTrue(popularity >= 0, "Popularitas harus lebih besar atau sama dengan 0.");
        Assert.assertNotNull(releaseDate, "Tanggal rilis tidak ada.");
        Assert.assertTrue(voteAverage >= 0 && voteAverage <= 10, "Nilai rata-rata suara harus antara 0 dan 10.");
        Assert.assertTrue(voteCount >= 0, "Jumlah suara harus lebih besar atau sama dengan 0.");

        System.out.println("Title: " + originalTitle);
        System.out.println("Overview: " + overview);
        System.out.println("Popularity: " + popularity);
        System.out.println("Release Date: " + releaseDate);
        System.out.println("Vote Average: " + voteAverage);
        System.out.println("Vote Count: " + voteCount);
    }

    @Test(priority = 8)
    public void testGetMovieDetailsNegative() {
        String invalidMovieId = "9999999"; // ID film yang tidak valid

        RequestSpecification request = RestAssured.given();
        request.queryParam("language", "en-US");
        request.header("Authorization", Mytoken);
        request.header("Accept", "application/json");

        Response response = request.get("/movie/" + invalidMovieId);

        System.out.println("\n===================================");
        System.out.println("Test: Get Movie Details Negative");
        System.out.println("===================================");

        Assert.assertEquals(response.statusCode(), 404, "Status code tidak sesuai. Diharapkan 404 Not Found.");

        String message = response.jsonPath().getString("status_message");
        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Response Message: " + message);
    }
}
