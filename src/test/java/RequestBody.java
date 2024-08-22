import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

public class RequestBody {

    Logger logger = Logger.getLogger(BookingRoom.class.getName());
    static UserBookingRequest userBookingRequest = new UserBookingRequest();
    static BookingDates bookDates = new BookingDates();

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

        userBookingRequest.setFirstname("pepito");
        userBookingRequest.setLastname("Perez");
        userBookingRequest.setTotalprice(120);
        userBookingRequest.setDepositpaid(true);
        bookDates.setCheckin("2025-01-01");
        bookDates.setCheckout("2025-01-01");
        userBookingRequest.setAdditionalneeds("super bowls & Breakfast");

        var serialized = mapper.writeValueAsString(userBookingRequest);
        logger.info(serialized);
    }

    public static String createAuthToken = """
            {
                "username" : "admin",
                "password" : "password123"
            }
            """;

    public static UserBookingRequest updateRegister() {

        bookDates.setCheckin("2025-08-01");
        bookDates.setCheckout("2025-09-01");

        userBookingRequest.setFirstname("Karlox");
        userBookingRequest.setLastname("Allen");
        userBookingRequest.setTotalprice(111);
        userBookingRequest.setDepositpaid(false);
        userBookingRequest.setBookingdates(bookDates);
        userBookingRequest.setAdditionalneeds("Breakfast");

        return userBookingRequest;
    }

    public static UserBookingRequest partialUpdateRegister(){

        userBookingRequest.setFirstname("James");
        userBookingRequest.setLastname("Allen");

        return userBookingRequest;
    }

    public static UserBookingRequest createBookingRequest() {

        bookDates.setCheckin("2025-01-01");
        bookDates.setCheckout("2025-01-01");

        userBookingRequest.setFirstname("chelita1");
        userBookingRequest.setLastname("Perez");
        userBookingRequest.setTotalprice(100);
        userBookingRequest.setDepositpaid(true);
        userBookingRequest.setBookingdates(bookDates);
        userBookingRequest.setAdditionalneeds("super bowls & Breakfast");

        return userBookingRequest;
    }

    public static UserBookingRequest createBookingRequestWithWrongField() {

        UserBookingRequest createBookingRequest = new UserBookingRequest();
        var bookDates = new BookingDates();
        bookDates.setCheckin("2025-01-01");
        bookDates.setCheckout("2025-01-01");

        createBookingRequest.setFirstname("chelita1");
        createBookingRequest.setWrongLastname("Perez");
        createBookingRequest.setTotalprice(100);
        createBookingRequest.setDepositpaid(true);
        createBookingRequest.setBookingdates(bookDates);
        createBookingRequest.setAdditionalneeds("super bowls & Breakfast");

        return createBookingRequest;
    }
}