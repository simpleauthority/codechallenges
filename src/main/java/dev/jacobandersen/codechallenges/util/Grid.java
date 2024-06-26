package dev.jacobandersen.codechallenges.util;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Grid<T> {
    public static <T> Grid<T> create(Stream<String> input, Function<String, List<T>> mapper) {
        final List<List<T>> grid = input.map(mapper).collect(Collectors.toList());
        if (!grid.stream().map(List::size).allMatch(size -> grid.get(0).size() == size)) {
            throw new IllegalArgumentException("Received grid had rows with varying columns - invalid");
        }

        return new Grid<>(grid.size(), grid.get(0).size(), grid);
    }

    private final int rows;
    private final int cols;
    private int currentRow;
    private int currentCol;
    private final List<List<T>> grid;

    public Grid(int rows, int cols, List<List<T>> grid) {
        this(rows, cols, 0, 0, grid);
    }

    public Grid(int rows, int cols, int currentRow, int currentCol, List<List<T>> grid) {
        this.rows = rows;
        this.cols = cols;
        this.currentRow = currentRow;
        this.currentCol = currentCol;
        this.grid = grid;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public int getCurrentCol() {
        return currentCol;
    }

    public T get() {
        return grid.get(currentRow).get(currentCol);
    }

    public void set(T value) {
        grid.get(currentRow).set(currentCol, value);
    }

    public void move(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IllegalArgumentException("Tried to move current cell beyond boundaries");
        }

        currentRow = row;
        currentCol = col;
    }

    public T peek(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return null;
        }

        return grid.get(row).get(col);
    }

    public Grid<T> moveNorth() {
        move(currentRow - 1, currentCol);
        return this;
    }

    public T peekNorth() {
        return peek(currentRow - 1, currentCol);
    }

    public Grid<T> moveSouth() {
        move(currentRow + 1, currentCol);
        return this;
    }

    public T peekSouth() {
        return peek(currentRow + 1, currentCol);
    }

    public Grid<T> moveWest() {
        move(currentRow, currentCol - 1);
        return this;
    }

    public T peekWest() {
        return peek(currentRow, currentCol - 1);
    }

    public Grid<T> moveEast() {
        move(currentRow, currentCol + 1);
        return this;
    }

    public T peekEast() {
        return peek(currentRow, currentCol + 1);
    }

    public Grid<T> moveCellToFirstOccurrence(T needle) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (peek(row, col).equals(needle)) {
                    move(row, col);
                    return this;
                }
            }
        }

        throw new IllegalArgumentException("Needle not found in Grid.");
    }

    public Grid<T> move(Direction direction) {
        return switch (direction) {
            case NORTH -> moveNorth();
            case EAST -> moveEast();
            case WEST -> moveWest();
            case SOUTH -> moveSouth();
        };
    }

    public T peek(Direction direction) {
        return switch (direction) {
            case NORTH -> peekNorth();
            case EAST -> peekEast();
            case WEST -> peekWest();
            case SOUTH -> peekSouth();
        };
    }

    public int[] peekPosition(Direction direction) {
        int[] position = new int[2];
        move(direction);
        position[0] = getCurrentRow();
        position[1] = getCurrentCol();
        move(direction.getOpposite());
        return position;
    }

    public Grid<T> copy() {
        return new Grid<>(rows, cols, currentRow, currentCol, grid);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Grid {\n" +
                "  rows =" + rows + "\n" +
                "  cols =" + cols + "\n" +
                "  currentRow =" + currentRow + "\n" +
                "  currentCol =" + currentCol + "\n" +
                "  grid = {\n");

        grid.forEach(row -> builder.append("    ").append(row).append("\n"));

        builder.append("}");

        return builder.toString();
    }
}
