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

    public void addBooking(Booking booking){
        bookingList.add(booking);
    }
}
