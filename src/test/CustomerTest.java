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
        //Arrange
        Customer customer = new Customer("Test");
        FitnessLesson.FitnessType testFitnessType = FitnessLesson.FitnessType.BoxFit;
        LocalDateTime testDateTime = LocalDateTime.of(2023, 4, 1, 10, 0);
        FitnessLesson lesson = new FitnessLesson(testFitnessType, testDateTime);
        Booking booking = new Booking(customer,lesson);

        //Act
        customer.addBooking(booking);

        //Assert
        assertTrue(customer.getBookingList().contains(booking));
        assertEquals(1, customer.getBookingList().size());
    }

    @Test
    void testAddBookingWrongCustomer() {
        //Arrange
        Customer customer = new Customer("Test");
        Customer anotherCustomer = new Customer("Another");
        FitnessLesson.FitnessType testFitnessType = FitnessLesson.FitnessType.BoxFit;
        LocalDateTime testDateTime = LocalDateTime.of(2023, 4, 1, 10, 0);
        FitnessLesson lesson = new FitnessLesson(testFitnessType, testDateTime);
        Booking booking = new Booking(anotherCustomer,lesson);   //create a booking which does not belong to customer "Test"

        //Act
        customer.addBooking(booking);

        //Assert
        assertFalse(customer.getBookingList().contains(booking));
        assertEquals(0, customer.getBookingList().size());
    }

    @Test
    void testAddAttendedBooking() {
        //Arrange
        Customer customer = new Customer("Test");
        FitnessLesson.FitnessType testFitnessType = FitnessLesson.FitnessType.BoxFit;
        LocalDateTime testDateTime = LocalDateTime.of(2023, 4, 1, 10, 0);
        FitnessLesson lesson = new FitnessLesson(testFitnessType, testDateTime);
        Booking booking = new Booking(customer,lesson);
        booking.attend(5, "Nice");

        //Act
        customer.addBooking(booking);

        //Assert
        assertFalse(customer.getBookingList().contains(booking));
        assertEquals(0, customer.getBookingList().size());
    }

    @Test
    void testAddCancelledBooking() {
        //Arrange
        Customer customer = new Customer("Test");
        FitnessLesson.FitnessType testFitnessType = FitnessLesson.FitnessType.BoxFit;
        LocalDateTime testDateTime = LocalDateTime.of(2023, 4, 1, 10, 0);
        FitnessLesson lesson = new FitnessLesson(testFitnessType, testDateTime);
        Booking booking = new Booking(customer,lesson);
        booking.cancel();

        //Act
        customer.addBooking(booking);

        //Assert
        assertFalse(customer.getBookingList().contains(booking));
        assertEquals(0, customer.getBookingList().size());
    }

    @Test
    void testAddDuplicateBooking() {
        //Arrange
        Customer customer = new Customer("Test");
        FitnessLesson.FitnessType testFitnessType = FitnessLesson.FitnessType.BoxFit;
        LocalDateTime testDateTime = LocalDateTime.of(2023, 4, 1, 10, 0);
        FitnessLesson lesson = new FitnessLesson(testFitnessType, testDateTime);
        Booking booking = new Booking(customer,lesson);

        //Act
        customer.addBooking(booking);
        customer.addBooking(booking);

        //Assert
        assertTrue(customer.getBookingList().contains(booking));
        assertEquals(1, customer.getBookingList().size());
    }
}