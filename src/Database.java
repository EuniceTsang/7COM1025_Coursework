import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Database {
    private List<Booking> bookingList = new ArrayList<>();
    private List<FitnessLesson> fitnessLessons = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private Customer currentCustomer;

    public Database() {
        LocalDateTime dateTime = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).truncatedTo(ChronoUnit.DAYS);

        //find the first weekend day of this month
        while (dateTime.getDayOfWeek() != DayOfWeek.SATURDAY) {
            dateTime = dateTime.plusDays(1);
        }
        //add two lessons for each fitness type for 8 weekends
        for (int i = 0; i < 16; i++) {
            for (FitnessLesson.FitnessType fitnessType : FitnessLesson.FitnessType.values()) {
                fitnessLessons.add(new FitnessLesson(fitnessType, dateTime.withHour(10)));
                fitnessLessons.add(new FitnessLesson(fitnessType, dateTime.withHour(16)));
            }
            if (dateTime.getDayOfWeek() == DayOfWeek.SATURDAY) {
                dateTime = dateTime.plusDays(1);
            } else {
                dateTime = dateTime.plusDays(6);
            }
        }
        //add customer and bookings
        customers.add(new Customer("Eren"));
        customers.add(new Customer("Mikasa"));
        customers.add(new Customer("Armin"));
        customers.add(new Customer("Annie"));
        customers.add(new Customer("Levi"));

        for (int i = 0; i < customers.size(); i++) {
            currentCustomer = customers.get(i);
            int j = i;
            while (j < fitnessLessons.size()) {
                createBooking(fitnessLessons.get(j));
                j += 5;
            }
        }
        currentCustomer = null;

        //attend some lesson
        bookingList.get(1).attend(5, "Good");
        bookingList.get(3).attend(4, "I like it!");
        bookingList.get(5).attend(3, "Fair");
        bookingList.get(7).attend(2, "The lesson is too difficult for beginners");
        bookingList.get(9).attend(1, "The teacher is so rude!");
        bookingList.get(11).attend(5, "Excellent!!");
    }

    //for login/logout
    public void userLogin(String username) {
        for (Customer customer : customers) {
            if (customer.getUsername().equals(username)) {
                currentCustomer = customer;
                return;
            }
        }
        //create new customer if not exist
        Customer newCustomer = new Customer(username);
        customers.add(newCustomer);
        currentCustomer = newCustomer;
    }
    public void userLogout() {
        System.out.printf("Logout success, bye %s\n", currentCustomer.getUsername());
        currentCustomer = null;
    }

    //for manage booking
    public Booking createBooking(FitnessLesson lesson) {
        if (lesson.checkStudentExist(currentCustomer)) {
            System.out.println("You already joined this class.");
            return null;
        }
        if (lesson.getBookingList().size() >= 5) {
            System.out.println("The class capacity is full.");
            return null;
        }
        Booking booking = new Booking(currentCustomer, lesson);
        currentCustomer.addBooking(booking);
        lesson.addBooking(booking);
        bookingList.add(booking);
        return booking;
    }
    public void cancelBooking(Booking booking) {
        booking.cancel();
        bookingList.remove(booking);
    }

    public void changeBooking(Booking booking, FitnessLesson lesson) {
        booking.changeFitnessLesson(lesson);
    }

    //filter fitness lessons by month
    public List<FitnessLesson> getFitnessLessons(int month) {
        return fitnessLessons.stream().filter(new Predicate<FitnessLesson>() {
            @Override
            public boolean test(FitnessLesson fitnessLesson) {
                return fitnessLesson.getDatetime().getMonthValue() == month;
            }
        }).toList();
    }

    //filter fitness lessons by weekday
    public List<FitnessLesson> getFitnessLessons(DayOfWeek weekday) {
        return fitnessLessons.stream().filter(new Predicate<FitnessLesson>() {
            @Override
            public boolean test(FitnessLesson fitnessLesson) {
                return fitnessLesson.getDatetime().getDayOfWeek() == weekday;
            }
        }).toList();
    }

    //filter fitness lessons by fitness type
    public List<FitnessLesson> getFitnessLessons(FitnessLesson.FitnessType fitnessType) {
        return fitnessLessons.stream().filter(new Predicate<FitnessLesson>() {
            @Override
            public boolean test(FitnessLesson fitnessLesson) {
                return fitnessLesson.getFitnessType() == fitnessType;
            }
        }).toList();
    }

    public List<Booking> getBookingList(){
        return bookingList.stream().filter(new Predicate<Booking>() {
            @Override
            public boolean test(Booking booking) {
                return booking.getCustomer() == currentCustomer && booking.getBookingStatus() == Booking.BookingStatus.Booked;
            }
        }).toList();
    }
}
