package maze;

import java.util.Scanner;
import java.util.Random;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Maze {

    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();

    private int height;
    private int width;
    private MazeCell[][] grid;

    public MazeCell[][] getGrid() {
        return grid;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void createMaze() {

        System.out.println("Please, enter the size of a maze");
        int input = scanner.nextInt();
        height = input;
        width = input;

        grid = new MazeCell[height][width];

        createGrid();

        Deque<MazeCell> uncheckedCells = new ArrayDeque<>();

        int startingIndex = random.nextInt(height - 2) + 1;
        MazeCell currentPath = grid[startingIndex][0];
        uncheckedCells.offer(currentPath);

        while (!uncheckedCells.isEmpty()) {

            currentPath = uncheckedCells.pollLast();

            if (connectsPaths(currentPath)) {
                currentPath.setAsWall();
                continue;
            } else {
                currentPath.setAsPath();
                if (currentPath.getColumn() == width - 1) {
                    for (int i = 0; i < height; i++) {
                        if (currentPath.getRow() != i) {
                            grid[i][width - 1].setAsWall();
                        }
                    }
                }
            }

            List<MazeCell> neighbors = cellNeighbors(currentPath);
            List<MazeCell> validNeighbors = new ArrayList<>();

            for (MazeCell neighbor : neighbors) {
                if (hasPathNear(neighbor, currentPath)) {
                    neighbor.setAsWall();
                } else if (!uncheckedCells.contains(neighbor)) {
                    uncheckedCells.offer(neighbor);
                    validNeighbors.add(neighbor);
                }
            }

            if (!validNeighbors.isEmpty()) {
                MazeCell randomCell = validNeighbors.get(random.nextInt(validNeighbors.size()));

                while (uncheckedCells.contains(randomCell)) {
                    uncheckedCells.remove(randomCell);
                }
                uncheckedCells.offer(randomCell);
            }
        }

    }

    private void createGrid() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = new MazeCell(i, j);
                if (i == 0 || i == height - 1 || j == 0 || i % 2 == 0 && j % 2 == 0) {
                    grid[i][j].setAsWall();
                }
            }
        }
    }

    private boolean connectsPaths(MazeCell cell) {

        int row = cell.getRow();
        int column = cell.getColumn();
        MazeCell upper = getCellByPos(row - 1, column);  // upper
        MazeCell lower = getCellByPos(row + 1, column);  // lower
        MazeCell leftSide = getCellByPos(row, column - 1);  // left
        MazeCell rightSide = getCellByPos(row, column + 1);  // right

        boolean horizontalConnection = leftSide != null && rightSide != null && leftSide.isPath() && rightSide.isPath();
        boolean verticalConnection = upper != null && lower != null && upper.isPath() && lower.isPath();

        return  horizontalConnection || verticalConnection;
    }

    private MazeCell getCellByPos(int row, int column) {
        try {
            return grid[row][column];
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    private List<MazeCell> cellNeighbors(MazeCell cell) {
        /*
         * Returns a list of the cell's vertical & horizontal direct neighbors
         * */

        int row = cell.getRow();
        int column = cell.getColumn();
        List<MazeCell> neighbors = new ArrayList<>();
        neighbors.add(getCellByPos(row - 1, column));  // upper
        neighbors.add(getCellByPos(row + 1, column));  // lower
        neighbors.add(getCellByPos(row, column - 1));  // left
        neighbors.add(getCellByPos(row, column + 1));  // right

        neighbors.removeIf(Objects::isNull);
        neighbors.removeIf(MazeCell::isVisited);

        return neighbors;
    }

    private boolean hasPathNear(MazeCell cell, MazeCell excl) {

        if (excl == null) {
            return false;
        }

        List<MazeCell> neighbors = cellNeighbors(cell);
        neighbors.remove(excl);
        for (MazeCell neighbor : neighbors) {
            if (neighbor.isPath()) {
                return true;
            }
        }
        return false;
    }

    public void printMaze() {

        System.out.println();
        for (MazeCell[] row : grid) {
            for (MazeCell cell : row) {
                System.out.print(cell.toString());
            }
            System.out.println();
        }
        System.out.println();
    }

    public String getStringMaze() {
        StringBuilder sb = new StringBuilder();
        for (MazeCell[] row : grid) {
            for (MazeCell cell : row) {
                sb.append(cell.toString());
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public boolean createMazeFromString(String stringMaze) {

        String[] rows = stringMaze.split("\n");
        height = rows.length;
        width = rows[0].length() / 2;
        grid = new MazeCell[height][width];

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                grid[i][j] = new MazeCell(i, j);
                if (rows[i].charAt(j * 2) == ' ') {
                    grid[i][j].setAsPath();
                } else if (rows[i].charAt(j * 2) == '\u2588') {
                    grid[i][j].setAsWall();
                } else {
                    return false;
                }
            }
        }
        return true;

    }

}