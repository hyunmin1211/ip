import java.util.Scanner;

public class Chris {
    public static void main(String[] args) {
        String banner = "  ____ _          _     \n"
                + " / ___| |__  _ __(_)___ \n"
                + "| |   | '_ \\| '__| / __|\n"
                + "| |___| | | | |  | \\__ \\\n"
                + " \\____|_| |_|_|  |_|___/";
        String line = "____________________________________________________________";

        System.out.println(line);
        System.out.println(banner);
        System.out.println("Hello! I'm Chris.");
        System.out.println("What can I do for you?");
        System.out.println(line);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            System.out.println(line);

            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(line);
                break;
            }

            System.out.println(input);
            System.out.println(line);
        }
        scanner.close();
    }
}
