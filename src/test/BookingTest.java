package test;

import WFCBookingSystem.Booking;
import WFCBookingSystem.Customer;
import WFCBookingSystem.FitnessLesson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;

import static org.junit.Assert.*;


class BookingTest {
    private ByteArrayOutputStream testOut = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(testOut));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(System.out);
    }

    @Test
    void testChangeFitnessLesson() {
        //Arrange
        FitnessLesson.FitnessType originalFitnessType = FitnessLesson.FitnessType.BoxFit;
        LocalDateTime originalDateTime = LocalDateTime.of(2023, 4, 1, 10, 0);
        FitnessLesson originalLesson = new FitnessLesson(originalFitnessType, originalDateTime);

        FitnessLesson.FitnessType newFitnessType = FitnessLesson.FitnessType.Yoga;
        LocalDateTime newDateTime = LocalDateTime.of(2023, 4, 1, 16, 0);
        FitnessLesson newLesson = new FitnessLesson(newFitnessType, newDateTime);

        Customer customer = new Customer("test");
        Booking booking = new Booking(customer, originalLesson);
        originalLesson.addBooking(booking);
        customer.addBooking(booking);

        //Act
        booking.changeFitnessLesson(newLesson);

        //Assert
        assertEquals(customer, booking.getCustomer());
        assertEquals(Booking.BookingStatus.Booked, booking.getBookingStatus());
        assertEquals(newLesson, booking.getFitnessLesson());
        assertFalse(originalLesson.getBookingList().contains(booking));
        assertTrue(newLesson.getBookingList().contains(booking));
        assertTrue(customer.getBookingList().contains(booking));
    }

    @Test
    void testChangeToSameFitnessLesson() {
        //Arrange
        FitnessLesson.FitnessType originalFitnessType = FitnessLesson.FitnessType.BoxFit;
        LocalDateTime originalDateTime = LocalDateTime.of(2023, 4, 1, 10, 0);
        FitnessLesson originalLesson = new FitnessLesson(originalFitnessType, originalDateTime);

        Customer customer = new Customer("test");
        Booking booking = new Booking(customer, originalLesson);
        originalLesson.addBooking(booking);
        customer.addBooking(booking);

        //Act
        booking.changeFitnessLesson(originalLesson);

        //Assert
        assertEquals(customer, booking.getCustomer());
        assertEquals(Booking.BookingStatus.Booked, booking.getBookingStatus());
        assertEquals(originalLesson, booking.getFitnessLesson());
        assertTrue(originalLesson.getBookingList().contains(booking));
        assertTrue(customer.getBookingList().contains(booking));
        assertEquals("You already joined this class", testOut.toString().trim());
    }

    @Test
    void testChangeToFullFitnessLesson() {
        //Arrange
        FitnessLesson.FitnessType originalFitnessType = FitnessLesson.FitnessType.BoxFit;
        LocalDateTime originalDateTime = LocalDateTime.of(2023, 4, 1, 10, 0);
        FitnessLesson originalLesson = new FitnessLesson(originalFitnessType, originalDateTime);

        FitnessLesson.FitnessType newFitnessType = FitnessLesson.FitnessType.Yoga;
        LocalDateTime newDateTime = LocalDateTime.of(2023, 4, 1, 16, 0);
        FitnessLesson newLesson = new FitnessLesson(newFitnessType, newDateTime);
        //make new lesson full booked
        newLesson.addBooking(new Booking(new Customer("customer1"), newLesson));
        newLesson.addBooking(new Booking(new Customer("customer2"), newLesson));
        newLesson.addBooking(new Booking(new Customer("customer3"), newLesson));
        newLesson.addBooking(new Booking(new Customer("customer4"), newLesson));
        newLesson.addBooking(new Booking(new Customer("customer5"), newLesson));

        Customer customer = new Customer("test");
        Booking booking = new Booking(customer, originalLesson);
        originalLesson.addBooking(booking);
        customer.addBooking(booking);

        //Act
        booking.changeFitnessLesson(newLesson);

        //Assert
        assertEquals(customer, booking.getCustomer());
        assertEquals(Booking.BookingStatus.Booked, booking.getBookingStatus());
        assertEquals(originalLesson, booking.getFitnessLesson());
        assertTrue(originalLesson.getBookingList().contains(booking));
        assertTrue(customer.getBookingList().contains(booking));
        assertEquals("The class capacity is full", testOut.toString().trim());
    }

    @Test
    void testChangeAttendedBooking() {
        //Arrange
        FitnessLesson.FitnessType originalFitnessType = FitnessLesson.FitnessType.BoxFit;
        LocalDateTime originalDateTime = LocalDateTime.of(2023, 4, 1, 10, 0);
        FitnessLesson originalLesson = new FitnessLesson(originalFitnessType, originalDateTime);

        FitnessLesson.FitnessType newFitnessType = FitnessLesson.FitnessType.Yoga;
        LocalDateTime newDateTime = LocalDateTime.of(2023, 4, 1, 16, 0);
        FitnessLesson newLesson = new FitnessLesson(newFitnessType, newDateTime);

        Customer customer = new Customer("test");
        Booking booking = new Booking(customer, originalLesson);
        originalLesson.addBooking(booking);
        customer.addBooking(booking);
        booking.attend(5, "Nice");  //create an attended booking

        //Act
        booking.changeFitnessLesson(newLesson);

        //Assert
        assertEquals(customer, booking.getCustomer());
        assertEquals(Booking.BookingStatus.Attended, booking.getBookingStatus());
        assertEquals(originalLesson, booking.getFitnessLesson());
        assertTrue(originalLesson.getBookingList().contains(booking));
        assertTrue(customer.getBookingList().contains(booking));
    }

    @Test
    void testChangeCancelledBooking() {
        //Arrange
        FitnessLesson.FitnessType originalFitnessType = FitnessLesson.FitnessType.BoxFit;
        LocalDateTime originalDateTime = LocalDateTime.of(2023, 4, 1, 10, 0);
        FitnessLesson originalLesson = new FitnessLesson(originalFitnessType, originalDateTime);

        FitnessLesson.FitnessType newFitnessType = FitnessLesson.FitnessType.Yoga;
        LocalDateTime newDateTime = LocalDateTime.of(2023, 4, 1, 16, 0);
        FitnessLesson newLesson = new FitnessLesson(newFitnessType, newDateTime);

        Customer customer = new Customer("test");
        Booking booking = new Booking(customer, originalLesson);
        originalLesson.addBooking(booking);
        booking.cancel();   //create a cancelled booking

        //Act
        booking.changeFitnessLesson(newLesson);

        //Assert
        assertEquals(customer, booking.getCustomer());
        assertEquals(Booking.BookingStatus.Cancelled, booking.getBookingStatus());
        assertFalse(originalLesson.getBookingList().contains(booking));
        assertFalse(newLesson.getBookingList().contains(booking));
        assertFalse(customer.getBookingList().contains(booking));
    }

}