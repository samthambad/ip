import java.util.Scanner;
import java.util.ArrayList;
public class Sisyphus {
  public static void printList(ArrayList<String> todoList, ArrayList<Boolean> doneList) {
    for (int i = 0; i < todoList.size(); i++) {
      int listIndex = i + 1;
      if (doneList.get(i)) {
        System.out.println("    " + listIndex + ". [X] " + todoList.get(i));
      } else {
        System.out.println("    " + listIndex + ". [ ] " + todoList.get(i));
      }
    }
  }
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
        ArrayList<Boolean> doneList = new ArrayList<>();
        String input = "";
        Scanner myObj = new Scanner(System.in);
        while (!input.equals("bye")) {
          input = myObj.nextLine();
          String[] inputArr = input.split(" ");
          System.out.println(divider);
          switch (inputArr[0]) {
            case "bye":
              System.out.println("    See you!");
              break;
            case "list":
              printList(todoList, doneList);
              break;
            case "mark":
              if (inputArr[1].length() > 0) {
                int number = Integer.parseInt(inputArr[1]);
                doneList.set(number-1, true);
                System.out.println("Okay, task " + number + " is done");
                printList(todoList, doneList);
              }
              break;
            case "unmark":
              if (inputArr[1].length() > 0) {
                int number = Integer.parseInt(inputArr[1]);
                doneList.set(number-1, false);
                System.out.println("Okay, task " + number + " is not done yet");
                printList(todoList, doneList);
              }
              break;
            default:
              if (input.length() == 0) break;
              todoList.add(input);
              doneList.add(false);
              System.out.println("    added: " + input);
              break;
          }
          System.out.println(divider);
        }
    }
}
