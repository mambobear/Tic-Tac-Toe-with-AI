package tictactoe;

import java.util.Random;

class Computer extends Player {

    enum ComputerLevel {
        EASY, MEDIUM, HARD
    }

    ComputerLevel level;

    Computer(char mark, ComputerLevel level) {
        super(mark);
        this.level = level;
    }

    public Game.Status makeMove() {
        Game.Move move = null;
        switch (this.level) {
            case EASY:
                System.out.println("Making move level \"easy\"");
                move = findEasyMove();
                break;
            case MEDIUM:
                System.out.println("Making move level \"medium\"");
                move = findMediumMove();
                break;
            case HARD:
                System.out.println("Making move level \"hard\"");
                move = findHardMove();
                break;
        }
        assert move != null;
        this.grid.markCell(move.getRow(), move.getCol(), this.mark);
        return this.grid.statusAfterMove(move.getRow(), move.getCol());
    }

    private Game.Move findHardMove() {
        Game.Move bestMove = null;
        int bestScore = Integer.MIN_VALUE;
        for (Game.Move move : this.grid.availableMoves()) {
            int score = evaluateMove(move, this.mark);
            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }
        return bestMove;
    }

    private int evaluateMove(Game.Move move, char player) {

        if (this.grid.completesLine(move, player)) {
            return Integer.MAX_VALUE;
        }

        int score;
        this.grid.markCell(move.getRow(), move.getCol(), player);

        if (this.grid.isDraw()) {
            score = 0;
        } else {
            char opponent = opponent(player);
            score = - evaluatePosition(opponent);
        }
        this.grid.unmarkCell(move.getRow(), move.getCol(), player);
        return score;
    }

    private int evaluatePosition(char player) {
        int bestScore = Integer.MIN_VALUE;
        for (Game.Move move : this.grid.availableMoves()) {
            int score = evaluateMove(move, player);
            if (score > bestScore) {
                bestScore = score;
            }
        }
        return bestScore;
    }

    private Game.Move findMediumMove() {
        Game.Move move = winningMoveFor(this.mark);
        if (move != null) return move;

        move = winningMoveFor(this.opponent(this.mark));
        if (move != null) return move;

        return findEasyMove();
    }

    private Game.Move findEasyMove() {
        Random rand = new Random(System.currentTimeMillis());
        while (true) {
            int row = rand.nextInt(3);
            int col = rand.nextInt(3);
            if (!this.grid.isCellEmpty(row, col)) continue;
            return new Game.Move(row, col);
        }
    }
}
