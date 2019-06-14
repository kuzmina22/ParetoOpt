package ru.kuzmina.paretoOpt;

import java.util.List;
import java.util.Set;

public class Application {
    public static void main(String[] args) {
        Optimizer optimizer = new Optimizer(3, 5);
        List<Set<Integer>> paretoSet = optimizer.findParetoSet();
        System.out.println("paretoSet: " + paretoSet);
    }

}
