package utils;

import algorithm.GeneticAlgorithm;

public class FitnessThread extends Thread {
    int time = 0;
    public void run() {
        while (!Thread.interrupted()) {
            if (time<= 100){

                System.out.println("at "+ time + "s best cost = " + GeneticAlgorithm.bestFitness.get());
            }
            try {
                Thread.sleep(10000);
                time=time+10;
            } catch (InterruptedException e) {
                System.out.println();
            }
        }
    }
}