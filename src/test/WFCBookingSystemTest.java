package test;

import WFCBookingSystem.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class WFCBookingSystemTest {
    private ByteArrayOutputStream testOut = new ByteArrayOutputStream();
    private ByteArrayInputStream testIn;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(testOut));
    }

    @AfterEach
    void tearDown() {
        System.setOut(System.out);
        System.setIn(System.in);
    }

    private void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @Test
    void testShowTimetable1() {
        List<FitnessLesson> testFitnessLessonList = new ArrayList<>();
        FitnessLesson.FitnessType testFitnessType = FitnessLesson.FitnessType.BoxFit;
        LocalDateTime testDateTime = LocalDateTime.of(2023, 4, 1, 10, 0);
        FitnessLesson testFitnessLesson = new FitnessLesson(testFitnessType, testDateTime);
        testFitnessLessonList.add(testFitnessLesson);

        provideInput("1");
        FitnessLesson result = WFCBookingSystem.showTimetable(testFitnessLessonList);
        assertEquals(testFitnessLesson, result);
    }

    @Test
    void testShowTimetable2() {
        List<FitnessLesson> testFitnessLessonList = new ArrayList<>();
        FitnessLesson.FitnessType testFitnessType1 = FitnessLesson.FitnessType.BoxFit;
        LocalDateTime testDateTime1 = LocalDateTime.of(2023, 4, 1, 10, 0);
        FitnessLesson testFitnessLesson1 = new FitnessLesson(testFitnessType1, testDateTime1);
        testFitnessLessonList.add(testFitnessLesson1);

        FitnessLesson.FitnessType testFitnessType2 = FitnessLesson.FitnessType.Yoga;
        LocalDateTime testDateTime2 = LocalDateTime.of(2023, 4, 1, 16, 0);
        FitnessLesson testFitnessLesson2 = new FitnessLesson(testFitnessType2, testDateTime2);
        testFitnessLessonList.add(testFitnessLesson2);

        provideInput("2");
        FitnessLesson result = WFCBookingSystem.showTimetable(testFitnessLessonList);
        assertEquals(testFitnessLesson2, result);
    }
    @Test
    void testShowTimetableBack() {
        List<FitnessLesson> testFitnessLessonList = new ArrayList<>();
        FitnessLesson.FitnessType testFitnessType = FitnessLesson.FitnessType.BoxFit;
        LocalDateTime testDateTime = LocalDateTime.of(2023, 4, 1, 10, 0);
        FitnessLesson testFitnessLesson = new FitnessLesson(testFitnessType, testDateTime);
        testFitnessLessonList.add(testFitnessLesson);

        provideInput("BACK");
        FitnessLesson result = WFCBookingSystem.showTimetable(testFitnessLessonList);
        assertNull(result);
    }
    @Test
    void testShowTimetableInvalidIndex() {
        List<FitnessLesson> testFitnessLessonList = new ArrayList<>();
        FitnessLesson.FitnessType testFitnessType = FitnessLesson.FitnessType.BoxFit;
        LocalDateTime testDateTime = LocalDateTime.of(2023, 4, 1, 10, 0);
        FitnessLesson testFitnessLesson = new FitnessLesson(testFitnessType, testDateTime);
        testFitnessLessonList.add(testFitnessLesson);

        provideInput("5 1 ");
        FitnessLesson result = WFCBookingSystem.showTimetable(testFitnessLessonList);
        assertTrue(testOut.toString().contains("Please input a valid index"));
        assertEquals(testFitnessLesson, result);
    }

    @Test
    void testShowTimetableInvalidDatatype() {
        List<FitnessLesson> testFitnessLessonList = new ArrayList<>();
        FitnessLesson.FitnessType testFitnessType = FitnessLesson.FitnessType.BoxFit;
        LocalDateTime testDateTime = LocalDateTime.of(2023, 4, 1, 10, 0);
        FitnessLesson testFitnessLesson = new FitnessLesson(testFitnessType, testDateTime);
        testFitnessLessonList.add(testFitnessLesson);

        provideInput("abc 1 ");
        FitnessLesson result = WFCBookingSystem.showTimetable(testFitnessLessonList);
        assertTrue(testOut.toString().contains("Please input a number, or type 'BACK' to back to query"));
        assertEquals(testFitnessLesson, result);
    }
}