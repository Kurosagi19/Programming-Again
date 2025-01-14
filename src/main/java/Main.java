import org.Tour;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int choice;
        Scanner s = new Scanner(System.in);
        Tour tour = new Tour();
        System.out.println("===== Menu =====");
        System.out.println("1. Tour management");
        System.out.println("2. Function");
        System.out.println("0. Exit program");
        System.out.print("Please choose: ");
        choice = s.nextInt();
        switch(choice) {
            case 1:
                tour.tourManagement();
        }
    }
}
