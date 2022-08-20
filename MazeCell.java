package maze;

public class MazeCell {

    private static final String WALL = "\u2588\u2588";
    private static final String PATH = "  ";
    private static final String EXIT_PATH = "//";
    private boolean path;
    private boolean pathToExit;
    private boolean visited;
    private boolean exit = false;
    private final int row;
    private final int column;

    public MazeCell(int row, int column, boolean isPath) {
        this.row = row;
        this.column = column;
        this.path = isPath;
        this.visited = false;
    }

    public MazeCell(int row, int column) {
        this(row, column, false);
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isPath() {
        return path;
    }

    public void setAsPath() {
        this.path = true;
        this.visited = true;
    }

    public void setAsWall() {
        this.path = false;
        this.visited = true;
    }

    public void setVisited(boolean state) {
        visited = state;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setExit() {
        exit = true;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isPathToExit() {
        return pathToExit;
    }

    public void setPathToExit() {
        pathToExit = true;
    }

    @Override
    public String toString() {
        if (isPathToExit()) {
            return EXIT_PATH;
        } else {
            return isPath() ? PATH : WALL;
        }
    }

}