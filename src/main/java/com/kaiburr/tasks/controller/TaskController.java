package com.kaiburr.tasks.controller;

import com.kaiburr.tasks.repository.TaskRepository;
import com.kaiburr.tasks.model.Task;
import com.kaiburr.tasks.model.TaskExecution;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskRepository repo;

    public TaskController(TaskRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public ResponseEntity<?> getTasks(@RequestParam(name = "id", required = false) String id) {
        if (id == null) {
            return ResponseEntity.ok(repo.findAll());
        }
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<?> createOrUpdateTask(@RequestBody Task task) {
        if (!isSafeCommand(task.getCommand())) {
            return ResponseEntity.badRequest().body("Unsafe command detected");
        }
        Task saved = repo.save(task);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable String id) {
        repo.deleteById(id);
        return ResponseEntity.ok("Deleted " + id);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(name = "name") String name) {
        return ResponseEntity.ok(repo.findByNameContainingIgnoreCase(name));
    }

    @PutMapping("/{id}/execute")
    public ResponseEntity<?> executeTask(@PathVariable String id) {
        Optional<Task> optTask = repo.findById(id);
        if (optTask.isEmpty())
            return ResponseEntity.notFound().build();

        Task task = optTask.get();
        TaskExecution exec = runCommand(task.getCommand());

        if (task.getTaskExecutions() == null) {
            task.setTaskExecutions(new ArrayList<>());
        }
        task.getTaskExecutions().add(exec);

        repo.save(task);
        return ResponseEntity.ok(exec);
    }

    private boolean isSafeCommand(String command) {
        List<String> blackList = Arrays.asList("rm", "shutdown", "reboot", "kill");
        return blackList.stream().noneMatch(command::contains);
    }

    private TaskExecution runCommand(String command) {
        TaskExecution exec = new TaskExecution();
        exec.setStartTime(java.time.Instant.now());
        try {
            Process proc = Runtime.getRuntime().exec(command);
            Scanner s = new Scanner(proc.getInputStream()).useDelimiter("\\A");
            String out = s.hasNext() ? s.next() : "";
            proc.waitFor();
            exec.setEndTime(java.time.Instant.now());
            exec.setOutput(out);
        } catch (Exception e) {
            exec.setEndTime(java.time.Instant.now());
            exec.setOutput("Error: " + e.getMessage());
        }
        return exec;
    }
}
