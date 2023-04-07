package me.kp56.timetables.fuzzylogic;

import java.util.ArrayList;
import java.util.List;

public class FuzzyLogic {
    private List<FuzzyContainer> containers = new ArrayList<>();

    public void addContainer(FuzzyContainer container) {
        containers.add(container);
    }

    public double evaluate(List<Double> options) {
        double evaluation = 0;
        for (int i = 0; i < containers.size(); i++) {
            evaluation += containers.get(i).evaluate(options.get(i)) / containers.size();
        }
        return evaluation;
    }
}
