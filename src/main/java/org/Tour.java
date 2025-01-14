package org;

import com.sun.tools.javac.Main;

import java.util.Scanner;

public class Tour {
    public void tourManagement() {
        int choice;
        Scanner s = new Scanner(System.in);
        System.out.println("===== Tour Management =====");
        System.out.println("1. Add a new tour");
        System.out.println("2. Update information");
        System.out.println("3. Delete tour");
        System.out.println("4. Show tour list");
        System.out.println("0. Return to main menu");
        System.out.print("Please choose: ");
        choice = s.nextInt();
        switch(choice) {
            case 0:
                break;
        }
    }
}
