package components;

public class Graph {
    private int vertexAmount;
    private int[][] adjacencyMatrix;

    public Graph(int vertexAmount) {
        this.vertexAmount = vertexAmount;
        this.adjacencyMatrix = new int[vertexAmount][vertexAmount];

        for (int i = 0; i < vertexAmount; i++) {
            for (int j = 0; j < vertexAmount; j++) {
                this.adjacencyMatrix[i][j] = -1;
            }
        }
    }

    public void addCost(int i, int j, int cost) {
        this.adjacencyMatrix[i][j] = cost;
    }

    public int getCost(int i, int j) {
        return adjacencyMatrix[i][j];
    }

    public int getVertexAmount() {
        return vertexAmount;
    }

    public int[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    public void printAdjacencyMatrix() {
        for (int i = 0; i < vertexAmount; i++) {

            for (int j = 0; j < vertexAmount; j++) {
                System.out.print(this.adjacencyMatrix[i][j] + "\t");
            }
            System.out.println();
        }
    }

}

