package WFCBookingSystem;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
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
        Customer customer1 = new Customer("Eren");
        customers.add(customer1);
        currentCustomer = customer1;
        createBooking(fitnessLessons.get(0));
        createBooking(fitnessLessons.get(1));
        createBooking(fitnessLessons.get(2)).attend(5, "Good");

        Customer customer2 = new Customer("Mikasa");
        customers.add(customer2);
        currentCustomer = customer2;
        createBooking(fitnessLessons.get(0));
        createBooking(fitnessLessons.get(1));
        createBooking(fitnessLessons.get(2)).attend(4, "I like it!");

        Customer customer3 = new Customer("Armin");
        customers.add(customer3);
        currentCustomer = customer3;
        createBooking(fitnessLessons.get(0));
        createBooking(fitnessLessons.get(1));
        createBooking(fitnessLessons.get(2)).attend(3, "Fair");

        Customer customer4 = new Customer("Annie");
        customers.add(customer4);
        currentCustomer = customer4;
        createBooking(fitnessLessons.get(0));
        createBooking(fitnessLessons.get(1));
        createBooking(fitnessLessons.get(2)).attend(2, "The lesson is too difficult for beginners");

        Customer customer5 = new Customer("Levi");
        customers.add(customer5);
        currentCustomer = customer5;
        createBooking(fitnessLessons.get(0));
        createBooking(fitnessLessons.get(2)).attend(1, "The teacher is so rude!");

        currentCustomer = null;
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

    public Customer getCurrentCustomer() {
        return currentCustomer;
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

    public List<FitnessLesson> getFitnessLessons() {
        return fitnessLessons;
    }

    public List<Booking> getBookingList() {
        return bookingList.stream().filter(new Predicate<Booking>() {
            @Override
            public boolean test(Booking booking) {
                return booking.getCustomer() == currentCustomer && booking.getBookingStatus() == Booking.BookingStatus.Booked;
            }
        }).toList();
    }

    public Map<FitnessLesson.FitnessType, Double> getFitnessTypeIncomeMap(int month) {
        Map<FitnessLesson.FitnessType, Double> fitnessTypeIncomeMap = new HashMap<>();
        for (FitnessLesson.FitnessType type : FitnessLesson.FitnessType.values()) {
            fitnessTypeIncomeMap.put(type, 0d);
        }
        for (Booking booking : bookingList) {
            FitnessLesson lesson = booking.getFitnessLesson();
            if (booking.getBookingStatus() == Booking.BookingStatus.Cancelled) {
                continue;
            }
            if (lesson.getDatetime().getMonthValue() != month) {
                continue;
            }
            double income = fitnessTypeIncomeMap.get(lesson.getFitnessType());
            income += lesson.getPrice();
            fitnessTypeIncomeMap.put(lesson.getFitnessType(), income);
        }
        return fitnessTypeIncomeMap;
    }
}
