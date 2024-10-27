# RestAPI Testing Project

## Overview

This project is focused on testing the API endpoints of The Movie Database (TMDB) using RestAssured, a popular Java library for API testing. The tests cover various functionalities such as retrieving movie details, checking now playing movies, and posting movie ratings. The goal is to ensure the API behaves as expected under both normal and erroneous conditions.

## API Endpoints Tested

1. **Get Now Playing Movies**
   - **Endpoint:** `/movie/now_playing`
   - **Description:** Retrieves a list of movies currently playing in theaters.
   - **Tests:**
     - Valid request to check for status code 200.
     - Invalid page number request to check for status code 400.

2. **Get Popular Movies**
   - **Endpoint:** `/movie/popular`
   - **Description:** Retrieves a list of popular movies.
   - **Tests:**
     - Valid request to check for status code 200.
     - Invalid page number request to check for status code 400.

3. **Get Movie Details**
   - **Endpoint:** `/movie/{movie_id}`
   - **Description:** Retrieves detailed information about a specific movie.
   - **Tests:**
     - Valid request for an existing movie ID to check for status code 200 and validate the response data.
     - Invalid request for a non-existing movie ID to check for status code 404.

4. **Post Movie Rating**
   - **Endpoint:** `/movie/{movie_id}/rating`
   - **Description:** Submits a rating for a specific movie.
   - **Tests:**
     - Valid request to check for status codes 200 and 201 based on the operation.
     - Invalid page number request to check for status code 400.

## Testing Framework

- **Java**: The tests are written in Java, utilizing the RestAssured library for API interaction.
- **TestNG**: The TestNG framework is used for structuring and running the tests.

## Setup Instructions

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/irgantaraa/RestAPI.git
   cd RestAPI
   ```

2. **Dependencies:**
   Ensure you have the following dependencies in your `pom.xml` if you're using Maven:

   ```xml
   <dependency>
       <groupId>io.rest-assured</groupId>
       <artifactId>rest-assured</artifactId>
       <version>4.4.0</version>
       <scope>test</scope>
   </dependency>
   <dependency>
       <groupId>org.testng</groupId>
       <artifactId>testng</artifactId>
       <version>7.4.0</version>
       <scope>test</scope>
   </dependency>
   ```

3. **Run Tests:**
   To run the tests, you can use an IDE like IntelliJ or Eclipse that supports Maven and TestNG. Alternatively, you can run the tests from the command line:
   ```bash
   mvn test
   ```

## Output

The test results will be displayed in the console, indicating the total number of tests run, passes, failures, and skips. For example:

```
===============================================
Default Suite
Total tests run: 8, Passes: 8, Failures: 0, Skips: 0
===============================================
```

## Contributing

If you'd like to contribute to this project, feel free to fork the repository and submit a pull request.

## License

This project is licensed under the MIT License.
