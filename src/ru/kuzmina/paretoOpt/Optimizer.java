package ru.kuzmina.paretoOpt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Optimizer {

    public static final String MAX = "max";
    public static final String MIN = "min";

    private int n;
    private int m;
    private JSet jSet;

    private Functional g1;
    private Functional g2;

    public Optimizer(int n, int m) {
        this.n = n;
        this.m = m;
        this.g1 = new Functional(n, m);
        System.out.println("g1: " + g1.get());
        this.g2 = new Functional(n, m);
        System.out.println("g2: " + g2.get());
        this.jSet = new JSet(n, m);
    }

    public List<Set<Integer>> findParetoSet() {
        List<Set<Integer>> paretoSet = optimize(MAX);
        jSet.set(paretoSet);
        return optimize(MIN);
    }

    private List<Set<Integer>> optimize(String type) {
        List<Set<Integer>> paretoSet = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            System.out.println("i = " + i);
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
                System.out.println("        k1 = " + k1 + "; " + "k2 = " + k2);
                jSet.refresh(i, g1, k1, g2, k2, type);
                setOfJ = jSet.get().get(i);
                System.out.println("    jSet: " + jSet.get());
            }
            paretoSet.add(iPareto);
        }
        return paretoSet;
    }

}
