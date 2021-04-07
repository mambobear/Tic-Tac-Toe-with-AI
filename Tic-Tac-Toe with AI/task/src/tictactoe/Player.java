package tictactoe;

abstract class Player {
    abstract Game.Move makeMove();
    final char mark;
    Game game = null;

    Player(char mark) {
        this.mark = mark;
    }

    public void setGame(Game game) {
        this.game = game;
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
