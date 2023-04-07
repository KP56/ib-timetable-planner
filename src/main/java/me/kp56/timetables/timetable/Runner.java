package me.kp56.timetables.timetable;

import me.kp56.timetables.configuration.Config;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Runner {
    private static Config config = Config.getInstance();
    private static File workingDir = new File(System.getProperty("user.dir"));

    public Runner() throws InterruptedException {
        System.out.println("Initializing required data structures.");
	    Timetable.init();

        long beginning = System.currentTimeMillis();

        List<AtomicInteger> environmentGaps = new ArrayList<>();
        List<AtomicReference<Double>> fitnessValues = new ArrayList<>();
        // change it to false to stop algorithm
        AtomicBoolean keep_running = new AtomicBoolean(true);

        int environments = config.getInteger("genetic.environments");
        if (environments == -1) {
            environments = Runtime.getRuntime().availableProcessors();
        }

        /*Creating environments in which the modified version of genetic algorithm will
        * keep creating new generations. Each thread handles a separate environment.*/
        for (int i = 0; i < environments; i++) {
            int finalI = i;
            //Creating references which will be passed to the handleEnvironment function so that this thread can keep track of the results of the other
            environmentGaps.add(new AtomicInteger(-1));
            fitnessValues.add(new AtomicReference<>(0d));
            //Starting a new thread and calling handleEnvironment
            new Thread(() -> {
                try {
                    handleEnvironment("environment" + (finalI + 1), environmentGaps.get(finalI), fitnessValues.get(finalI), keep_running);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }

        //This is the loop which prints information related to the performance of timetables in different environments.
        infLoop:
        while (keep_running.get()) {
            for (AtomicInteger integer : environmentGaps) {
                //Checking if any environment is yet to produce the first generation
                if (integer.get() == -1) {
                    Thread.sleep(1000);
                    //If it is, we wait 1 more second and check again
                    continue infLoop;
                }
            }

            System.out.println("Environment performance (" + ((System.currentTimeMillis() - beginning) / 1000) + "s):");
            for (int i = 0; i < environments; i++) {
                System.out.println("- Environment #" + (i + 1) + ": " + environmentGaps.get(i) + " Gaps/" + fitnessValues.get(i) + " Fitness");
            }

            int bestGaps = Integer.MAX_VALUE;
            double bestFitness = 0;
            for (AtomicReference<Double> d : fitnessValues) {
                bestFitness = Math.max(bestFitness, d.get());
            }
            for (AtomicInteger i : environmentGaps) {
                bestGaps = Math.min(bestGaps, i.get());
            }

            System.out.println("Best performance: " + bestGaps + " Gaps/" + bestFitness + " Fitness");
            System.out.println();
            Thread.sleep(1000);
        }
    }

    private static void handleEnvironment(String name, AtomicInteger currentGaps, AtomicReference<Double> currentFitness, AtomicBoolean keep_running) throws IOException {
        File environmentDir = new File(name);

        if (!environmentDir.exists()) {
            environmentDir.mkdir();
        }

        //Deleting all files related to previously generated timetables in this specific environment during a previous runtime
        deleteAllCandidateFiles(environmentDir);

        //Initializing random timetables
        List<Timetable> timetables = new ArrayList<>();
        int generationSize = config.getInteger("genetic.generation_size");
        for (int i = 0; i < generationSize; i++) {
            //Fixing an empty timetable creates a random one
            Timetable random = new Timetable();
            random.fix();
            timetables.add(random);
        }

        //Creating a hashtable for keeping track of Timetables in the "optimal" map to avoid duplicates
        Set<Timetable> inOptimal = new HashSet<>();
        //The optimal map contains the best timetables. Key is the fitness and value is the list of Timetables which have that fitness
        Map<Double,List<Timetable>> optimal = new TreeMap<>();
        //The number of timetables in optimal
        int optimalSize = 0;

        //Last time we have managed to find a better timetable than the one which was the best back then
        long lastImprovement = System.currentTimeMillis();

        double bestFitness = 0;
        int optimalGaps = 0;

        double bestFitnessAfterReset = 0;
        //An infinite loop in which we create new generations and evaluate the current one
        for (int i = 0; keep_running.get(); i++) {
            double maximalFitness = 0;
            Timetable best = null;
            int bestGaps = 0;
            double minimalFitness = Double.MAX_VALUE;
            int lowestGaps = Integer.MAX_VALUE;
            for (Timetable t : timetables) {
                double fitness = t.fitness();
                int gaps = t.gaps();
                lowestGaps = Math.min(lowestGaps, gaps);
                minimalFitness = Math.min(minimalFitness, fitness);
                if (maximalFitness < fitness) {
                    maximalFitness = fitness;
                    best = t;
                    bestGaps = gaps;
                }
            }

            //If the timetable we are checking is not in the optimal map
            if (!inOptimal.contains(best)) {
                //Checking if the number of timetables in optimal reached the config value
                if (optimalSize == config.getInteger("keep_track_of")) {
                    Map.Entry<Double, List<Timetable>> entry = ((Map.Entry<Double, List<Timetable>>) optimal.entrySet().toArray()[0]);
                    /*If the maximal fitness in this generation is greater than the worst fitness in the optimal map,
                    * then we want to remove that timetable, add a new one and replace all files.*/
                    if (maximalFitness > entry.getKey()) {
                        optimal.remove(entry.getKey());
                        if (entry.getValue().size() > 1) {
                            List<Timetable> withoutLast = new ArrayList<>(entry.getValue());
                            inOptimal.remove(withoutLast.get(withoutLast.size() - 1));
                            withoutLast.remove(withoutLast.size() - 1);
                            optimal.put(entry.getKey(), withoutLast);
                        } else {
                            inOptimal.remove(entry.getValue().get(0));
                        }
                        if (optimal.containsKey(maximalFitness)) {
                            optimal.get(maximalFitness).add(best);
                        } else {
                            optimal.put(maximalFitness, new ArrayList<>(Collections.singletonList(best)));
                        }

                        deleteAllCandidateFiles(environmentDir);
                        saveCandidates(environmentDir, optimal);
                        inOptimal.add(best);
                    }
                } else {
                    //If the nr of elements in optimal is yet to reach that config value, we just want to add the best timetable from this generation
                    if (optimal.containsKey(maximalFitness)) {
                        optimal.get(maximalFitness).add(best);
                    } else {
                        optimal.put(maximalFitness, new ArrayList<>(Collections.singletonList(best)));
                    }

                    deleteAllCandidateFiles(environmentDir);
                    saveCandidates(environmentDir, optimal);
                    inOptimal.add(best);

                    optimalSize++;
                }
            }

            //Updating the statistics related to the best timetables in the environment
            if (maximalFitness > bestFitness) {
                bestFitness = maximalFitness;
                optimalGaps = bestGaps;
            }

            //Managed to improve the timetable so resetting the timeout timer
            if (maximalFitness > bestFitnessAfterReset) {
                bestFitnessAfterReset = maximalFitness;
                lastImprovement = System.currentTimeMillis();
            }

            currentGaps.set(optimalGaps);
            currentFitness.set(bestFitness);

            List<Timetable> newList = new ArrayList<>();
            //Creating new timetables from the best one in this generation
            while (newList.size() < generationSize) {
                Timetable newTimetable = best.offspring();
                newList.add(newTimetable);
            }
            timetables = newList;

            //If we haven't managed to find a better timetable in the specified time, we reset the entire generation
            if (System.currentTimeMillis() - lastImprovement >= config.getInteger("genetic.environment_timeout") * 1000L) {
                lastImprovement = System.currentTimeMillis();
                bestFitnessAfterReset = 0;

                timetables = new ArrayList<>();
                for (int j = 0; j < generationSize; j++) {
                    //Fixing an empty timetable creates a random one
                    Timetable random = new Timetable();
                    random.fix();
                    timetables.add(random);
                }
            }
        }
    }

    private static void saveCandidates(File dir, Map<Double, List<Timetable>> optimal) throws IOException {
        int counter = 1;
        for (Map.Entry<Double, List<Timetable>> entry2 : optimal.entrySet()) {
            for (Timetable t : entry2.getValue()) {
                t.saveToFile(dir.getName() + "/candidate_timetable" + counter, t.gaps(), t.fitness());
                counter++;
            }
        }
    }
    private static void deleteAllCandidateFiles(File dir) {
        for (File f : dir.listFiles()) {
            if (f.getName().contains("candidate_timetable")) {
                f.delete();
            }
        }
    }

    public static List<Double> normalizeProbabilities(List<Integer> unnormalized) {
        int sum = 0;
        for (int i : unnormalized) {
            sum += i;
        }

        List<Double> normalized = new ArrayList<>();
        for (int i = 0; i < unnormalized.size(); i++) {
            normalized.add((double) unnormalized.get(i) / sum);
        }

        return normalized;
    }

    public static int randomItem(List<Double> probabilities) {
        double p = Math.random();
        double cumulativeProbability = 0.0;
        for (int j = 0; j < probabilities.size(); j++) {
            cumulativeProbability += probabilities.get(j);
            if (p <= cumulativeProbability) {
                return j;
            }
        }

        return -1;
    }
}
