package com.wei.samples.threadpool;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wei on 15/5/25.
 */
public class WorkManagerTest {

    @Test
    public void workManagerIntegrateTest() {
        //任务模拟
        List<Worker> workerList = new ArrayList<Worker>();
        for (int i = 0; i < 10; i++) {
            workerList.add(new Worker() {
                @Override
                void mission() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "sleep 2s");

                }
            });
        }
        //初始化
        WorkManager workManager = new WorkManager("test",3);

        //启动
        workManager.startWork(workerList);

        System.out.println("all mission complete.");
    }
}