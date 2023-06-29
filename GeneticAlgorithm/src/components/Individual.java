package components;

import java.util.List;

public class Individual {
    private List<Integer> path;
    private int fitnessValue;

    public Individual(List<Integer> path, int fitnessValue) {
        this.path = path;
        this.fitnessValue = fitnessValue;
    }

    public void setPath(List<Integer> path) {
        this.path = path;
    }

    public List<Integer> getPath() {
        return path;
    }

    public int getFitnessValue() {
        return fitnessValue;
    }

    public void setFitnessValue(int fitnessValue) {
        this.fitnessValue = fitnessValue;
    }

}