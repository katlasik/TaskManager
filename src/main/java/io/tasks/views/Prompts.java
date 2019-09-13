package io.tasks.views;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public interface Prompts {

    default int choice(int limit, Scanner scanner) {

        int choice = 0;

        while (choice <= 0 || choice > limit) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ignored) {
            }
        }
        System.out.println();

        return choice;
    }

    default boolean yesNo(Scanner scanner) {

        String choice = "";

        while (!choice.equals("n") && !choice.equals("t")) {
            try {
                choice = scanner.nextLine().toLowerCase();
            } catch (InputMismatchException ignored) {
            }
        }
        System.out.println();

        return choice.equals("t");
    }

    default String text(Scanner scanner) {
        String result = "";
        while (result.trim().isEmpty()) {
            result = scanner.nextLine();
        }
        System.out.println();
        return result;
    }

    default LocalDate date(Scanner scanner) {
        LocalDate date = null;
        while (date == null) {
            try {
                date = LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("Nieprawidłowy format daty!");
            }
        }
        System.out.println();
        return date;
    }

}
