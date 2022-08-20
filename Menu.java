package maze;

import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Menu {

    private final Scanner scanner = new Scanner(System.in);
    private Maze currentMaze;

    public void showMenu() {

        StringBuilder sb = new StringBuilder();
        sb.append("=== Menu ===\n")
                .append("1. Generate a new maze.\n")
                .append("2. Load a maze.\n");
        if (!(currentMaze == null)) {
            sb.append("3. Save the maze.\n")
                    .append("4. Display the maze.\n")
                    .append("5. Find the escape.\n");

        }
        sb.append("0. Exit.\n");
        System.out.print(sb);
        sb.delete(0, sb.length());

        pickMenuOption();

    }

    private void pickMenuOption() {
        int pick = Integer.parseInt(scanner.nextLine());
        System.out.print("\n");
        switch (pick) {
            case 1:
                currentMaze = new Maze();
                currentMaze.createMaze();
                currentMaze.printMaze();
                showMenu();
                break;
            case 2:
                loadMaze();
                showMenu();
                break;
            case 3:
                saveMaze();
                showMenu();
                break;
            case 4:
                currentMaze.printMaze();
                showMenu();
                break;
            case 5:
                MazeRunner mazeRunner = new MazeRunner(currentMaze);
                mazeRunner.runMaze();
                showMenu();
                break;
            case 0:
                System.out.println("Bye!");
                System.exit(0);
                break;
            default:
                System.out.println("Incorrect option. Please try again");
        }
    }

    private void saveMaze() {
        String path = scanner.nextLine();
        File file = new File(path);
        try (FileWriter fileWriter = new FileWriter(file, false)) {
            fileWriter.write(currentMaze.getStringMaze());
        } catch (IOException e) {
            System.out.printf("An exception occurred %s\n", e.getMessage());
        }
    }

    private void loadMaze() {
        String path = scanner.nextLine();
        try {
            String stringMaze = new String(Files.readAllBytes(Paths.get(path)));
            currentMaze = new Maze();
            if (!currentMaze.createMazeFromString(stringMaze)) {
                System.out.println("Cannot load the maze. It has an invalid format");
                currentMaze = null;
                showMenu();
            }
        } catch (IOException e) {
            System.out.printf("The file %s does not exist\n", path);
        }
    }

}
