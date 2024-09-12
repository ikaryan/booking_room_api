package steps.booking;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
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

public class BookingRoomUnsuccessfullySteps extends RequestBody {

    Logger logger = Logger.getLogger(BookingRoomUnsuccessfullySteps.class.getName());
    static BookingDates bookDates = new BookingDates();
    String firstname;

    @When("^that an auth token attempts to be created with invalid path$")
    public void thatAnAuthTokenIsCreatedWithInvalidPath(DataTable dataTable) {

        List<Map<String, String>> userData = dataTable.asMaps(String.class, String.class);
        Map<String, String> userMap = userData.get(0);

        given()
                .body(RequestBody.createAuthToken(userMap))
                .post(userMap.get("invalidpath"))
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Then("^the receptionist verifies that the user (.*) doesn't appear in that search$")
    public void theReceptionistVerifiesThatTheUserJimDoesntAppearInThatSearch(String userToSearch) {
        assertThat(firstname, not(equalToIgnoringCase(userToSearch)));
    }
}
