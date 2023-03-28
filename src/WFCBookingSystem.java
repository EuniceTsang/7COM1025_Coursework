import java.time.DayOfWeek;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

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
        System.out.println("===============================================================");
        System.out.println("Welcome to Weekend Fitness Club Booking System");
        System.out.println("Please input your username to log in, or type 'EXIT' to leave the system:");
        String input = scanner.next();
        if (input.equalsIgnoreCase("EXIT")) {
            System.out.println("Goodbye!");
            System.exit(0);
        } else {
            database.userLogin(input);
            System.out.printf("Welcome, %s\n", input);
            showMainMenu();
        }
    }

    public static void showMainMenu() {
        System.out.println("===============================================================");
        System.out.println("Please input the index of action you would like to do:");
        System.out.println("1. Book a group fitness lesson");
        System.out.println("2. Change/Cancel a booking ");
        System.out.println("3. Attend a lesson");
        System.out.println("4. Monthly lesson report");
        System.out.println("5. Monthly champion fitness type report");
        System.out.println("6. Logout");
        boolean error;
        do {
            error = false;
            try {
                scanner = new Scanner(System.in);
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
                        System.out.println("Please input a number within 1 to 6");
                        error = true;
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please input a number");
                error = true;
            }
        }
        while (error);
    }

    //book fitness lesson
    public static void bookFitnessLesson() {
        System.out.println("===============================================================");
        FitnessLesson lesson = queryFitnessLesson();
        if (lesson == null) {
            showMainMenu();
        } else {
            Booking booking = database.createBooking(lesson);
            if (booking == null) {
                System.out.println("Please select another lesson");
                bookFitnessLesson();
            } else {
                System.out.printf("Success.\nNew booking: {%s}\n", booking.toString());
                showMainMenu();
            }
        }
    }

    //update fitness lesson
    public static void updateFitnessLesson() {
        System.out.println("===============================================================");
        List<Booking> bookingList = database.getBookingList();
        if (bookingList.size() == 0) {
            System.out.println("Currently you have no any booking");
            showMainMenu();
        }
        System.out.println("Here is all your booking");
        for (int i = 0; i < bookingList.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, bookingList.get(i).toString());
        }
        System.out.println("Please input the index of booking you would like to edit, or type 'BACK' to back to main menu");
        boolean error = false;
        do {
            error = false;
            scanner = new Scanner(System.in);
            String input = scanner.next();
            if (input.equalsIgnoreCase("BACK")) {
                showMainMenu();
            } else {
                try {
                    int inputInt = Integer.parseInt(input);
                    if (inputInt < 0 || inputInt > bookingList.size()) {
                        System.out.println("Please input a valid index");
                        error = true;
                        continue;
                    }
                    Booking editBooking = bookingList.get(inputInt - 1);
                    System.out.println("Please select an action to edit the booking");
                    System.out.println("1. Change");
                    System.out.println("2. Cancel");
                    inputInt = scanner.nextInt();
                    switch (inputInt) {
                        case 1:
                            changeBooking(editBooking);
                            break;
                        case 2:
                            cancelBooking(editBooking);
                            break;
                        default:
                            System.out.println("Please input a valid index");
                            error = true;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please input a number, or type 'BACK' to back to query");
                    error = true;
                } catch (InputMismatchException e) {
                    System.out.println("Please input a number");
                    error = true;
                }
            }
        } while (error);
    }

    public static void changeBooking(Booking booking) {
        System.out.println("Please search a lesson you wanna to replace");
        FitnessLesson lesson = queryFitnessLesson();
        System.out.printf("Are you sure you want to change your lesson? (YES/NO)\n {%s} -> {%s}\n", booking.getFitnessLesson().toString(), lesson.toString());
        boolean error = false;
        do {
            error = false;
            scanner = new Scanner(System.in);
            String input = scanner.next().toUpperCase();
            switch (input) {
                case "YES":
                    database.changeBooking(booking, lesson);
                    System.out.printf("Success\nUpdated booking: {%s}\n", booking.toString());
                    showMainMenu();
                    break;
                case "NO":
                    showMainMenu();
                    break;
                default:
                    error = true;
                    System.out.println("Please input YES or NO");
                    break;
            }
        } while (error);
    }

    public static void cancelBooking(Booking booking) {
        System.out.printf("Are you sure you want to cancel this booking? (YES/NO)\n{%s}\n", booking.toString());
        boolean error = false;
        do {
            error = false;
            scanner = new Scanner(System.in);
            String input = scanner.next().toUpperCase();
            switch (input) {
                case "YES":
                    database.cancelBooking(booking);
                    System.out.printf("Success\nCancelled booking: {%s}\n", booking.toString());
                    showMainMenu();
                    break;
                case "NO":
                    showMainMenu();
                    break;
                default:
                    error = true;
                    System.out.println("Please input YES or NO");
                    break;
            }
        } while (error);
    }

    //query fitness lesson by date ot fitness type
    public static FitnessLesson queryFitnessLesson() {
        System.out.println("Please select a way to view timetable, or type 'BACK' to back to last step");
        System.out.println("1. By date");
        System.out.println("2. By fitness type");
        boolean error;
        do {
            try {
                error = false;
                scanner = new Scanner(System.in);
                String input = scanner.next();
                if (input.equalsIgnoreCase("BACK")) {
                    return null;
                }
                int inputInt = Integer.parseInt(input);
                switch (inputInt) {
                    case 1:
                        return queryByDate();
                    case 2:
                        return queryByFitnessType();
                    default:
                        System.out.println("Please input a number within 1 to 2");
                        error = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please input a number, or type 'BACK' to back to main menu");
                error = true;
            }
        }
        while (error);
        return null;
    }

    public static FitnessLesson queryByDate() {
        System.out.println("Please input a weekday");
        System.out.println("1. Saturday");
        System.out.println("2. Sunday");
        boolean error;
        do {
            try {
                error = false;
                scanner = new Scanner(System.in);
                int input = scanner.nextInt();
                if (input < 1 || input > 2) {
                    error = true;
                    System.out.println("Please input a valid index");
                    continue;
                }
                List<FitnessLesson> lessonList = database.getFitnessLessons(input == 1 ? DayOfWeek.SATURDAY : DayOfWeek.SUNDAY);
                if (lessonList.size() == 0) {
                    System.out.println("No lessons provided on that day, please try another date");
                    error = true;
                } else {
                    System.out.printf("Here is all the lesson on %s\n", input);
                    return showTimetable(lessonList);
                }
            } catch (InputMismatchException e) {
                System.out.println("Please input a valid index");
                error = true;
            }
        } while (error);
        return null;
    }

    public static FitnessLesson queryByFitnessType() {
        System.out.println("Please input the index of fitness type");
        FitnessLesson.FitnessType[] fitnessTypeList = FitnessLesson.FitnessType.values();
        for (int i = 0; i < fitnessTypeList.length; i++) {
            System.out.printf("%d. %s%n", i + 1, fitnessTypeList[i].name());
        }
        boolean error;
        do {
            try {
                error = false;
                scanner = new Scanner(System.in);
                int input = scanner.nextInt();
                if (input < 0 || input > fitnessTypeList.length) {
                    System.out.println("Please input a valid index");
                    error = true;
                    continue;
                }
                FitnessLesson.FitnessType fitnessType = fitnessTypeList[input - 1];
                List<FitnessLesson> lessonList = database.getFitnessLessons(fitnessType);
                if (lessonList.size() == 0) {
                    System.out.println("No lessons provided for this fitness type, please try another fitness type");
                    error = true;
                } else {
                    System.out.printf("Here is all the lesson of %s\n", fitnessType.name());
                    return showTimetable(lessonList);
                }
            } catch (InputMismatchException e) {
                System.out.println("Please input a valid index");
                error = true;
            }
        } while (error);
        return null;
    }

    public static FitnessLesson showTimetable(List<FitnessLesson> lessonList) {
        for (int i = 0; i < lessonList.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, lessonList.get(i).toString());
        }
        System.out.println("Please select a lesson, or type 'BACK' to back to query");
        boolean error;
        do {
            error = false;
            scanner = new Scanner(System.in);
            String input = scanner.next();
            if (input.equalsIgnoreCase("BACK")) {
                return null;
            } else {
                try {
                    int inputInt = Integer.parseInt(input);
                    if (inputInt < 0 || inputInt > lessonList.size()) {
                        System.out.println("Please input a valid index");
                        error = true;
                        continue;
                    }
                    return lessonList.get(inputInt - 1);
                } catch (NumberFormatException e) {
                    System.out.println("Please input a number, or type 'BACK' to back to query");
                    error = true;
                }
            }
        } while (error);
        return null;
    }

    //attend fitness lesson
    public static void attendFitnessLesson() {
        System.out.println("===============================================================");
        List<Booking> bookingList = database.getBookingList();
        if (bookingList.size() == 0) {
            System.out.println("Currently you have no any booking");
            showMainMenu();
        }
        System.out.println("Here is all your booking");
        for (int i = 0; i < bookingList.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, bookingList.get(i).toString());
        }
        System.out.println("Please input the index of booking you would like to attend, or type 'BACK' to back to main menu");
        boolean error;
        do {
            error = false;
            scanner = new Scanner(System.in);
            String input = scanner.next();
            if (input.equalsIgnoreCase("BACK")) {
                showMainMenu();
            } else {
                try {
                    int inputInt = Integer.parseInt(input);
                    if (inputInt < 0 || inputInt > bookingList.size()) {
                        System.out.println("Please input a valid index");
                        error = true;
                        continue;
                    }
                    Booking booking = bookingList.get(inputInt - 1);
                    System.out.printf("Thanks for attend the lesson {%s}\n", booking.toString());
                    System.out.println("Please rate (1-5) the lesson");
                    boolean rateError;
                    do {
                        try {
                            rateError = false;
                            scanner = new Scanner(System.in);
                            int rate = scanner.nextInt();
                            if (rate < 1 || rate > 5) {
                                System.out.println("Please input a number from 1-5");
                                rateError = true;
                                continue;
                            }
                            System.out.println("Please leave a comment for the lesson");
                            String comment = scanner.next();
                            booking.attend(rate, comment);
                            System.out.println("Thanks for your feedback!");
                            showMainMenu();
                        } catch (InputMismatchException e) {
                            System.out.println("Please input a number");
                            rateError = true;
                        }
                    } while (rateError);
                } catch (NumberFormatException e) {
                    System.out.println("Please input a number, or type 'BACK' to back to query");
                    error = true;
                }
            }
        } while (error);
    }

    public static void monthlyLessonReport() {
        System.out.println("===============================================================");
        System.out.println("Please input the month that you would like to view the report, or type 'BACK' to back to main menu");
        boolean error;
        do {
            error = false;
            scanner = new Scanner(System.in);
            String input = scanner.next();
            if (input.equalsIgnoreCase("BACK")) {
                showMainMenu();
            } else {
                try {
                    int inputInt = Integer.parseInt(input);
                    if (inputInt < 1 || inputInt > 12) {
                        System.out.println("Please input a valid month (1-12)");
                        error = true;
                        continue;
                    }
                    List<FitnessLesson> lessonOfMonth = database.getFitnessLessons(inputInt);
                    String monthStr = Month.of(inputInt).getDisplayName(TextStyle.FULL, Locale.getDefault());
                    if (lessonOfMonth.size() == 0) {
                        System.out.printf("There is no lesson in %s\n", monthStr);
                    } else {
                        System.out.printf("There is the monthly report of %s:\n", monthStr);
                        for (FitnessLesson lesson : lessonOfMonth) {
                            System.out.println(lesson.generateReport());
                        }
                    }
                    showMainMenu();
                } catch (NumberFormatException e) {
                    System.out.println("Please input a number, or type 'BACK' to back to query");
                    error = true;
                }
            }
        } while (error);
    }

    public static void monthlyChampionReport() {
        System.out.println("===============================================================");
        System.out.println("Please input the month that you would like to view the report, or type 'BACK' to back to main menu");
        boolean error;
        do {
            error = false;
            scanner = new Scanner(System.in);
            String input = scanner.next();
            if (input.equalsIgnoreCase("BACK")) {
                showMainMenu();
            } else {
                try {
                    int inputInt = Integer.parseInt(input);
                    if (inputInt < 1 || inputInt > 12) {
                        System.out.println("Please input a valid month (1-12)");
                        error = true;
                        continue;
                    }
                    Map<FitnessLesson.FitnessType, Double> fitnessTypeIncomeMap = database.getFitnessTypeIncomeMap(inputInt);
                    List<Map.Entry<FitnessLesson.FitnessType, Double>> sortedMapEntry = new ArrayList<>(fitnessTypeIncomeMap.entrySet());
                    sortedMapEntry.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
                    String monthStr = Month.of(inputInt).getDisplayName(TextStyle.FULL, Locale.getDefault());

                    System.out.printf("There is the monthly champion fitness type report of %s:\n", monthStr);
                    int rank = 1;
                    for (Map.Entry<FitnessLesson.FitnessType, Double> entry : sortedMapEntry) {
                        System.out.printf("%d. %s, Total income: £%.2f\n", rank, entry.getKey().name(), entry.getValue());
                        rank++;
                    }
                    showMainMenu();
                } catch (NumberFormatException e) {
                    System.out.println("Please input a number, or type 'BACK' to back to query");
                    error = true;
                }
            }
        } while (error);
    }

    public static void logout() {
        database.userLogout();
    }
}