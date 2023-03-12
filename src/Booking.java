import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class Booking {
    enum BookingStatus {
        Booked, Cancelled, Attended
    }

    ;

    private Customer customer;
    private FitnessLesson fitnessLesson;
    private String bookingNumber;
    private BookingStatus bookingStatus;
    private Integer rating;
    private String review;

    public Booking(String bookingNumber, Customer customer, FitnessLesson fitnessLesson) {
        this.bookingNumber = bookingNumber;
        this.customer = customer;
        this.fitnessLesson = fitnessLesson;
        bookingStatus = BookingStatus.Booked;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void attend(int rating, String review) {
        bookingStatus = BookingStatus.Attended;
        this.rating = rating;
        this.review = review;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return String.format("Booking number: %s, %s %s", bookingNumber, fitnessLesson.getFitnessType().name(), fitnessLesson.getDatetime().format(formatter));
    }
}
