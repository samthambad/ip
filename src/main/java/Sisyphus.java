import java.util.Scanner;
import java.util.ArrayList;
public class Sisyphus {
    public static void main(String[] args) {
        String logo = """
        ░▒▓███████▓▒░▒▓█▓▒░░▒▓███████▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓███████▓▒░░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░░▒▓███████▓▒░ 
        ░▒▓█▓▒░      ░▒▓█▓▒░▒▓█▓▒░      ░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░        
        ░▒▓█▓▒░      ░▒▓█▓▒░▒▓█▓▒░      ░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░        
        ░▒▓██████▓▒░░▒▓█▓▒░░▒▓██████▓▒░ ░▒▓██████▓▒░░▒▓███████▓▒░░▒▓████████▓▒░▒▓█▓▒░░▒▓█▓▒░░▒▓██████▓▒░  
              ░▒▓█▓▒░▒▓█▓▒░      ░▒▓█▓▒░  ░▒▓█▓▒░   ░▒▓█▓▒░      ░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░      ░▒▓█▓▒░ 
              ░▒▓█▓▒░▒▓█▓▒░      ░▒▓█▓▒░  ░▒▓█▓▒░   ░▒▓█▓▒░      ░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░      ░▒▓█▓▒░ 
        ░▒▓███████▓▒░░▒▓█▓▒░▒▓███████▓▒░   ░▒▓█▓▒░   ░▒▓█▓▒░      ░▒▓█▓▒░░▒▓█▓▒░░▒▓██████▓▒░░▒▓███████▓▒░  
          """;
        String divider = "-----------------------------";
        String welcomeMessage = "Hello, I am Sisyphus, what can I do for you?";
        System.out.println(logo);
        System.out.println(welcomeMessage);
        System.out.println(divider);
        ArrayList<String> todoList = new ArrayList<String>();
        String input = "";
        Scanner myObj = new Scanner(System.in);
        while (!input.equals("bye")) {
          input = myObj.nextLine();
          System.out.println(divider);
          switch (input) {
            case "bye":
              System.out.println("    See you!");
              break;
            case "list":
              int i = 1;
              for (String item : todoList) {
                System.out.println("    " + i++ + ". " + item);
              }
              break;
            default:
              todoList.add(input);
              System.out.println("    added: " + input);
              break;
          }
          System.out.println(divider);
        }
    }
}
