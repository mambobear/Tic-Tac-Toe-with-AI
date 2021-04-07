package tictactoe;

import java.util.Scanner;

class Human extends Player {
    Human(char mark) {
        super(mark);
    }

    public Game.Move makeMove() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter the coordinates: ");
            int row;
            int col;

            String[] input = scanner.nextLine().split("\\s+");
            String rowStr = input[0];
            try {
                row = getCoordinate(rowStr);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                continue;
            }

            String colStr = (input.length == 1) ? scanner.next() : input[1];

            try {
                col = getCoordinate(colStr);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                continue;
            }
            if (!this.game.cellEmpty(row - 1, col - 1)) {
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }
            return new Game.Move(row - 1, col - 1);
        }
    }

    private int getCoordinate(String coordStr) {
        try {
            int coord = Integer.parseInt(coordStr);
            if (coord < 1 || coord > 3) {
                throw new IllegalArgumentException("Coordinates should be from 1 to 3!");
            }
            return  coord;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("You should enter numbers!");
        }
    }
}
