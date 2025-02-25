// src/app/models/pipeline-status.model.ts

export interface Job {
  id: string;
  status: string;
}

export interface MachineRepresentation {
  name: string;
  currentJobs: Job[];
  queue: Job[];
}

export interface PipelineStatus {
  machines: {
    machineA: MachineRepresentation;
    machineB: MachineRepresentation;
    machineC: MachineRepresentation;
  };
  finishedJobs: Job[];
}
