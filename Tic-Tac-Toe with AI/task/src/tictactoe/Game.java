package tictactoe;

import java.util.ArrayList;

class Game {

    static final ArrayList<String> playerTypes = new ArrayList<>();

    static {
        playerTypes.add("easy");
        playerTypes.add("medium");
        playerTypes.add("hard");
        playerTypes.add("user");
    }

    enum Status {
        NOT_FINISHED, DRAW, X_WINS, O_WINS
    }

    private final Grid grid;

    private Status status = Status.NOT_FINISHED;

    private final Player[] players;
    private int currentPlayerIndex = 0;

    Game(String player1Name, String player2Name) {

        Player playerX = PlayerCreator.createPlayer(player1Name, 'X');
        Player playerO = PlayerCreator.createPlayer(player2Name, 'O');

        this.grid = new Grid();

        assert playerX != null;
        assert playerO != null;
        playerX.setGrid(this.grid);
        playerO.setGrid(this.grid);

        this.players = new Player[]{playerX, playerO};
    }

    public void start() {

        while (this.status == Status.NOT_FINISHED) {
            this.printGrid();
            this.makeMove();
            this.switchPlayers();
        }
        this.printGameStatus();
    }

    public void printGrid() {
        System.out.println(this.grid);
    }

    public void makeMove() {
        assert this.status == Status.NOT_FINISHED;
        this.status = this.currentPlayer().makeMove();
    }

    void switchPlayers() {
        this.currentPlayerIndex = (this.currentPlayerIndex + 1) % 2;
    }

    private Player currentPlayer() {
        return this.players[this.currentPlayerIndex];
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

    static class Move {

        private final int row;
        private final int col;

        Move(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }
    }
}
