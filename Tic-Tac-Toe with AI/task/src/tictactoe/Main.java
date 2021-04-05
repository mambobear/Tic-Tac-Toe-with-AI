package tictactoe;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        while (game.getStatus() == Game.Status.NOT_FINISHED) {
            game.printGrid();
            game.makeMove();
            game.switchPlayers();
        }
        game.printGameStatus();
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
    private int nEmptyCells = SIZE * SIZE;
    private Status status = Status.NOT_FINISHED;

    public Status getStatus() {
        return status;
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
        Move move = (this.currentPlayer == 'X') ? getUserMove() : makeEasyMove();
        grid[move.row][move.col] = currentPlayer;
        this.nEmptyCells--;
        this.status = getStatusAfter(move);
    }

    private Move makeEasyMove() {
        assert this.currentPlayer == 'O';
        assert this.status == Status.NOT_FINISHED;
        Random rand = new Random(System.currentTimeMillis());
        while (true) {
            int row = rand.nextInt(3);
            int col = rand.nextInt(3);
            if (!cellEmpty(row, col)) continue;
            System.out.println("Making move level \"easy\"");
            return new Move(row, col);
        }
    }

    private boolean cellEmpty(int row, int col) {
        return grid[row][col] == Empty;
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

    public Move getUserMove() {
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
            if (!cellEmpty(row - 1, col - 1)) {
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