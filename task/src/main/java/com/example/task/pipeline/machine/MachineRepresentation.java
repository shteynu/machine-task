package com.example.task.pipeline.machine;

import com.example.task.pipeline.model.Job;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MachineRepresentation {
    private String name;
    private List<Job> currentJobs;
    private List<Job> queue;

    public MachineRepresentation() {}

    public MachineRepresentation(String name, List<Job> currentJobs, List<Job> queue) {
        this.name = name;
        this.currentJobs = currentJobs;
        this.queue = queue;
    }
}

