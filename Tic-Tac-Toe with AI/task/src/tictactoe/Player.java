package tictactoe;

abstract class Player {

    final char mark;
    Grid grid = null;

    abstract Game.Status makeMove();

    Player(char mark) {
        this.mark = mark;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public char opponent(char mark) {
        return mark == 'X' ? 'O' : 'X';
    }

    Game.Move winningMoveFor(char mark) {
        return this.grid.winningMoveFor(mark, opponent(mark));
    }
}

class PlayerCreator {
    public static Player createPlayer(String playerType, char mark) {
        if ("easy".equals(playerType)) {
            return new Computer(mark, Computer.ComputerLevel.EASY);
        }

        if ("medium".equals(playerType)) {
            return new Computer(mark, Computer.ComputerLevel.MEDIUM);
        }

        if ("hard".equals(playerType)) {
            return new Computer(mark, Computer.ComputerLevel.HARD);
        }

        if ("user".equals(playerType)) {
            return new Human(mark);
        }
        return null;
    }
}
