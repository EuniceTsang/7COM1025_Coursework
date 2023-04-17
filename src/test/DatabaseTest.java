package test;

import WFCBookingSystem.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.Assert.assertEquals;

class DatabaseTest {

    Database database;
    List<FitnessLesson> lessonList;
    private ByteArrayOutputStream testOut = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        database = new Database();
        lessonList = database.getFitnessLessons();
        System.setOut(new PrintStream(testOut));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(System.out);
    }

    @Test
    void testCreateBooking1() {
        database.userLogin("test");
        FitnessLesson lesson = lessonList.get(3);   //a lesson which have no student
        Booking booking = database.createBooking(lesson);
        assertEquals(Booking.BookingStatus.Booked, booking.getBookingStatus());
        assertEquals(lesson, booking.getFitnessLesson());
        assertEquals(database.getCurrentCustomer(), booking.getCustomer());
    }

    @Test
    void testCreateBooking2() {
        database.userLogin("test");
        FitnessLesson lesson = lessonList.get(1); //a lesson which have 4 students
        Booking booking = database.createBooking(lesson);
        assertEquals(Booking.BookingStatus.Booked, booking.getBookingStatus());
        assertEquals(lesson, booking.getFitnessLesson());
        assertEquals(database.getCurrentCustomer(), booking.getCustomer());
    }

    @Test
    void testRebooking() {
        database.userLogin("test");
        FitnessLesson lesson = lessonList.get(3);
        database.createBooking(lesson);
        database.createBooking(lesson);
        assertEquals("You already joined this class", testOut.toString().trim());
    }

    @Test
    void testFullBooking() {
        database.userLogin("test");
        FitnessLesson lesson = lessonList.get(0); //a lesson which already have 5 students
        database.createBooking(lesson);
        assertEquals("The class capacity is full", testOut.toString().trim());
    }

    @Test
    void testReBookingFullLesson() {
        database.userLogin("test");
        FitnessLesson lesson = lessonList.get(1); //a lesson which have 4 students
        database.createBooking(lesson);
        database.createBooking(lesson);
        assertEquals("You already joined this class", testOut.toString().trim());
    }
}