package com.titantrack.models;

public class Exercise {
    private String name;
    private String muscleGroup;
    private String setsReps;
    private String difficulty;

    public Exercise(String name, String muscleGroup, String setsReps, String difficulty) {
        this.name = name;
        this.muscleGroup = muscleGroup;
        this.setsReps = setsReps;
        this.difficulty = difficulty;
    }

    public String getName() {
        return name;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public String getSetsReps() {
        return setsReps;
    }

    public String getDifficulty() {
        return difficulty;
    }
}