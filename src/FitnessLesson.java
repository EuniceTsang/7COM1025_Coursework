import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FitnessLesson {
    enum FitnessType {
        Spin, Yoga, Bodysculpt, Zumba, Aquacise, BoxFit
    }

    private FitnessType fitnessType;
    private LocalDateTime datetime;

    private List<Booking> bookingList;

    private double price;

    public FitnessLesson(FitnessType fitnessType, LocalDateTime datetime) {
        this.fitnessType = fitnessType;
        this.datetime = datetime;
        bookingList = new ArrayList<>();
        //set price according to fitness type
        switch (fitnessType) {
            case Spin:
                price = 20.5;
                break;
            case Yoga:
                price = 19.4;
                break;
            case Bodysculpt:
                price = 24.0;
                break;
            case Zumba:
                price = 23.9;
                break;
            case Aquacise:
                price = 25.0;
                break;
            case BoxFit:
                price = 25.5;
                break;
            default:
                break;
        }
    }

    public List<Booking> getBookingList() {
        return bookingList;
    }

    public boolean checkStudentExist(Customer customer) {
        for (Booking booking : bookingList) {
            if (booking.getCustomer() == customer) {
                return true;
            }
        }
        return false;
    }

    public void addBooking(Booking booking) {
        if (bookingList.size() < 5) {
            bookingList.add(booking);
        }
    }

    public FitnessType getFitnessType() {
        return fitnessType;
    }

    public void setFitnessType(FitnessType fitnessType) {
        this.fitnessType = fitnessType;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return String.format("%s %s £%.2f", datetime.format(formatter), fitnessType.name(), price);
    }
}
