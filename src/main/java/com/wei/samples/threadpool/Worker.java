package com.wei.samples.threadpool;

/**
 * Created by wei on 15/5/25.
 */
public abstract class  Worker implements Runnable {
    private int missionStatue=0;
    @Override
    public void run() {
        try {
            missionStatue=2;
            mission();
            missionStatue=1;
        }catch (Exception e){
            missionStatue=-1;
        }
    }
    abstract void mission();
}
