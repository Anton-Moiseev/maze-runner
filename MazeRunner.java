package maze;

import java.util.Random;
import java.util.Deque;
import java.util.ArrayDeque;

public class MazeRunner {

    private Maze mazeObject;
    private MazeCell[][] grid;
    private int height;
    private int width;
    private MazeCell start;
    private final Deque<MazeCell> stack = new ArrayDeque();

    private final Random random = new Random();

    public MazeRunner(Maze maze) {
        mazeObject = maze;
        grid = maze.getGrid();
        height = maze.getHeight();
        width = maze.getWidth();
        resetVisited(maze);
        setExits();
    }

    private void resetVisited(Maze maze) {
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                grid[i][j].setVisited(false);
            }
        }
    }

    private void setExits() {
        for (int i = 0; i < width; ++i) {
            if (grid[i][0].isPath()) {
                grid[i][0].setExit();
                start = grid[i][0];
            }
            if (grid[i][width - 1].isPath()) {
                grid[i][width - 1].setExit();
            }
        }
    }

    public void runMaze() {

        stack.push(start);
        start.setVisited(true);

        checkNeighbours(start);

    }

    private void checkNeighbours(MazeCell cell) {

        try {
            if (grid[cell.getRow() - 1][cell.getColumn()].isPath()) {
                if (!grid[cell.getRow() - 1][cell.getColumn()].isVisited()) {
                    grid[cell.getRow() - 1][cell.getColumn()].setVisited(true);
                    stack.push(grid[cell.getRow() - 1][cell.getColumn()]);
                    if (grid[cell.getRow() - 1][cell.getColumn()].isExit()) {
                        colorPathToExit();
                        mazeObject.printMaze();
                    } else {
                        checkNeighbours(grid[cell.getRow() - 1][cell.getColumn()]);
                    }
                }
            }
        } catch (Exception e) {

        }

        try {
            if (grid[cell.getRow() + 1][cell.getColumn()].isPath()) {
                if (!grid[cell.getRow() + 1][cell.getColumn()].isVisited()) {
                    grid[cell.getRow() + 1][cell.getColumn()].setVisited(true);
                    stack.push(grid[cell.getRow() + 1][cell.getColumn()]);
                    if (grid[cell.getRow() + 1][cell.getColumn()].isExit()) {
                        colorPathToExit();
                        mazeObject.printMaze();
                    } else {
                        checkNeighbours(grid[cell.getRow() + 1][cell.getColumn()]);
                    }
                }
            }
        } catch (Exception e) {

        }

        try {
            if (grid[cell.getRow()][cell.getColumn() - 1].isPath()) {
                if (!grid[cell.getRow()][cell.getColumn() - 1].isVisited()) {
                    grid[cell.getRow()][cell.getColumn() - 1].setVisited(true);
                    stack.push(grid[cell.getRow()][cell.getColumn() - 1]);
                    if (grid[cell.getRow()][cell.getColumn() - 1].isExit()) {
                        colorPathToExit();
                        mazeObject.printMaze();
                    } else {
                        checkNeighbours(grid[cell.getRow()][cell.getColumn() - 1]);
                    }
                }
            }
        } catch (Exception e) {

        }

        try {
            if (grid[cell.getRow()][cell.getColumn() + 1].isPath()) {
                if (!grid[cell.getRow()][cell.getColumn() + 1].isVisited()) {
                    grid[cell.getRow()][cell.getColumn() + 1].setVisited(true);
                    stack.push(grid[cell.getRow()][cell.getColumn() + 1]);
                    if (grid[cell.getRow()][cell.getColumn() + 1].isExit()) {
                        colorPathToExit();
                        mazeObject.printMaze();
                    } else {
                        checkNeighbours(grid[cell.getRow()][cell.getColumn() + 1]);
                    }
                }
            }
        } catch (Exception e) {

        }

        if (stack.size() > 0) {
            stack.pop();
        }

    }

    private void colorPathToExit() {
        int max = stack.size();
        for (int i = 0; i < max; ++i) {
            stack.pop().setPathToExit();
        }
    }

}
