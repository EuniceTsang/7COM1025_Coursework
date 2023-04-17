package test;

import WFCBookingSystem.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    Database database;
    List<FitnessLesson> lessonList;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void before() {
        database = new Database();
        lessonList = database.getFitnessLessons();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void testCreateBooking1() {
        database.userLogin("test");
        FitnessLesson lesson = lessonList.get(3);   //a lesson which have no student
        Booking booking = database.createBooking(lesson);
        Assertions.assertEquals(booking.getBookingStatus(), Booking.BookingStatus.Booked);
        Assertions.assertEquals(booking.getFitnessLesson(), lesson);
        Assertions.assertEquals(booking.getCustomer(), database.getCurrentCustomer());
    }

    @Test
    void testCreateBooking2() {
        database.userLogin("test");
        FitnessLesson lesson = lessonList.get(1); //a lesson which have 4 students
        Booking booking = database.createBooking(lesson);
        Assertions.assertEquals(booking.getBookingStatus(), Booking.BookingStatus.Booked);
        Assertions.assertEquals(booking.getFitnessLesson(), lesson);
        Assertions.assertEquals(booking.getCustomer(), database.getCurrentCustomer());
    }

    @Test
    void testRebooking() {
        database.userLogin("test");
        FitnessLesson lesson = lessonList.get(3);
        database.createBooking(lesson);
        database.createBooking(lesson);
        assertEquals("You already joined this class.", outputStreamCaptor.toString().trim());
    }

    @Test
    void testFullBooking() {
        database.userLogin("test");
        FitnessLesson lesson = lessonList.get(0); //a lesson which already have 5 students
        database.createBooking(lesson);
        assertEquals("The class capacity is full.", outputStreamCaptor.toString().trim());
    }

    @Test
    void testReBookingFullLesson() {
        database.userLogin("test");
        FitnessLesson lesson = lessonList.get(1); //a lesson which have 4 students
        database.createBooking(lesson);
        database.createBooking(lesson);
        assertEquals("You already joined this class.", outputStreamCaptor.toString().trim());
    }

    @AfterEach
    public void tearDown() {
        System.setOut(System.out);
    }
}