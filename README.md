# Genetic Algorithm for TSP

## Introduction
This project implements a Genetic Algorithm, which is a heuristic optimization method inspired by evolutionary mechanisms such as selection, crossover, and mutation. The algorithm is specifically designed to solve the Traveling Salesman Problem (TSP), where the goal is to find the shortest route that visits all cities and returns to the starting point.

## Mutation Methods
The program provides two mutation methods for individuals in the population:

### Transposition Mutation 
The transposition mutation method performs a transposition operation on an individual's path. It randomly selects two positions in the path and swaps the elements at those positions.

### Insertion Mutation
The insertion mutation method randomly selects two positions in an individual's path and inserts the element at one position to the other position, shifting the elements accordingly.

## Crossover Method
The program uses the Order Crossover (OX) method for crossover. This method combines genetic material from two parent individuals to create offspring individuals. The crossover process involves selecting two parent individuals, determining crossover points, and creating new individuals by combining genetic material from the parents.

## Selection Method
The program employs the Elitism Selection method. This method selects the best individuals from the population based on their fitness values and includes them in the next generation. The selection process ensures that the best individuals have a higher chance of being selected for the next generation.

## Initial Population Initialization
The initial population is generated using the nearest neighbor algorithm. This algorithm selects the nearest unvisited city as the next city to be added to the path, iteratively constructing an initial solution.

## Usage
To use the program, follow these steps:

1. Run the `Main` class to start the application.
2. In the main menu, choose the option to read the graph from a file.
3. Optionally, you can set various configurations by selecting the corresponding menu options. These configurations include:
   - Setting the stopping condition for the algorithm.
   - Setting the population size.
   - Setting the mutation probability.
   - Setting the crossover probability.
   - Selecting the desired mutation method.
4. Once you have configured the necessary parameters, you can proceed to run the Genetic Algorithm by selecting the appropriate option from the menu.
5. The algorithm will execute, and you will see the progress and output displayed in the console.
6. Optionally, you can choose to test the best cost found every 10 seconds for a duration of 100 seconds.
7. To exit the program, select the "Exit" option from the menu.



