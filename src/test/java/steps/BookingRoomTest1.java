package steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BookingRoomTest1 {

    Logger logger = Logger.getLogger(BookingRoomTest1.class.getName());
    String requestBody;

    @Test
    public void getAllBookingIds() {//200 OK

        given()
                .get("booking")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("bookingid", notNullValue());
    }


   /* @Test
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
    }*/
}
