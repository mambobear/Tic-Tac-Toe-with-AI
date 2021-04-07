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

    public Game.Move makeMove() {
        switch (this.level) {
            case EASY: return makeEasyMove();
            case MEDIUM: return makeMediumMove();
            case HARD: return makeHardMove();
        }
        return null;
    }

    private Game.Move makeHardMove() {
        return null;
    }

    private Game.Move makeMediumMove() {
        Game.Move move = game.winningMoveFor(this.mark);
        if (move != null) return move;

        move = game.winningMoveFor(this.game.opponent(this.mark));
        if (move != null) return move;

        return makeEasyMove();
    }

    private Game.Move makeEasyMove() {
        Random rand = new Random(System.currentTimeMillis());
        while (true) {
            int row = rand.nextInt(3);
            int col = rand.nextInt(3);
            if (!this.game.cellEmpty(row, col)) continue;
            System.out.println("Making move level \"easy\"");
            return new Game.Move(row, col);
        }
    }
}
