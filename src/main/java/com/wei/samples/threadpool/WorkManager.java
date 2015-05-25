package com.wei.samples.threadpool;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by wei on 15/5/25.
 */
public class WorkManager {
    private String workname;
    private ExecutorService workPool;
    private Date startTime;
    private Date endTime;

    public WorkManager(String workname) {
        this(workname, null);
    }

    public WorkManager(String workname, Integer threadCount) {
        this.workname = workname;
        if (threadCount != null && threadCount > 0) {
            this.workPool = Executors.newFixedThreadPool(threadCount);
        } else {
            this.workPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 5);
        }
    }

    public void startWork(List<Worker> workerList) {
        startTime = new Date();
        System.out.println(workname + " start at:" + startTime);
        for (Worker worker : workerList) {
            workPool.execute(worker);
        }
        workPool.shutdown();
        while (!workPool.isTerminated()) {
            try {
                workPool.awaitTermination(1, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        endTime = new Date();
        System.out.println(workname + " end at:" + endTime + " using time:" + ((endTime.getTime() - startTime.getTime()) / 1000 % 60));
    }

}
