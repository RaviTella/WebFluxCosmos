package com.webFlux.cosmos.resilience.util;
import org.springframework.util.StopWatch;

public class Timer {
    private StopWatch stopWatch;

    public Timer() {
        stopWatch = new StopWatch();
    }

    public void start(){
        if (this.stopWatch.isRunning()){
            stopWatch.stop();
        } else {
            stopWatch.start();
        }
    }

    public void stop(){
        if (this.stopWatch.isRunning()) {
            stopWatch.stop();
        }
    }

    public long getTotalTimeMillis(){
        return this.stopWatch.getTotalTimeMillis();
    }
}
