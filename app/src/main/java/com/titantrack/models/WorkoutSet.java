package com.titantrack.models;

public class WorkoutSet {
    private int reps;
    private double weight;

    public WorkoutSet(int reps, double weight) {
        this.reps = reps;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return reps + " reps con " + weight + " kg";
    }
}
