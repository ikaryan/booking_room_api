package steps.booking;

import steps.BookingRoomTest1;
import steps.data_types.BookingDates;
import steps.data_types.UserBookingRequest;

import java.util.Map;
import java.util.logging.Logger;

import static java.lang.String.format;

public class RequestBody {

    Logger logger = Logger.getLogger(BookingRoomTest1.class.getName());
    protected static BookingDates bookDates = new BookingDates();
    static UserBookingRequest userBookingRequest = new UserBookingRequest();


    public static String createAuthToken(Map<String, String> userMap){

       String jsonString = format("""
            {
                "username" : "%s",
                "password" : "%s"
            }
            """,
               userMap.get("username"),
               userMap.get("password"));
       return jsonString;
    }

    public static UserBookingRequest createBookingRequest(Map<String, String> userMap) {

        bookDates.setCheckin(userMap.get("checkin"));
        bookDates.setCheckout(userMap.get("checkout"));

        userBookingRequest.setFirstname(userMap.get("firstname"));
        userBookingRequest.setLastname(userMap.get("lastname"));
        userBookingRequest.setTotalprice(Integer.parseInt(userMap.get("totalprice")));
        userBookingRequest.setDepositpaid(Boolean.parseBoolean(userMap.get("deposit")));
        userBookingRequest.setBookingdates(bookDates);
        userBookingRequest.setAdditionalneeds(userMap.get("needs"));

        return userBookingRequest;
    }

   /* public static UserBookingRequest updateRegister() {

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

    public static UserBookingRequest partialUpdateRegister() {

        userBookingRequest.setFirstname("James");
        userBookingRequest.setLastname("Allen");

        return userBookingRequest;
    }



    public static UserBookingRequest createBookingRequestWithWrongField() {

        UserBookingRequest userBookingRequest1 = new UserBookingRequest();
        var bookDates = new BookingDates();
        bookDates.setCheckin("2025-01-01");
        bookDates.setCheckout("2025-01-01");

        userBookingRequest1.setFirstname("chelita1");
        userBookingRequest1.setWrongLastname("Perez");
        userBookingRequest1.setTotalprice(100);
        userBookingRequest1.setDepositpaid(true);
        userBookingRequest1.setBookingdates(bookDates);
        userBookingRequest1.setAdditionalneeds("super bowls & Breakfast");

        return userBookingRequest1;
    }*/
}