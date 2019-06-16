package ru.kuzmina.paretoOpt.optimize;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JSet {

    private List<Set<Integer>> j = new ArrayList<>();

    public JSet(int n, int m) {
        Set<Integer> buff = new HashSet<>();
        for (int j = 0; j < m; j++) {
            buff.add(j);
        }
        for (int i = 0; i < n; i++) {
            j.add(buff);
        }
    }

    public List<Set<Integer>> get() {
        return j;
    }

    public void set(List<Set<Integer>> j) {
        this.j = j;
    }

    public void refresh(int i, Functional g1, int k1, Functional g2, int k2, String type) {
        Set<Integer> newIJ = new HashSet<>();
        List<Double> g1I = new ArrayList<>(g1.get().get(i));
        List<Double> g2I = new ArrayList<>(g2.get().get(i));
        switch (type) {
            case Optimizer.MAX:
                for (Integer j : j.get(i)) {
                    if (g1I.get(j) > g1I.get(k2) && g2I.get(j) > g2I.get(k1)) {
                        newIJ.add(j);
                    }
                }
                break;
            case Optimizer.MIN:
                for (Integer j : j.get(i)) {
                    if (g1I.get(j) < g1I.get(k2) && g2I.get(j) < g2I.get(k1)) {
                        newIJ.add(j);
                    }
                }
                break;
            default:
                break;
        }

        j.set(i, newIJ);
    }

}
