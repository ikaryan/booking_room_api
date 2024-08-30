import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BookingRoomTest {

    Logger logger = Logger.getLogger(BookingRoomTest.class.getName());
    String requestBody;

    @Test
    public void deserialization() throws JsonProcessingException {

        final ObjectMapper mapper = new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT);

        var jsonString = """
                 {
                     "firstname": "Karlox",
                     "lastname": "Allen",
                     "totalprice": 111,
                     "depositpaid": false,
                     "bookingdates": {
                         "checkin": "2025-01-01",
                         "checkout": "2025-01-01"
                     },
                     "additionalneeds": "super bowls & Breakfast"
                 }
                """;

        var deserialized = mapper.readValue(jsonString, UserBookingRequest.class);
        logger.info("" + deserialized);
    }

    @Test
    public void serialization() throws JsonProcessingException {

        final ObjectMapper mapper = new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT);

        RequestBody.userBookingRequest.setFirstname("pepito");
        RequestBody.userBookingRequest.setLastname("Perez");
        RequestBody.userBookingRequest.setTotalprice(120);
        RequestBody.userBookingRequest.setDepositpaid(true);
        RequestBody.bookDates.setCheckin("2025-01-01");
        RequestBody.bookDates.setCheckout("2025-01-01");
        RequestBody.userBookingRequest.setAdditionalneeds("super bowls & Breakfast");

        var serialized = mapper.writeValueAsString(RequestBody.userBookingRequest);
        logger.info(serialized);
    }

    @BeforeAll
    public static void setup() {

        RestAssured.baseURI = "https://restful-booker.herokuapp.com/";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .addHeader("Accept", "application/json")
                .build();

    }

    @Test
    public void createAuthToken() { //200 Ok

        given()
                .body(RequestBody.createAuthToken)
                .post("auth")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("token", notNullValue());
    }

    @Test
    public void createAuthTokenWithInvalidPath() {//404 No found

        requestBody = """
                {
                    "username" : "admin",
                    "password" : "password123"
                }
                """;
        given()
                .body(requestBody)
                .post("auth1")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void getAllBookingIds() {//200 OK

        given()
                .get("booking")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("bookingid", notNullValue());
    }

    @Test
    public void getSpecificBooking() {//200 OK

        String firstname = given()
                .get("booking/1")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().jsonPath().getString("firstname");
        assertThat(firstname, equalToIgnoringCase("Susan"));
    }

    @Test
    public void getSpecificBookingButInvalidAssert() {//200 OK but fail

        String firstname = given()
                .get("booking/1")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().jsonPath().getString("firstname");
        assertThat(firstname, equalToIgnoringCase("sally"));
    }

    @Test
    public void createBookingWrongEndpoint() {//404 Not Found

        given()
                .body(RequestBody.createBookingRequest())
                .post("booking1")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void createBookingWithWrongData() {//500 internal server error - lastname1

        given()
                .body(RequestBody.createBookingRequestWithWrongField())
                .post("booking")
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void createBooking() {//200 OK

       given()
                .body(RequestBody.createBookingRequest())
                .post("booking")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("bookingid", greaterThanOrEqualTo(1));

    }

    @Test
    public void deleteNotExistingBooking() {//405 Method not allowed

        given()
                .delete("booking/3969")
                .then()
                .statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
    }

    @Test
    public void deleteBooking() {//200 Ok

        given()
                .delete("booking/1")
                .then()
                .statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    public void updateRegister() {

        given()
                .when()
                .body(RequestBody.updateRegister())
                .put("booking/187")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON);
    }

    @Test
    public void partialUpdateRegister() {

        given()
                .body(RequestBody.partialUpdateRegister())
                .patch("booking/861");
    }

    @Test
    public void partialUpdateRegisterNotBooking() {//404 Not found

        given()
                .body(RequestBody.partialUpdateRegister())
                .patch("booking/")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}
