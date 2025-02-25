package com.example.task.pipeline.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Job {
    private final String id;

    private String status;

    public Job(String id) {
        this.id = id;
        this.status = "Created";
    }

}

