package steps.hooks;

import io.cucumber.java.Before;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;

public class BackendHooks {

    @Before
    public static void setup() {

        RestAssured.baseURI = "https://restful-booker.herokuapp.com/";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .addHeader("Accept", "application/json")
                .build();
    }
}