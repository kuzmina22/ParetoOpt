package ru.kuzmina.paretoOpt.optimize;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

public class Optimizer {

    private static Logger log = Logger.getLogger(Optimizer.class.getName());

    public static final String MAX = "max";
    public static final String MIN = "min";

    private int n;
    private int m;
    private JSet jSet;

    private Functional g1;
    private Functional g2;
    private List<Double> x = new ArrayList<>();
    private List<Double> y = new ArrayList<>();
    List<Set<Integer>> paretoSet;

    public Optimizer(int n, int m) {
        this.n = n;
        this.m = m;
        this.jSet = new JSet(n, m);
    }

    public void generate() {
        this.g1 = new Functional(n, m);
        log.info("Generated functional g1: " + g1.get());
        this.g2 = new Functional(n, m);
        log.info("Generated functional g2: " + g2.get());
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            x.add(Math.floor(random.nextDouble() + random.nextInt(10) + 1));
        }
        for (int j = 0; j < m; j++) {
            y.add(Math.floor(random.nextDouble() + random.nextInt(10) + 1));
        }
    }

    public void setG1(List<List<Double>> g) {
        this.g1 = new Functional(g);
        log.info("Functional g1: " + g1.get());
    }

    public void setG2(List<List<Double>> g) {
        this.g2 = new Functional(g);
        log.info("Functional g2: " + g2.get());
    }

    public void setX(List<Double> x) {
        this.x = x;
        log.info("X: " + x);
    }

    public void setY(List<Double> y) {
        this.y = y;
        log.info("Y: " + y);
    }

    public List<Set<Integer>> findParetoSet() {
        List<Set<Integer>> paretoSet = optimize(MAX);
        jSet.set(paretoSet);
        this.paretoSet = optimize(MIN);
        return this.paretoSet;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j : paretoSet.get(i)) {
                stringBuilder.append("g1[");
                stringBuilder.append(String.valueOf(i + 1));
                stringBuilder.append("]opt = ");
                stringBuilder.append(String.valueOf(g1.get().get(i).get(j)));
                stringBuilder.append("; ");
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j : paretoSet.get(i)) {
                stringBuilder.append("g2[");
                stringBuilder.append(String.valueOf(i + 1));
                stringBuilder.append("]opt = ");
                stringBuilder.append(String.valueOf(g2.get().get(i).get(j)));
                stringBuilder.append("; ");
            }
        }
        return stringBuilder.toString();
    }

    private List<Set<Integer>> optimize(String type) {
        log.info("Start optimize with type " + type);
        List<Set<Integer>> paretoSet = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            log.info("i == " + i);
            Set<Integer> iPareto = new HashSet<>();
            Set<Integer> setOfJ = jSet.get().get(i);
            while (setOfJ.size() > 0) {
                Set<Integer> idxOfMaxG1 = g1.findAllIdxOfOptOfIStrategy(i, setOfJ, type);
                Integer k1;
                if (idxOfMaxG1.size() > 1) {
                    k1 = g2.findOptJByListOfIdx(i, idxOfMaxG1, type);
                } else {
                    k1 = (int) idxOfMaxG1.toArray()[0];
                }
                iPareto.add(k1);
                Set<Integer> idxOfMaxG2 = g2.findAllIdxOfOptOfIStrategy(i, setOfJ, type);
                Integer k2;
                if (idxOfMaxG2.size() > 1) {
                    k2 = g1.findOptJByListOfIdx(i, idxOfMaxG2, type);
                } else {
                    k2 = (int) idxOfMaxG2.toArray()[0];
                }
                iPareto.add(k2);
                log.info("k1 == " + k1 + "; " + "k2 == " + k2);
                jSet.refresh(i, g1, k1, g2, k2, type);
                setOfJ = jSet.get().get(i);
                log.info("Set of j index after refresh: " + jSet.get());
            }
            paretoSet.add(iPareto);
        }
        log.info("Optimize completed");
        return paretoSet;
    }

}
