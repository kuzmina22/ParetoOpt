package ru.kuzmina.paretoOpt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Functional {

    private List<List<Double>> g = new ArrayList<>();

    public Functional(int n, int m) {
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            List<Double> buff = new ArrayList<>();
            for (int j = 0; j < m; j++) {
                buff.add(Math.floor(random.nextDouble() + random.nextInt(10) + 1));
            }
            g.add(buff);
        }
    }

    public List<List<Double>> get() {
        return this.g;
    }

    private Double findOpt(int i, Set<Integer> j, String type) {
        List<Double> gI = new ArrayList<>();
        j.forEach(idx -> gI.add(g.get(i).get(idx)));
        switch (type) {
            case Optimizer.MAX:
                Collections.sort(gI);
                break;
            case Optimizer.MIN:
                Collections.reverse(gI);
                break;
            default:
                break;
        }
        return gI.get(gI.size() - 1);
    }

    public Set<Integer> findAllIdxOfOptOfIStrategy(int i, Set<Integer> j, String type) {
        Double max = this.findOpt(i, j, type);
        Set<Integer> setOfj = new HashSet<>();
        for (Integer idx : j) {
            if (g.get(i).get(idx).equals(max)) {
                setOfj.add(idx);
            }
        }
        return setOfj;
    }

    public Integer findOptJByListOfIdx(int i, Set<Integer> idx, String type) {
        List<Double> gByIdx = new ArrayList<>();
        idx.forEach(j -> gByIdx.add(g.get(i).get(j)));
        switch (type) {
            case Optimizer.MAX:
                Collections.sort(gByIdx);
                break;
            case Optimizer.MIN:
                Collections.reverse(gByIdx);
                break;
            default:
                break;
        }
        Double max = gByIdx.get(idx.size() - 1);
        return g.get(i).indexOf(max);
    }


}
