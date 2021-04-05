package tictactoe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the cells: ");
        String state = scanner.next();

        Game game = new Game(state);
        game.printGrid();
        game.makeMove();
        game.printGameStatus();
        game.switchPlayers();
    }
}

class Game {

    enum Status {
        NOT_FINISHED, DRAW, X_WINS, O_WINS
    }

    static class Move {
        private final int row;
        private final int col;

        Move(int row, int col) {
            this.row = row;
            this.col = col;
        }

    }

    private final char Empty = ' ';
    private final int SIZE = 3;
    private final char[][] grid = {
            {Empty, Empty, Empty},
            {Empty, Empty, Empty},
            {Empty, Empty, Empty},
    };

    private char currentPlayer = 'X';
    private int nEmptyCells;
    private Status status;

    Game(String state) {
        int i = 0;
        int count = 0;
        this.nEmptyCells = 0;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                char ch = state.charAt(i++);
                if (ch != '_') {
                    grid[row][col] = ch;
                    count++;
                }
            }
        }
        if (count % 2 == 1) currentPlayer = 'O';
        this.nEmptyCells = grid.length * grid.length - count;

        // TODO: Determine the status of the game
        this.status = Status.NOT_FINISHED;
    }

    public void printGrid() {
        System.out.println("---------");
        for (char[] row : grid) {
            System.out.print("| ");
            for (char c : row) {
                System.out.print(c + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    public void makeMove() {
        Move move = getMove();
        grid[move.row][move.col] = currentPlayer;
        this.nEmptyCells--;
        this.status = getStatusAfter(move);
    }

    void switchPlayers() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    void printGameStatus() {
        printGrid();
        switch (this.status) {
            case DRAW:
                System.out.println("Draw");
                break;
            case O_WINS:
                System.out.println("O wins");
                break;
            case X_WINS:
                System.out.println("X wins");
                break;
            case NOT_FINISHED:
                System.out.println("Game not finished");
                break;
        }
    }

    private Status getStatusAfter(Move move) {
        if (hasLineAt(move)) return (this.currentPlayer == 'X') ? Status.X_WINS : Status.O_WINS;
        if (this.nEmptyCells == 0) return Status.DRAW;
        return Status.NOT_FINISHED;
    }

    private boolean hasLineAt(Move move) {
        return hasRowAt(move) || hasColumnAt(move) || hasDiagonalAt(move);
    }

    private boolean hasDiagonalAt(Move move) {
        char player = this.grid[move.row][move.col];

        if (move.row == move.col) {
            for (int i = 0; i < SIZE; i++) {
                if (grid[i][i] != player) return false;

            }
            return true;
        }

        if (move.row + move.col == SIZE - 1) {
            for (int i = 0; i < SIZE; i++) {
                if (grid[i][SIZE - 1 - i] != player) return false;
            }
            return true;
        }

        return false;
    }

    private boolean hasColumnAt(Move move) {
        char player = this.grid[move.row][move.col];
        for (int row = 0; row < SIZE; row++) {
            if (this.grid[row][move.col] != player) return false;
        }
        return true;
    }

    private boolean hasRowAt(Move move) {
        char player = this.grid[move.row][move.col];
        for (int col = 0; col < SIZE; col++) {
            if (this.grid[move.row][col] != player) return false;
        }
        return true;
    }

    public Move getMove() {
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

            if (grid[row - 1][col - 1] != Empty) {
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }
            return new Move(row - 1, col - 1);
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