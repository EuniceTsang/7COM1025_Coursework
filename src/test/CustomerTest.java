package test;

import WFCBookingSystem.Booking;
import WFCBookingSystem.Customer;
import WFCBookingSystem.FitnessLesson;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void testAddBooking() {
        Customer customer = new Customer("Test");
        FitnessLesson.FitnessType testFitnessType = FitnessLesson.FitnessType.BoxFit;
        LocalDateTime testDateTime = LocalDateTime.of(2023, 4, 1, 10, 0);
        FitnessLesson lesson = new FitnessLesson(testFitnessType, testDateTime);
        Booking booking = new Booking(customer,lesson);
        customer.addBooking(booking);

        assertTrue(customer.getBookingList().contains(booking));
        assertEquals(1, customer.getBookingList().size());
    }

    @Test
    void testAddBookingWrongCustomer() {
        Customer customer = new Customer("Test");
        Customer wrongcCustomer = new Customer("Wrong");
        FitnessLesson.FitnessType testFitnessType = FitnessLesson.FitnessType.BoxFit;
        LocalDateTime testDateTime = LocalDateTime.of(2023, 4, 1, 10, 0);
        FitnessLesson lesson = new FitnessLesson(testFitnessType, testDateTime);
        Booking booking = new Booking(wrongcCustomer,lesson);
        customer.addBooking(booking);

        assertFalse(customer.getBookingList().contains(booking));
        assertEquals(0, customer.getBookingList().size());
    }

    @Test
    void testAddAttendedBooking() {
        Customer customer = new Customer("Test");
        FitnessLesson.FitnessType testFitnessType = FitnessLesson.FitnessType.BoxFit;
        LocalDateTime testDateTime = LocalDateTime.of(2023, 4, 1, 10, 0);
        FitnessLesson lesson = new FitnessLesson(testFitnessType, testDateTime);
        Booking booking = new Booking(customer,lesson);
        booking.attend(5, "Nice");
        customer.addBooking(booking);

        assertFalse(customer.getBookingList().contains(booking));
        assertEquals(0, customer.getBookingList().size());
    }

    @Test
    void testAddCancelledBooking() {
        Customer customer = new Customer("Test");
        FitnessLesson.FitnessType testFitnessType = FitnessLesson.FitnessType.BoxFit;
        LocalDateTime testDateTime = LocalDateTime.of(2023, 4, 1, 10, 0);
        FitnessLesson lesson = new FitnessLesson(testFitnessType, testDateTime);
        Booking booking = new Booking(customer,lesson);
        booking.cancel();
        customer.addBooking(booking);

        assertFalse(customer.getBookingList().contains(booking));
        assertEquals(0, customer.getBookingList().size());
    }

    @Test
    void testAddDuplicateBooking() {
        Customer customer = new Customer("Test");
        FitnessLesson.FitnessType testFitnessType = FitnessLesson.FitnessType.BoxFit;
        LocalDateTime testDateTime = LocalDateTime.of(2023, 4, 1, 10, 0);
        FitnessLesson lesson = new FitnessLesson(testFitnessType, testDateTime);
        Booking booking = new Booking(customer,lesson);
        customer.addBooking(booking);
        customer.addBooking(booking);

        assertTrue(customer.getBookingList().contains(booking));
        assertEquals(1, customer.getBookingList().size());
    }
}