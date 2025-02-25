package com.example.task.pipeline.machine;

import com.example.task.pipeline.model.Job;
import lombok.Getter;
import lombok.Setter;

import java.util.Queue;
import java.util.List;
import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.Collections;

@Getter@Setter
public class Machine {
    private final String name;
    private final long delayMillis;
    private final ExecutorService executor;
    private final Queue<Job> jobQueue = new ConcurrentLinkedQueue<>();
    private final List<Job> currentJobs = Collections.synchronizedList(new ArrayList<>());

    public Machine(String name, int concurrency, long delayMillis) {
        this.name = name;
        this.delayMillis = delayMillis;
        this.executor = Executors.newFixedThreadPool(concurrency);
    }

    public Future<Job> process(Job job) {
        // Add to the waiting queue
        jobQueue.add(job);
        return executor.submit(() -> {
            // Remove from queue, mark as processing
            jobQueue.remove(job);
            currentJobs.add(job);
            job.setStatus("Processing at " + name);
            Thread.sleep(delayMillis); // simulate work
            currentJobs.remove(job);
            job.setStatus("Completed at " + name);
            return job;
        });
    }
}

