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
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class WFCBookingSystemTest {
    private final ByteArrayOutputStream testOut = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(testOut));
    }

    @AfterEach
    void tearDown() {
        System.setOut(System.out);
        System.setIn(System.in);
    }

    private void provideInput(String data) {  //stimulate user input
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @Test
    void testShowTimetable1() {
        //Arrange
        List<FitnessLesson> testFitnessLessonList = new ArrayList<>();
        FitnessLesson.FitnessType testFitnessType = FitnessLesson.FitnessType.BoxFit;
        LocalDateTime testDateTime = LocalDateTime.of(2023, 4, 1, 10, 0);
        FitnessLesson testFitnessLesson = new FitnessLesson(testFitnessType, testDateTime);
        testFitnessLessonList.add(testFitnessLesson);   //add one item to fitness lesson list

        provideInput("1");

        //Act
        FitnessLesson result = WFCBookingSystem.showTimetable(testFitnessLessonList);

        //Assert
        assertEquals(testFitnessLesson, result);
    }

    @Test
    void testShowTimetable2() {
        //Arrange
        List<FitnessLesson> testFitnessLessonList = new ArrayList<>();

        //add two items to fitness lesson list
        FitnessLesson.FitnessType testFitnessType1 = FitnessLesson.FitnessType.BoxFit;
        LocalDateTime testDateTime1 = LocalDateTime.of(2023, 4, 1, 10, 0);
        FitnessLesson testFitnessLesson1 = new FitnessLesson(testFitnessType1, testDateTime1);
        testFitnessLessonList.add(testFitnessLesson1);
        FitnessLesson.FitnessType testFitnessType2 = FitnessLesson.FitnessType.Yoga;
        LocalDateTime testDateTime2 = LocalDateTime.of(2023, 4, 1, 16, 0);
        FitnessLesson testFitnessLesson2 = new FitnessLesson(testFitnessType2, testDateTime2);
        testFitnessLessonList.add(testFitnessLesson2);

        provideInput("2");

        //Act
        FitnessLesson result = WFCBookingSystem.showTimetable(testFitnessLessonList);

        //Assert
        assertEquals(testFitnessLesson2, result);
    }

    @Test
    void testShowTimetableBack() {
        //Arrange
        List<FitnessLesson> testFitnessLessonList = new ArrayList<>();
        FitnessLesson.FitnessType testFitnessType = FitnessLesson.FitnessType.BoxFit;
        LocalDateTime testDateTime = LocalDateTime.of(2023, 4, 1, 10, 0);
        FitnessLesson testFitnessLesson = new FitnessLesson(testFitnessType, testDateTime);
        testFitnessLessonList.add(testFitnessLesson);

        provideInput("BACK");

        //Act
        FitnessLesson result = WFCBookingSystem.showTimetable(testFitnessLessonList);

        //Assert
        assertNull(result);
    }

    @Test
    void testShowTimetableInvalidIndex() {
        //Arrange
        List<FitnessLesson> testFitnessLessonList = new ArrayList<>();
        FitnessLesson.FitnessType testFitnessType = FitnessLesson.FitnessType.BoxFit;
        LocalDateTime testDateTime = LocalDateTime.of(2023, 4, 1, 10, 0);
        FitnessLesson testFitnessLesson = new FitnessLesson(testFitnessType, testDateTime);
        testFitnessLessonList.add(testFitnessLesson);

        provideInput("5");  //input exceeds the number of fitness lesson

        try {
            //Act
            WFCBookingSystem.showTimetable(testFitnessLessonList);
        } catch (NoSuchElementException e) {    //only one input provided, expected to throw NoSuchElementException
            //Assert
            assertTrue(testOut.toString().contains("Please input a valid index"));
        }
    }

    @Test
    void testShowTimetableInvalidDatatype() {
        //Arrange
        List<FitnessLesson> testFitnessLessonList = new ArrayList<>();
        FitnessLesson.FitnessType testFitnessType = FitnessLesson.FitnessType.BoxFit;
        LocalDateTime testDateTime = LocalDateTime.of(2023, 4, 1, 10, 0);
        FitnessLesson testFitnessLesson = new FitnessLesson(testFitnessType, testDateTime);
        testFitnessLessonList.add(testFitnessLesson);

        provideInput("abc");    //input with invalid data type

        try {
            //Act
            WFCBookingSystem.showTimetable(testFitnessLessonList);
        } catch (NoSuchElementException e) {   //only one input provided, expected to throw NoSuchElementException
            //Assert
            assertTrue(testOut.toString().contains("Please input a number, or type 'BACK' to back to query"));
        }
    }
}