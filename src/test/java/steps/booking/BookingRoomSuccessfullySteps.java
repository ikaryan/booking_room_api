package steps.booking;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.apache.http.HttpStatus;
import steps.data_types.BookingDates;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BookingRoomSuccessfullySteps extends RequestBody {

    Logger logger = Logger.getLogger(BookingRoomSuccessfullySteps.class.getName());
    static BookingDates bookDates = new BookingDates();
    //    static UserBookingRequest userBookingRequest = new UserBookingRequest();
    String firstname;

    @When("^that an auth token is created$")
    public void thatAnAuthTokenIsCreated(DataTable dataTable) {

        List<Map<String, String>> userData = dataTable.asMaps(String.class, String.class);
        Map<String, String> userMap = userData.get(0);

        given()
                .body(RequestBody.createAuthToken(userMap))
                .post("auth")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("token", notNullValue());
    }

    @Given("that hotel requires to search a specific reservation")
    public void thatHotelRequiresToSearchASpecificReservation(DataTable dataTable) {

        List<Map<String, String>> userData = dataTable.asMaps(String.class, String.class);
        Map<String, String> userMap = userData.get(0);

        firstname = given()
                .get(format("booking/%s", userMap.get("bookingnumber")))
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().jsonPath().getString(userMap.get("search"));
    }

    @And("^the receptionist verifies if the user (.*) appears in that search$")
    public void theReceptionistVerifiesIfUserAppearsInThatSearch(String userToSearch) {
        assertThat(firstname, equalToIgnoringCase(userToSearch));
    }

    @Given("that an user made a new reservation")
    public void thatAnUserMadeANewReservation(DataTable dataTable) {

        List<Map<String, String>> userData = dataTable.asMaps(String.class, String.class);
        Map<String, String> userMap = userData.get(0);

        given()
                .body(RequestBody.createBookingRequest(userMap))
                .post(userMap.get("path"))
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(userMap.get("itemtosearch"), greaterThanOrEqualTo(1));
    }
}
