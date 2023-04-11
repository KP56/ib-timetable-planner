package me.kp56.timetables.fuzzylogic;

import java.util.ArrayList;
import java.util.List;

public class FuzzyLogic {
    private List<List<FuzzyContainer>> containers = new ArrayList<>();

    public void addContainer(int group, FuzzyContainer container) {
        containers.get(group).add(container);
    }

    public void addGroup() {
        containers.add(new ArrayList<>());
    }

    private double evaluate(int container, List<Double> options) {
        double evaluation = 0;
        for (int i = 0; i < containers.get(container).size(); i++) {
            evaluation += containers.get(container).get(i).evaluate(options.get(i)) / containers.get(container).size();
        }
        return evaluation;
    }

    public double evaluate(List<List<Double>> options) {
        double evaluation = 0;
        for (int i = 0; i < containers.size(); i++) {
            evaluation += evaluate(i, options.get(i)) / containers.size();
        }

        return evaluation;
    }
}
