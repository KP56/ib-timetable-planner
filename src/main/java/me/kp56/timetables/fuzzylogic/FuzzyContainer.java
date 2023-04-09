package me.kp56.timetables.fuzzylogic;

import java.util.ArrayList;
import java.util.List;

/*
* Stores percentages of the following possible answers:
* - Very good
* - Good
* - Medium
* - Bad
* - Very bad
* */
public class FuzzyContainer {
    public List<double[]> results = new ArrayList<>();

    public FuzzyContainer(int size) {
        for (int i = 0; i < size; i++) {
            results.add(new double[5]);
        }
    }

    public void modifyRecord(int index, int... distribution) {
        int sum = 0;
        for (int i : distribution) {
            sum += i;
        }

        double[] newVal = new double[5];
        for (int i = 0; i < 5; i++) {
            newVal[i] = (double) distribution[i] / sum;
        }

        results.set(index, newVal);
    }

    public double evaluate(double x) {
        double evaluation = 0;
        if (x == (int) x) {
            double[] arr = results.get((int) x);
            for (int i = 0; i < 5; i++) {
                evaluation += (i + 1) * arr[i];
            }
        } else {
            double[] arr1 = results.get((int) x);
            double[] arr2 = results.get((int) Math.ceil(x));
            for (int i = 0; i < 5; i++) {
                //Calculating parameters of a linear function
                double a = (arr1[i]-arr2[i])/(Math.ceil(x)-x);
                double b = arr1[i]-(arr1[i]-arr2[i])/(Math.ceil(x)-x);
                evaluation += (i + 1) * (a * x + b);
            }
        }

        return evaluation;
    }
}
