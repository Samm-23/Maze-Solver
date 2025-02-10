import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;


class MazeVisualization extends JFrame {
    private int[][] maze;
    
    MazeVisualization(int[][] maze) {
        this.maze = maze;
        setTitle("Maze Visualization");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        int cellSize = Math.min(400 / maze[0].length, 400 / maze.length);

        int width = maze[0].length * cellSize;
        int height = maze.length * cellSize;

        width += 20;
        height += 20;

        Insets insets = getInsets();
        setSize(width + insets.left + insets.right, height + insets.top + insets.bottom);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int cellSize = Math.min(300 / maze[0].length, 300 / maze.length);

        Insets insets = getInsets();
        int startX = insets.left + 10;
        int startY = insets.top + 10; 

        g.setColor(Color.BLACK);
        g.drawRect(startX - 5, startY - 5, maze[0].length * cellSize + 10, maze.length * cellSize + 10);

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (maze[i][j] == 1) {
                    g.setColor(Color.WHITE);
                } else {
                    g.setColor(Color.BLACK);
                }
                g.fillRect(startX + j * cellSize, startY + i * cellSize, cellSize, cellSize);
                g.setColor(Color.BLACK);
                g.drawRect(startX + j * cellSize, startY + i * cellSize, cellSize, cellSize);
            }
        }
    }
}

class Maze_Solver_Improvised {
    public static void solve(int i, int j, int a[][], int n, ArrayList<String> ans, String move, int vis[][]) {
        if (i == n - 1 && j == n - 1) {
            ans.add(move);
            return;
        }

        // downward
        if (i + 1 < n && vis[i + 1][j] == 0 && a[i + 1][j] == 1) {
            vis[i][j] = 1;
            solve(i + 1, j, a, n, ans, move + 'D', vis);
            vis[i][j] = 0;
        }

        // left
        if (j - 1 >= 0 && vis[i][j - 1] == 0 && a[i][j - 1] == 1) {
            vis[i][j] = 1;
            solve(i, j - 1, a, n, ans, move + 'L', vis);
            vis[i][j] = 0;
        }

        // right
        if (j + 1 < n && vis[i][j + 1] == 0 && a[i][j + 1] == 1) {
            vis[i][j] = 1;
            solve(i, j + 1, a, n, ans, move + 'R', vis);
            vis[i][j] = 0;
        }

        // upward
        if (i - 1 >= 0 && vis[i - 1][j] == 0 && a[i - 1][j] == 1) {
            vis[i][j] = 1;
            solve(i - 1, j, a, n, ans, move + 'U', vis);
            vis[i][j] = 0;
        }
    }

    public static ArrayList<String> findPath(int[][] m, int n) {
        int vis[][] = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                vis[i][j] = 0;
            }
        }

        ArrayList<String> ans = new ArrayList<>();
        if (m[0][0] == 1) {
            solve(0, 0, m, n, ans, "", vis);
        }
        return ans;
    }

    private static String findBestPath(ArrayList<String> paths) {
        if (paths.isEmpty()) {
            return null;
        }
        
        String bestPath = paths.get(0);
        for (String path : paths) {
            if (path.length() < bestPath.length()) {
                bestPath = path;
            }
        }
        return bestPath;
    }

    private static int[][] generateMatrix(String difficulty) {
        switch (difficulty.toLowerCase()) {
            case "easy":
                return new int[][]{
                    {1, 1, 0, 1, 0, 1},
                    {0, 1, 1, 1, 1, 1},
                    {1, 0, 0, 1, 1, 1},
                    {1, 1, 1, 0, 0, 1},
                    {0, 1, 0, 1, 1, 1},
                    {1, 1, 1, 1, 0, 1}
                };
            case "medium":
                return new int[][]{
                    {1, 1, 1, 1, 0, 0},
                    {0, 1, 0, 1, 1, 0},
                    {0, 1, 1, 0, 1, 0},
                    {1, 1, 0, 0, 1, 0},
                    {1, 0, 1, 1, 1, 1},
                    {1, 1, 1, 0, 0, 1}
                };
            case "advanced":
                return new int[][]{
                    {1, 0, 0, 0, 1, 1},
                    {1, 1, 1, 1, 1, 0},
                    {0, 0, 1, 0, 0, 0},
                    {0, 1, 1, 1, 1, 1},
                    {1, 1, 0, 0, 1, 0},
                    {0, 1, 1, 0, 1, 1}
                };
            default:
                System.out.println("Invalid difficulty. Select a valid level for maze.");
                return new int[][]{};
        }
    }

    private static void displayMaze(int[][] maze) {
        MazeVisualization mazeVisualization = new MazeVisualization(maze);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter 'start' to execute the program:");
        String userInput = scanner.nextLine();

        if ("start".equalsIgnoreCase(userInput)) {
            System.out.println("Choose the difficulty level:");
            System.out.println("1. Easy");
            System.out.println("2. Medium");
            System.out.println("3. Advanced");

            System.out.print("Enter your choice: ");
            int difficultyChoice = scanner.nextInt();

            String difficulty;
            switch (difficultyChoice) {
                case 1:
                    difficulty = "easy";
                    break;
                case 2:
                    difficulty = "medium";
                    break;
                case 3:
                    difficulty = "advanced";
                    break;
                default:
                    System.out.println("Invalid choice. Using easy difficulty.");
                    difficulty = "easy";
                    break;
            }

            int[][] a = generateMatrix(difficulty);
            int n = a.length;

            Maze_Solver_Improvised obj = new Maze_Solver_Improvised();
            ArrayList<String> res = obj.findPath(a, n);

            System.out.println("Options:");
            System.out.println("1. Display all paths");
            System.out.println("2. Display the best path");
            System.out.println("3. Display both");

            System.out.print("Enter your choice: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.println("All paths:");
                    for (String path : res) {
                        System.out.println(path);
                    }
                    break;
                case 2:
                    String bestPath = findBestPath(res);
                    System.out.println("Best Path:");
                    System.out.println(bestPath);
                    break;
                case 3:
                    System.out.println("All paths:");
                    for (String path : res) {
                        System.out.println(path);
                    }
                    String optimizedPath = findBestPath(res);
                    System.out.println("Best Path:");
                    System.out.println(optimizedPath);
                    break;
                default:
                    System.out.println("Invalid option. Exiting.");
            }

            System.out.println("Do you want to display the maze graphically? (yes/no)");
            String displayChoice = scanner.next();

            if ("yes".equalsIgnoreCase(displayChoice)) {
                displayMaze(a);
            } else {
                System.out.println("Thank you, hope you enjoyed your game!");
            }
        } else {
            System.out.println("Program not executed. You didn't enter 'start'.");
        }
    }
}