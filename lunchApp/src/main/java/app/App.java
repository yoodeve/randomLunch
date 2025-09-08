package app;

import app.service.UserManagementSystem;

import java.util.Scanner;

public class App {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        UserManagementSystem ums = new UserManagementSystem(sc);
        int count = 0;
        while(count < 2){
           ums.showMainMenu(sc);
           count++;
        }
    }
}
