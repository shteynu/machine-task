package com.example.task.pipeline.model;

import com.example.task.pipeline.machine.MachineRepresentation;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
    public class PipelineStatusDTO {
        private Map<String, MachineRepresentation> machines;
        private List<Job> finishedJobs;

        public PipelineStatusDTO() {}

        public PipelineStatusDTO(Map<String, MachineRepresentation> machines, List<Job> finishedJobs) {
            this.machines = machines;
            this.finishedJobs = finishedJobs;
        }


    }

