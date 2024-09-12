package steps.data_types;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UserBookingRequest {

    private String firstname;
    private String lastname;
    private String wronglastname;
    private int totalprice;
    private boolean depositpaid;
    private BookingDates bookingdates;
    private String additionalneeds;

    @Override
    public String toString() {
        return "UserBookingRequest{firstname='" + firstname + "', lastname=" + lastname + ", totalprice='" + totalprice +
                "', depositpaid =" + depositpaid + "', bookingdates=" + bookingdates + ",additionalneeds =" + additionalneeds + "'}";
    }

    /*public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setWrongLastname(String wronglastname) {
        this.wronglastname = wronglastname;
    }

    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }

    public void setDepositpaid(boolean depositpaid) {
        this.depositpaid = depositpaid;
    }

    public void setAdditionalneeds(String additionalneeds) {
        this.additionalneeds = additionalneeds;
    }

    public void setBookingdates(BookingDates bookingdates) {
        this.bookingdates = bookingdates;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String wronglastname() {
        return wronglastname;
    }

    public int getTotalprice() {
        return totalprice;
    }

    public boolean isDepositpaid() {
        return depositpaid;
    }

    public String getAdditionalneeds() {
        return additionalneeds;
    }

    public BookingDates getBookingdates() {
        return bookingdates;
    }
*/
}
