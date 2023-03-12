import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class WFCBookingSystem {
    static Database database;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        database = new Database();
        while (true) {
            login();
        }
    }

    public static void login() {
        System.out.println("Welcome to Weekend Fitness Club Booking System");
        System.out.println("Please input your username to log in, or type 'EXIT' to leave the system:");
        String input = scanner.next();
        if (input.equals("EXIT")) {
            System.out.println("Goodbye!");
            System.exit(0);
        } else {
            database.userLogin(input);
            System.out.println(String.format("Welcome, %s", input));
            showMainMenu();
        }
    }

    public static void showMainMenu() {
        System.out.println("Please input the index of action you would like to do:");
        System.out.println("1. Book a group fitness lesson");
        System.out.println("2. Change/Cancel a booking ");
        System.out.println("3. Attend a lesson");
        System.out.println("4. Monthly lesson report");
        System.out.println("5. Monthly champion fitness type report");
        System.out.println("6. Logout");
        try {
            int input = scanner.nextInt();
            switch (input) {
                case 1:
                    bookFitnessLesson();
                    break;
                case 2:
                    updateFitnessLesson();
                    break;
                case 3:
                    attendFitnessLesson();
                    break;
                case 4:
                    monthlyLessonReport();
                    break;
                case 5:
                    monthlyChampionReport();
                    break;
                case 6:
                    logout();
                    break;
                default:
                    System.out.println("Please input a number within 1 to 6.");
                    showMainMenu();
                    break;
            }
        } catch (InputMismatchException e) {
            System.out.println("Please input a number.");
            showMainMenu();
        }
    }

    //book fitness lesson
    public static void bookFitnessLesson() {
        System.out.println("Please select a way to view timetable, or type 'BACK' to back to main menu");
        System.out.println("1. By date");
        System.out.println("2. By fitness type");
        try {
            String input = scanner.next();
            if (input.equals("BACK")) {
                showMainMenu();
            }
            int inputInt = Integer.parseInt(input);
            switch (inputInt) {
                case 1:
                    viewTimetableByDate();
                    break;
                case 2:
                    viewTimetableByFitnessType();
                    break;
                default:
                    System.out.println("Please input a number within 1 to 2.");
                    bookFitnessLesson();
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("Please input a number, or type 'BACK' to back to main menu");
            bookFitnessLesson();
        }
    }

    public static void viewTimetableByDate() {
        System.out.println("Please input a date (yyyy-mm-dd)");
        String input = scanner.next();
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd")
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .toFormatter();
        try {
            LocalDateTime date = LocalDateTime.parse(input, formatter);
            List<FitnessLesson> lessonList = database.getFitnessLessons(date);
            if (lessonList.size() == 0) {
                System.out.println("No lessons provided on that day, please try another date.");
                viewTimetableByDate();
            } else {
                showTimetable(lessonList);
            }
        } catch (DateTimeParseException e) {
            System.out.println("Please input the date in correct format (yyyy-mm-dd).");
            viewTimetableByDate();
        }
    }

    public static void viewTimetableByFitnessType() {
        System.out.println("Please input the index of fitness type");
        FitnessLesson.FitnessType[] fitnessTypeList = FitnessLesson.FitnessType.values();
        for (int i = 0; i < fitnessTypeList.length; i++) {
            System.out.printf("%d. %s%n", i + 1, fitnessTypeList[i].name());
        }
        try {
            int input = scanner.nextInt();
            if (input < 0 || input > fitnessTypeList.length) {
                System.out.println("Please input a valid index.");
            }
            FitnessLesson.FitnessType fitnessType = fitnessTypeList[input - 1];
            List<FitnessLesson> lessonList = database.getFitnessLessons(fitnessType);
            if (lessonList.size() == 0) {
                System.out.println("No lessons provided for this fitness type, please try another fitness type.");
                viewTimetableByDate();
            } else {
                showTimetable(lessonList);
            }
        } catch (InputMismatchException e) {
            System.out.println("Please input a valid index.");
            viewTimetableByFitnessType();
        }
    }

    public static void showTimetable(List<FitnessLesson> lessonList) {
        System.out.println("Please input the index of lesson you would like to book, or type 'BACK' to back to query");
        for (int i = 0; i < lessonList.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, lessonList.get(i).toString());
        }
        boolean error = false;
        do {
            error = false;
            String input = scanner.next();
            if (input.equals("BACK")) {
                bookFitnessLesson();
            } else {
                try {
                    int inputInt = Integer.parseInt(input);
                    if (inputInt < 0 || inputInt > lessonList.size()) {
                        System.out.println("Please input a valid index.");
                        error = true;
                        continue;
                    }
                    Booking booking = database.createBooking(lessonList.get(inputInt));
                    if (booking == null) {
                        error = true;
                    }
                    else {
                        System.out.printf("Booking success. Your booking details:\n%s\n", booking.toString());
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please input a number, or type 'BACK' to back to query");
                    error = true;
                }
            }
        } while (error);
    }

    public static void updateFitnessLesson() {

    }

    public static void attendFitnessLesson() {

    }

    public static void monthlyLessonReport() {

    }

    public static void monthlyChampionReport() {

    }

    public static void logout() {
        database.userLogout();
    }
}