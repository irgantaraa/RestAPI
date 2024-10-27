    package com.juaracoding;

    import io.restassured.RestAssured;
    import io.restassured.http.ContentType;
    import io.restassured.response.Response;
    import io.restassured.specification.RequestSpecification;
    import org.json.simple.JSONObject;
    import org.testng.Assert;
    import org.testng.annotations.BeforeClass;
    import org.testng.annotations.Test;

    import java.util.List;

    public class TestTMDB {
        String Mytoken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkMjIzNTJjZjc4NDNhMDBiYTYzM2UyMDI2MWM0M2RkNSIsIm5iZiI6MTczMDAwMDE1OS4xOTQxNjEsInN1YiI6IjY3MTkwOGEzMWVhMzM5MjgyOTdjZDdlZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.8eNcZhR3bpIzuwQM75xxbgbH_YLsL8w9WgAf96MqAhc";

        @BeforeClass
        public void setUp() {
            RestAssured.baseURI = "https://api.themoviedb.org/3";
        }

        @Test
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
        @Test
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


        @Test
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

        @Test
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

        @Test
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
        @Test
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

        @Test
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

        @Test
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

            // Memeriksa apakah status code respons adalah 404 Not Found
            Assert.assertEquals(response.statusCode(), 404, "Status code tidak sesuai. Diharapkan 404 Not Found.");

            String message = response.jsonPath().getString("status_message");
            System.out.println("Status Code: " + response.statusCode());
            System.out.println("Response Message: " + message);
        }

    }





