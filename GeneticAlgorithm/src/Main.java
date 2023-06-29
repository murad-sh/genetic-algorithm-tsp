import algorithm.GeneticAlgorithm;
import components.Graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        Graph graph = null;

        int mainMenuSelection;
        do {
            printMainMenu();
            Scanner scanner = new Scanner(System.in);
            mainMenuSelection = scanner.nextInt();
            switch (mainMenuSelection) {
                case 0 -> {
                }
                case 1 -> {
                    System.out.println("1. ftv47.atsp");
                    System.out.println("2. ftv170.atsp");
                    System.out.println("3. rbg403.atsp");
                    System.out.println("4. Different file");
                    graph = read();
                }
                case 2 -> {
                    System.out.println("Stopping condition(sec): ");
                    GeneticAlgorithm.maxTime = scanner.nextInt();

                }
                case 3 -> {
                    System.out.println("Population size : ");
                    GeneticAlgorithm.populationSize = scanner.nextInt();
                }
                case 4 -> {
                    System.out.println("Mutation probability : ");
                    GeneticAlgorithm.mutationProbability = scanner.nextDouble();
                }
                case 5 -> {
                    System.out.println("Crossover probability : ");
                    GeneticAlgorithm.crossoverProbability = scanner.nextDouble();
                }
                case 6 -> {
                    System.out.println("Mutation method : ");
                    System.out.println("0. Insertion");
                    System.out.println("1. Transposition");
                    GeneticAlgorithm.mutationMethod = scanner.nextInt();
                    do {
                        GeneticAlgorithm.mutationMethod = scanner.nextInt();
                    } while (GeneticAlgorithm.mutationMethod == 0 || GeneticAlgorithm.mutationMethod == 1);

                }
                case 7 -> {
                    if (graph != null) {
                        System.out.println("Genetic Algorithm :");
                        GeneticAlgorithm.graph = graph;
                        GeneticAlgorithm.solve();
                    } else
                        System.out.println("components.Graph not Found!");
                }
                case 8 -> {
                    if (graph != null) {
                        System.out.println("GA best cost each 10 sec :");
                        GeneticAlgorithm.graph = graph;
                        GeneticAlgorithm.solveWithTimeMeasure();
                    } else
                        System.out.println("components.Graph not Found!");
                }
                default -> System.out.println("Menu Selection Error!");
            }
        } while (mainMenuSelection != 0);

    }

    public static void printMainMenu() {
        System.out.println("Main Menu:");
        System.out.println("1. Read graph from file");
        System.out.println("2. Set stopping condition");
        System.out.println("3. Set population size");
        System.out.println("4. Set mutation probability");
        System.out.println("5. Set crossover probability");
        System.out.println("6. Select mutation method");
        System.out.println("7. Run Genetic Algorithm");
        System.out.println("8. Test best cost found every 10 seconds for 100 seconds");
        System.out.println("0. Exit");
        System.out.println("Choose an option by entering the corresponding number:");
    }



    public static Graph read() throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        File file = null;
        int selected = sc.nextInt();
        switch (selected) {
            case 1 -> file = new File("data/ftv47.atsp");
            case 2 -> file = new File("data/ftv170.atsp");
            case 3 -> file = new File("data/rbg403.atsp");
            case 4 -> {
                System.out.print(" File name :");
                String fileName = sc.next();
                file = new File(fileName + ".atsp");
            }
            default -> {
            }
        }
        Scanner scanner = new Scanner(file);
        for (int i = 0; i < 3; i++) {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
        }

        if (scanner.hasNext()) {
            scanner.next();
        }

        int dimension = scanner.nextInt();
        for (int i = 0; i < 4; i++) {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
        }

        Graph graph = new Graph(dimension);
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                graph.addCost(i, j, scanner.nextInt());
            }
        }
        System.out.println("Graph successfully read from file.");
        //graph.printAdjacencyMatrix();
        return graph;
    }

}




