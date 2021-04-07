package tictactoe;

class Grid {
    class LineStats {

        private int numX;
        private int numO;

        LineStats() {
            this.numX = 0;
            this.numO = 0;
        }

        private void incX() {
            if (this.numX < 3) this.numX++;
        }

        private void incO() {
            if (this.numO < 3) this.numO++;
        }

        public void increment(char mark) {
            if (mark == 'X') this.incX();
            if (mark == 'O') this.incO();
        }

        public int numFor(char player) {
            assert player == 'X' || player == 'O';
            if (player == 'X') return numX;
            else return numO;
        }
    }

    private final char Empty = ' ';

    private final int ROWS = 0;
    private final int COLUMNS = 1;

    private final int SIZE = 3;
    private final char[][] grid = {
            {Empty, Empty, Empty},
            {Empty, Empty, Empty},
            {Empty, Empty, Empty},
    };

    private int nEmptyCells = SIZE * SIZE;

    LineStats[][] rowAndColumnCounts = new LineStats[2][SIZE];
    LineStats mainDiagonal = new LineStats();
    LineStats antiDiagonal = new LineStats();

    Grid() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < SIZE; j++) {
                this.rowAndColumnCounts[i][j] = new LineStats();
            }
        }
    }


    // Public methods
    public String toString() {
        StringBuilder strb = new StringBuilder();
        strb.append("---------\n");
        for (char[] row : grid) {
            strb.append("| ");
            for (char c : row) {
                strb.append(c).append(" ");
            }
            strb.append("|\n");
        }
        strb.append("---------\n");
        return strb.toString();
    }

    public boolean hasLineAt(int row, int col) {
        char player = this.grid[row][col];
        return hasRowAt(row, player) || hasColumnAt(col, player) || hasDiagonalAt(row, col, player);
    }

    public Game.Move winningMoveFor(char player, char opponent) {

        LineStats stats;
        for (int i = 0; i < SIZE; i++) {
            stats = rowAndColumnCounts[ROWS][i];
            if (stats.numFor(player) == 2 && stats.numFor(opponent) == 0) {
                for (int col = 0; col < SIZE; col++) {
                    if (this.grid[i][col] == Empty) return new Game.Move(i, col);
                }
            }
            stats = rowAndColumnCounts[COLUMNS][i];
            if (stats.numFor(player) == 2 && stats.numFor(opponent) == 0) {
                for (int row = 0; row < SIZE; row++) {
                    if (this.grid[row][i] == Empty) return new Game.Move(row, i);
                }
            }
        }

        if (mainDiagonal.numFor(player) == 2 && mainDiagonal.numFor(opponent) == 0) {
            for (int i = 0; i < SIZE; i++) {
                if (this.grid[i][i] == Empty) return new Game.Move(i, i);
            }
        }

        if (antiDiagonal.numFor(player) == 2 && antiDiagonal.numFor(opponent) == 0) {
            for (int i = 0; i < SIZE; i++) {
                if (this.grid[i][SIZE - 1 - i] == Empty) return new Game.Move(i, SIZE - 1 - i);
            }
        }

        return null;
    }

    public void markMove(Game.Move move, char mark) {
        grid[move.getRow()][move.getCol()] = mark;
        this.nEmptyCells--;
        int row = move.getRow();
        int col = move.getCol();
        rowAndColumnCounts[ROWS][row].increment(mark);
        rowAndColumnCounts[COLUMNS][col].increment(mark);
        if (row == col) mainDiagonal.increment(mark);
        if (row + col == SIZE - 1) antiDiagonal.increment(mark);
    }

    public boolean isCellEmpty(int row, int col) {
        return this.grid[row][col] == Empty;
    }

    public boolean isFull() {
        return this.nEmptyCells == 0;
    }


    // Private methods
    private boolean hasDiagonalAt(int row, int col, char player) {
        if (row == col) {
            if (player == 'X') return mainDiagonal.numX == SIZE;
            if (player == 'O') return mainDiagonal.numO == SIZE;
        }

        if (row + col == SIZE - 1) {
            if (player == 'X') return mainDiagonal.numX == SIZE;
            if (player == 'O') return mainDiagonal.numO == SIZE;
        }

        return false;
    }

    private boolean hasColumnAt(int col, char player) {
        if (player == 'X') {
            return this.rowAndColumnCounts[COLUMNS][col].numO == SIZE;
        }
        if (player == 'O') {
            return this.rowAndColumnCounts[COLUMNS][col].numO == SIZE;
        }
        return false;
    }

    private boolean hasRowAt(int row, char player) {
        if (player == 'X') {
            return this.rowAndColumnCounts[ROWS][row].numX == SIZE;
        }
        if (player == 'O') {
            return this.rowAndColumnCounts[ROWS][row].numO == SIZE;
        }
        return false;
    }


}
