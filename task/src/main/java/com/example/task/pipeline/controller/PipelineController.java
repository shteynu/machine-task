package com.example.task.pipeline.controller;

import com.example.task.pipeline.model.PipelineStatusDTO;
import com.example.task.pipeline.service.PipelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class PipelineController {

    @Autowired
    private PipelineService pipelineService;

    @PostMapping("/job/start")
    public ResponseEntity<String> startJob() {
        pipelineService.startJob();
        return ResponseEntity.ok("Job started");
    }

    @GetMapping("/status")
    public ResponseEntity<PipelineStatusDTO> getStatus() {
        return ResponseEntity.ok(pipelineService.getStatus());
    }
}