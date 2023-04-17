package WFCBookingSystem;

import java.time.format.DateTimeFormatter;

public class Booking {
    public enum BookingStatus {
        Booked, Cancelled, Attended
    }

    private Customer customer;
    private FitnessLesson fitnessLesson;
    private BookingStatus bookingStatus;
    private Integer rating;
    private String review;

    public Booking(Customer customer, FitnessLesson fitnessLesson) {
        this.customer = customer;
        this.fitnessLesson = fitnessLesson;
        bookingStatus = BookingStatus.Booked;
    }

    public Customer getCustomer() {
        return customer;
    }

    public FitnessLesson getFitnessLesson() {
        return fitnessLesson;
    }

    public boolean changeFitnessLesson(FitnessLesson newFitnessLesson) {
        if (this.bookingStatus != BookingStatus.Booked) {
            return false;
        }
        if (newFitnessLesson.getBookingList().size() >= 5) {
            System.out.println("The class capacity is full");
            return false;
        }
        if (newFitnessLesson.checkStudentExist(customer)) {
            System.out.println("You already joined this class");
            return false;
        }
        this.getFitnessLesson().removeBooking(this);
        this.fitnessLesson = newFitnessLesson;
        this.fitnessLesson.addBooking(this);
        return true;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public Integer getRating() {
        return rating;
    }

    public void attend(int rating, String review) {
        bookingStatus = BookingStatus.Attended;
        this.rating = rating;
        this.review = review;
    }

    public void cancel() {
        bookingStatus = BookingStatus.Cancelled;
        fitnessLesson.removeBooking(this);
        customer.removeBooking(this);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd(EEE) HH:mm");
        return String.format("%s %s", fitnessLesson.getFitnessType().name(), fitnessLesson.getDatetime().format(formatter));
    }
}
