    package com.juaracoding;

    import io.restassured.RestAssured;
    import io.restassured.response.Response;
    import io.restassured.specification.RequestSpecification;
    import org.testng.Assert;
    import org.testng.annotations.BeforeClass;
    import org.testng.annotations.Test;

    import java.util.List;

    public class TestGetMovieNowPlaying {
        String Mytoken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkMjIzNTJjZjc4NDNhMDBiYTYzM2UyMDI2MWM0M2RkNSIsIm5iZiI6MTczMDAwMDE1OS4xOTQxNjEsInN1YiI6IjY3MTkwOGEzMWVhMzM5MjgyOTdjZDdlZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.8eNcZhR3bpIzuwQM75xxbgbH_YLsL8w9WgAf96MqAhc";

        @BeforeClass
        public void setUp() {
            RestAssured.baseURI = "https://api.themoviedb.org/3";
        }

        @Test(priority = 1)
        public void testGetMovieNowPlaying() {
            RequestSpecification request = RestAssured.given();
            request.queryParam("language", "en-US");
            request.queryParam("page", 1);
            request.header("Authorization", Mytoken);
            request.header("Accept", "application/json");

            Response response = request.get("/movie/now_playing");

            System.out.println("\n===================================");
            System.out.println("Test: Get Movie Now Playing");
            System.out.println("===================================");

            Assert.assertEquals(response.statusCode(), 200);

            // Menampilkan data halaman 1
            int page = response.jsonPath().getInt("page");
            System.out.println("Page: " + page);

            List<String> movieTitles = response.jsonPath().getList("results.title");
            System.out.println("Movies on Page 1:");
            for (String title : movieTitles) {
                System.out.println(title);
            }
            Assert.assertEquals(page, 1, "Page number is not as expected.");
            Assert.assertFalse(movieTitles.isEmpty(), "Movie list should not be empty.");
        }
        @Test(priority = 2)
        public void testGetMovieNowPlayingNegative(){
            RequestSpecification request = RestAssured.given();
            request.queryParam("language", "en-US");
            request.queryParam("page", 9999);
            request.header("Authorization", Mytoken);
            request.header("Accept", "application/json");

            Response response = request.get("/movie/now_playing");

            System.out.println("\n===================================");
            System.out.println("Test: Get Movie Now Playing Negative");
            System.out.println(response.statusCode());
            System.out.println("===================================");

            Assert.assertEquals(response.statusCode(), 400);
        }
    }





