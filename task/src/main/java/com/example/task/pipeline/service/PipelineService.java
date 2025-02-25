package com.example.task.pipeline.service;

import com.example.task.pipeline.machine.Machine;
import com.example.task.pipeline.machine.MachineRepresentation;
import com.example.task.pipeline.model.Job;
import com.example.task.pipeline.model.PipelineStatusDTO;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
public class PipelineService {

    private final Machine machineA = new Machine("Machine A", 1, 10_000);  // 10 sec, 1 at a time
    private final Machine machineB = new Machine("Machine B", 2, 15_000);  // 15 sec, 2 in parallel
    private final Machine machineC = new Machine("Machine C", 1, 20_000);  // 20 sec, 1 at a time
    private final List<Job> finishedJobs = Collections.synchronizedList(new ArrayList<>());

    public void startJob() {
        // Create a new job with a unique id
        Job job = new Job(UUID.randomUUID().toString());
        job.setStatus("Started");

        // Chain the processing through the machines
        CompletableFuture.supplyAsync(() -> {
            try {
                return machineA.process(job).get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).thenCompose(result -> CompletableFuture.supplyAsync(() -> {
            try {
                return machineB.process(job).get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        })).thenCompose(result -> CompletableFuture.supplyAsync(() -> {
            try {
                return machineC.process(job).get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        })).thenAccept(result -> {
            job.setStatus("Finished");
            finishedJobs.add(job);
        });
    }

    public PipelineStatusDTO getStatus() {
        Map<String, MachineRepresentation> machines = new HashMap<>();

        machines.put("machineA", new MachineRepresentation(
                machineA.getName(),
                machineA.getCurrentJobs(),
                new ArrayList<>(machineA.getJobQueue())
        ));

        machines.put("machineB", new MachineRepresentation(
                machineB.getName(),
                machineB.getCurrentJobs(),
                new ArrayList<>(machineB.getJobQueue())
        ));

        machines.put("machineC", new MachineRepresentation(
                machineC.getName(),
                machineC.getCurrentJobs(),
                new ArrayList<>(machineC.getJobQueue())
        ));

        return new PipelineStatusDTO(machines, finishedJobs);
    }
}