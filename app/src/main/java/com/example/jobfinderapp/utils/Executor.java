package com.example.jobfinderapp.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Executor {
    private static ExecutorService executorService;

    public static synchronized ExecutorService getInstance() {
        if (executorService == null) {
            executorService = Executors.newFixedThreadPool(5);
        } else {
            return executorService;
        }
        return executorService;
    }
}
