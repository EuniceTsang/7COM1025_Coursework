import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class Database {
    private HashMap<String, Booking> bookingHashMap = new HashMap<>();
    private List<FitnessLesson> fitnessLessons = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private Customer currentCustomer;

    public Database() {
        LocalDateTime dateTime = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).truncatedTo(ChronoUnit.DAYS);

        //find the first weekend day of this month
        while (dateTime.getDayOfWeek() != DayOfWeek.SATURDAY) {
            dateTime = dateTime.plusDays(1);
        }
        //add two lessons for each fitness type for 8 weekend days
        for (int i = 0; i < 8; i++) {
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
        List<Booking> bookings = bookingHashMap.values().stream().toList();
        bookings.get(1).attend(5, "Good");
        bookings.get(3).attend(4, "I like it!");
        bookings.get(5).attend(3, "Fair");
        bookings.get(7).attend(2, "The lesson is too difficult for beginners");
        bookings.get(9).attend(1, "The teacher is so rude!");
        bookings.get(11).attend(5, "Excellent!!");
    }

    public void userLogout() {
        currentCustomer = null;
    }

    //for login function
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

    public Booking createBooking(FitnessLesson lesson) {
        if (lesson.checkStudentExist(currentCustomer)) {
            System.out.println("You already joined this class.");
            return null;
        }
        if (lesson.getBookingList().size() >= 5) {
            System.out.println("The class capacity is full.");
            return null;
        }
        String bookingNumber = UUID.randomUUID().toString();
        Booking booking = new Booking(bookingNumber, currentCustomer, lesson);
        currentCustomer.addBooking(booking);
        lesson.addBooking(booking);
        bookingHashMap.put(bookingNumber, booking);
        return booking;
    }

    public List<FitnessLesson> getFitnessLessons(LocalDateTime date) {
        return fitnessLessons.stream().filter(new Predicate<FitnessLesson>() {
            @Override
            public boolean test(FitnessLesson fitnessLesson) {
                return fitnessLesson.getDatetime().truncatedTo(ChronoUnit.DAYS).isEqual(date);
            }
        }).toList();
    }

    public List<FitnessLesson> getFitnessLessons(FitnessLesson.FitnessType fitnessType) {
        return fitnessLessons.stream().filter(new Predicate<FitnessLesson>() {
            @Override
            public boolean test(FitnessLesson fitnessLesson) {
                return fitnessLesson.getFitnessType() == fitnessType;
            }
        }).toList();
    }
}
