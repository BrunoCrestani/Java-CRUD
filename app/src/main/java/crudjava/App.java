package crudjava;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            Menu.tableMenu(scanner);
        } finally {
            scanner.close();
        }

    }
}