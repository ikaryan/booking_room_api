package steps.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.SneakyThrows;
import steps.data_types.BookingDates;
import steps.data_types.UserBookingRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static java.lang.String.format;

public class SerializeAndDeserializeSuccessfullySteps extends RequestBody {

    Logger logger = Logger.getLogger(SerializeAndDeserializeSuccessfullySteps.class.getName());
    static BookingDates bookDates = new BookingDates();
    private String jsonString;
    UserBookingRequest deserialized;
//    static UserBookingRequest userBookingRequest = new UserBookingRequest();

    @SneakyThrows
    @When("^a java object is stored in json file$")
    public void aJavaObjectIsStoredInJsonFile(DataTable data1) {

        List<List<String>> data = data1.asLists(String.class);
        List<Object> list = new ArrayList<>();

        final ObjectMapper mapper = new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT);

       /* userBookingRequest.setFirstname(data.get(1).get(0));
        userBookingRequest.setLastname(data.get(1).get(1));
        userBookingRequest.setTotalprice(Integer.parseInt(data.get(1).get(2)));
        userBookingRequest.setDepositpaid(Boolean.parseBoolean(data.get(1).get(3)));
        bookDates.setCheckin(data.get(1).get(4));
        bookDates.setCheckout(data.get(1).get(5));
        userBookingRequest.setAdditionalneeds(data.get(1).get(6));

        list.add(userBookingRequest);
        list.add(bookDates);*/

        var serialized = mapper.writeValueAsString(list);
        logger.info(serialized);
    }

    @SneakyThrows
    @Given("^user have the following data$")
    public void userHaveTheFollowingData(DataTable dataTable) {

        List<Map<String, String>> userData = dataTable.asMaps(String.class, String.class);
        Map<String, String> userMap = userData.get(0);

        jsonString = format("""
                         {
                             "firstname": "%s", 
                             "lastname": "%s",
                             "totalprice": %s,
                             "depositpaid": "%s",
                             "bookingdates": {
                                 "checkin": "%s",
                                 "checkout": "%s"
                             },
                             "additionalneeds": "%s"
                         }
                        """, userMap.get("firstname"),
                userMap.get("lastname"),
                userMap.get("totalprice"),
                userMap.get("depositpaid"),
                userMap.get("checkin"),
                userMap.get("checkout"),
                userMap.get("additionalneeds"));
    }

    @SneakyThrows
    @When("user deserialize the JSON string")
    public void userDeserializeTheJSONString() {

        final ObjectMapper mapper = new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT);
        deserialized = mapper.readValue(jsonString, UserBookingRequest.class);
    }

    @Then("user should see the User object")
    public void userShouldSeeTheUserObject() {

        logger.info("" + deserialized);
    }
}