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
    private Player currentPlayer;
    private Status status = Status.NOT_FINISHED;

    private final Player[] players;
    private int currentPlayerIndex = 0;

    Game(Player playerX, Player playerO) {

        this.grid = new Grid();

        this.players = new Player[]{playerX, playerO};
        playerX.setGame(this);
        playerO.setGame(this);
        this.currentPlayer = playerX;
    }

    public void start() {
        while (this.getStatus() == Status.NOT_FINISHED) {
            this.printGrid();
            this.makeMove();
            this.switchPlayers();
        }
        this.printGameStatus();
    }

    public Status getStatus() {
        return status;
    }

    public void printGrid() {
        System.out.println(this.grid);
    }

    public void makeMove() {
        assert this.status == Status.NOT_FINISHED;
        Move move = this.currentPlayer.makeMove();
        this.grid.markMove(move, this.currentPlayer.mark);
        this.status = getStatusAfter(move);
    }

    public char opponent(char mark) {
        return mark == 'X' ? 'O' : 'X';
    }

    public Move winningMoveFor(char mark) {
        return this.grid.winningMoveFor(mark, opponent(mark));
    }

    boolean cellEmpty(int row, int col) {
        return grid.isCellEmpty(row, col);
    }

    void switchPlayers() {
        this.currentPlayerIndex = (this.currentPlayerIndex + 1) % 2;
        this.currentPlayer = players[this.currentPlayerIndex];
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
        if (hasLineAt(move)) return (this.currentPlayer.mark == 'X') ? Status.X_WINS : Status.O_WINS;
        if (this.grid.isFull()) return Status.DRAW;
        return Status.NOT_FINISHED;
    }

    private boolean hasLineAt(Move move) {
        return this.grid.hasLineAt(move.getRow(), move.getCol());
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
