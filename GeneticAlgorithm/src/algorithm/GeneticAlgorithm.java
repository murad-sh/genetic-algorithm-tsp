package algorithm;

import components.Graph;
import components.Individual;
import utils.FitnessThread;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class GeneticAlgorithm {
    static Random random = new Random();
    public static Graph graph;
    public static int populationSize = 300;
    public static double mutationProbability = 0.01f;
    public static double crossoverProbability = 0.8f;
    public static int maxTime = 10;
    public static int mutationMethod = 0;   // 1 - Insert , 0 - Transposition
    public static AtomicInteger bestFitness = new AtomicInteger();


    public static void solve() {
        List<Individual> population = generateInitialPopulation(populationSize);
        long endTime, execTime;
        long startTime = System.nanoTime();

        do {
            population.addAll(crossoverOX(population));
            if (mutationMethod == 0) {
                population = transpositionMutation(population);
            }
            if (mutationMethod == 1) {
                population = insertionMutation(population);
            }

            population = elitismSelection(population, populationSize);

            endTime = System.nanoTime();
            execTime = (endTime - startTime) / 1000000000;
        } while (execTime <= maxTime - 1);
        population = elitismSelection(population, 1);

        System.out.print("Best cost :" + population.get(0).getFitnessValue() + "\nBest Path: ");

        for (int i = 0; i < population.get(0).getPath().size(); i++) {
            System.out.print(population.get(0).getPath().get(i) + "-");
        }
        System.out.println(population.get(0).getPath().get(0));

        System.out.println("Time : " + execTime + "s");
    }

    static int getFitnessValue(List<Integer> path) {
        int cost = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            cost += graph.getAdjacencyMatrix()[path.get(i)][path.get(i + 1)];
        }
        //last connection
        cost += graph.getCost(path.get(path.size() - 1), path.get(0));
        return cost;
    }


    static List<Individual> generateInitialPopulation(int populationSize) {

        List<Individual> population = new ArrayList<>();

        List<Integer> nodes = new ArrayList<>();
        for (int i = 0; i < graph.getVertexAmount(); i++) {
            nodes.add(i);
        }
        // creating random individuals
        for (int i = 0; i < populationSize / 2; i++) {
            Collections.shuffle(nodes);

            int cost = getFitnessValue(nodes);
            population.add(new Individual(nodes, cost));
        }
        // Greedy society
        for (int i = 0; i < populationSize / 2; i++) {
            nodes = nearestNeighbor();
            int cost = getFitnessValue(nodes);
            population.add(new Individual(nodes, cost));
        }

        return population;
    }


    public static List<Integer> nearestNeighbor() {

        List<Integer> firstPath = new ArrayList<>();
        boolean[] visited = new boolean[graph.getVertexAmount()];
        Random random = new Random();
        int currentVertex = random.nextInt(graph.getVertexAmount());
        int destinationVertex = 0;
        int min;

        firstPath.add(currentVertex);
        Arrays.fill(visited, false);
        for (int i = 0; i < graph.getVertexAmount() - 1; i++) {
            visited[currentVertex] = true;
            min = Integer.MAX_VALUE;
            for (int j = 0; j < graph.getVertexAmount(); j++) {
                if (i != j && !visited[j] && graph.getAdjacencyMatrix()[currentVertex][j] < min) {
                    min = graph.getAdjacencyMatrix()[currentVertex][j];
                    destinationVertex = j;
                }
            }
            firstPath.add(destinationVertex);
            currentVertex = destinationVertex;
        }
        return firstPath;
    }


    public static List<Individual> transpositionMutation(List<Individual> population) {
        for (Individual individual : population) {
            List<Integer> path = new ArrayList<>(individual.getPath());
            if (random.nextDouble() < mutationProbability) {
                int currentIndex = random.nextInt(graph.getVertexAmount());
                int newIndex = random.nextInt(graph.getVertexAmount());
                //swap the element at currentIndex with the element at newIndex
                Collections.swap(path, currentIndex, newIndex);
                individual.setPath(path);
                individual.setFitnessValue(getFitnessValue(path));
            }
        }
        return population;
    }


    public static List<Individual> insertionMutation(List<Individual> population) {
        for (Individual individual : population) {
            List<Integer> path = new ArrayList<>(individual.getPath());
            if (random.nextDouble() < mutationProbability) {
                int currentIndex = random.nextInt(graph.getVertexAmount());
                int newIndex = random.nextInt(graph.getVertexAmount());
                int node = path.remove(currentIndex);
                path.add(newIndex, node);
                individual.setPath(path);
                individual.setFitnessValue(getFitnessValue(path));
            }
        }
        return population;
    }


    public static List<Individual> crossoverOX(List<Individual> population) {
        List<Individual> newPopulation = new ArrayList<>();
        for (int i = 0; i < population.size(); i++) {
            int indexParent1 = random.nextInt(population.size());
            int indexParent2 = random.nextInt(population.size());
            // check if crossover is possible
            if (random.nextDouble() < crossoverProbability) {
                int crossoverPoint1 = 0;
                int crossoverPoint2 = 0;
                do {
                    crossoverPoint1 = random.nextInt(population.get(i).getPath().size());
                    crossoverPoint2 = random.nextInt(population.get(i).getPath().size());
                } while (crossoverPoint2 <= crossoverPoint1);

                // create new individuals
                List<Integer> parent1 = population.get(indexParent1).getPath();
                List<Integer> parent2 = population.get(indexParent2).getPath();
                List<Integer> child1 = new ArrayList<>(parent1.subList(crossoverPoint1, crossoverPoint2));
                List<Integer> child2 = new ArrayList<>(parent2.subList(crossoverPoint1, crossoverPoint2));

                // Copy the remaining nodes from parent2 to child1
                for (int j = crossoverPoint2; j < parent1.size(); j++) {
                    if (!child1.contains(parent2.get(j))) {
                        child1.add(parent2.get(j));
                    }
                    if (!child2.contains(parent1.get(j))) {
                        child2.add(parent1.get(j));
                    }
                }

                for (int j = 0; j < crossoverPoint2; j++) {
                    if (!child1.contains(parent2.get(j))) {
                        child1.add(parent2.get(j));
                    }
                    if (!child2.contains(parent1.get(j))) {
                        child2.add(parent1.get(j));
                    }
                }

                Individual individual1 = new Individual(child1, getFitnessValue(child1));
                Individual individual2 = new Individual(child2, getFitnessValue(child2));
                newPopulation.add(individual1);
                newPopulation.add(individual2);
            }
        }

        return newPopulation;
    }

    public static List<Individual> elitismSelection(List<Individual> population, int newPopulationSize) {
        List<Individual> selected = new ArrayList<>();
        // sort the population based on fitness value
        population.sort(Comparator.comparingInt(Individual::getFitnessValue));
        // select the best selected
        for (int i = 0; i < newPopulationSize; i++) {
            selected.add(population.get(i));
        }
        return selected;
    }


    public static void solveWithTimeMeasure() {
        List<Individual> population = generateInitialPopulation(populationSize);
        population = elitismSelection(population, populationSize);
        bestFitness.set(population.get(0).getFitnessValue());
        FitnessThread thread = new FitnessThread();
        thread.start();
        long endTime, execTime, timeNew;
        long startTime = 0;
        startTime = System.nanoTime();
        do {
            population.addAll(crossoverOX(population));
            if (mutationMethod == 0) {
                population = transpositionMutation(population);
            }
            if (mutationMethod == 1) {
                population = insertionMutation(population);
            }
            population = elitismSelection(population, populationSize);
            bestFitness.getAndSet(population.get(0).getFitnessValue());
            endTime = System.nanoTime();
            execTime = (endTime - startTime) / 1000000000;
        } while (execTime <= 100);
        population = elitismSelection(population, 1);
        thread.interrupt();
        System.out.print("Results : ");
        for (int i = 0; i < population.get(0).getPath().size(); i++) {
            System.out.print(population.get(0).getPath().get(i) + "-");
        }
        System.out.println(population.get(0).getPath().get(0));
        System.out.println();

    }














}
