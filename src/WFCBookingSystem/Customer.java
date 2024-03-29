package WFCBookingSystem;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String username;
    private List<Booking> bookingList;

    public Customer(String name) {
        this.username = name;
        bookingList = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void addBooking(Booking booking) {
        if (!bookingList.contains(booking) &&
                booking.getBookingStatus() == Booking.BookingStatus.Booked &&
                booking.getCustomer() == this) {
            bookingList.add(booking);
        }
    }

    public void removeBooking(Booking booking) {
        bookingList.remove(booking);
    }

    public List<Booking> getBookingList() {
        return bookingList;
    }
}
