package test;

import WFCBookingSystem.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class FitnessLessonTest {

    FitnessLesson fitnessLesson;

    @Test
    void testGenerateReportFullBookingAllAttend() {
        FitnessLesson.FitnessType testFitnessType = FitnessLesson.FitnessType.BoxFit;
        LocalDateTime testDateTime = LocalDateTime.of(2023, 4, 1, 10, 0);
        fitnessLesson = new FitnessLesson(testFitnessType, testDateTime);
        Booking booking1 = new Booking(new Customer("test1"), fitnessLesson);
        fitnessLesson.addBooking(booking1);
        booking1.attend(5, "Excellent");

        Booking booking2 = new Booking(new Customer("test2"), fitnessLesson);
        fitnessLesson.addBooking(booking2);
        booking2.attend(4, "Good");

        Booking booking3 = new Booking(new Customer("test3"), fitnessLesson);
        fitnessLesson.addBooking(booking3);
        booking3.attend(3, "Not bad");

        Booking booking4 = new Booking(new Customer("test4"), fitnessLesson);
        fitnessLesson.addBooking(booking4);
        booking4.attend(2, "Fair");

        Booking booking5 = new Booking(new Customer("test5"), fitnessLesson);
        fitnessLesson.addBooking(booking5);
        booking5.attend(1, "Bad");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd(EEE) HH:mm");
        String expected = String.format("%s %s, No. of customers: %d, Average rating: %.1f", testDateTime.format(formatter), testFitnessType.name(), 5, (5 + 4 + 3 + 2 + 1) / 5f);

        assertEquals(expected, fitnessLesson.generateReport());
    }

    @Test
    void testGenerateReportFullBookingNoAttend() {
        FitnessLesson.FitnessType testFitnessType = FitnessLesson.FitnessType.BoxFit;
        LocalDateTime testDateTime = LocalDateTime.of(2023, 4, 1, 10, 0);
        fitnessLesson = new FitnessLesson(testFitnessType, testDateTime);
        Booking booking1 = new Booking(new Customer("test1"), fitnessLesson);
        fitnessLesson.addBooking(booking1);

        Booking booking2 = new Booking(new Customer("test2"), fitnessLesson);
        fitnessLesson.addBooking(booking2);

        Booking booking3 = new Booking(new Customer("test3"), fitnessLesson);
        fitnessLesson.addBooking(booking3);

        Booking booking4 = new Booking(new Customer("test4"), fitnessLesson);
        fitnessLesson.addBooking(booking4);

        Booking booking5 = new Booking(new Customer("test5"), fitnessLesson);
        fitnessLesson.addBooking(booking5);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd(EEE) HH:mm");
        String expected = String.format("%s %s, No. of customers: %d, Average rating: %.1f", testDateTime.format(formatter), testFitnessType.name(), 0, 0f);

        assertEquals(expected, fitnessLesson.generateReport());
    }

    @Test
    void testGenerateReportNoBooking() {
        FitnessLesson.FitnessType testFitnessType = FitnessLesson.FitnessType.BoxFit;
        LocalDateTime testDateTime = LocalDateTime.of(2023, 4, 1, 10, 0);
        fitnessLesson = new FitnessLesson(testFitnessType, testDateTime);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd(EEE) HH:mm");
        String expected = String.format("%s %s, No. of customers: %d, Average rating: %.1f", testDateTime.format(formatter), testFitnessType.name(), 0, 0f);

        assertEquals(expected, fitnessLesson.generateReport());
    }

    @Test
    void testGenerateReportPartlyAttend() {
        FitnessLesson.FitnessType testFitnessType = FitnessLesson.FitnessType.BoxFit;
        LocalDateTime testDateTime = LocalDateTime.of(2023, 4, 1, 10, 0);
        fitnessLesson = new FitnessLesson(testFitnessType, testDateTime);
        Booking booking1 = new Booking(new Customer("test1"), fitnessLesson);
        fitnessLesson.addBooking(booking1);
        booking1.attend(5, "Excellent");

        Booking booking2 = new Booking(new Customer("test2"), fitnessLesson);
        fitnessLesson.addBooking(booking2);
        booking2.attend(1, "Bad");

        Booking booking3 = new Booking(new Customer("test3"), fitnessLesson);
        fitnessLesson.addBooking(booking3);

        Booking booking4 = new Booking(new Customer("test4"), fitnessLesson);
        fitnessLesson.addBooking(booking4);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd(EEE) HH:mm");
        String expected = String.format("%s %s, No. of customers: %d, Average rating: %.1f", testDateTime.format(formatter), testFitnessType.name(), 2, (5 + 1) / 2f);

        assertEquals(expected, fitnessLesson.generateReport());
    }

    @Test
    void testGenerateReportOneBooking() {
        FitnessLesson.FitnessType testFitnessType = FitnessLesson.FitnessType.BoxFit;
        LocalDateTime testDateTime = LocalDateTime.of(2023, 4, 1, 10, 0);
        fitnessLesson = new FitnessLesson(testFitnessType, testDateTime);
        Booking booking1 = new Booking(new Customer("test1"), fitnessLesson);
        fitnessLesson.addBooking(booking1);
        booking1.attend(5, "Excellent");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd(EEE) HH:mm");
        String expected = String.format("%s %s, No. of customers: %d, Average rating: %.1f", testDateTime.format(formatter), testFitnessType.name(), 1, 5f);

        assertEquals(expected, fitnessLesson.generateReport());
    }
}