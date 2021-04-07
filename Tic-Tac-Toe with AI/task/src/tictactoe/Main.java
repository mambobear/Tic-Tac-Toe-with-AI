package tictactoe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Input command: ");
            String[] command = scanner.nextLine().split("\\s+");
            if (!isValid(command)) {
                System.out.println("Bad parameters!");
                continue;
            }
            String commandName = command[0];
            if ("exit".equals(commandName)) return;

            String player1Name = command[1];
            String player2Name = command[2];


            Player player1 = PlayerCreator.createPlayer(player1Name, 'X');
            Player player2 = PlayerCreator.createPlayer(player2Name, 'O');

            Game game = new Game(player1, player2);
            game.start();
        }
    }

    private static boolean isValid(String[] command) {
        if (command.length == 1 && "exit".equals(command[0])) return true;

        if (command.length == 3) {
            if ("start".equals(command[0])) {
                return Game.playerTypes.contains(command[1]) && Game.playerTypes.contains(command[2]);
            }
        }
        return false;
    }
}