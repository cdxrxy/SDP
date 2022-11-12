package com.example.restApi.Controller;

import com.example.restApi.Entity.TaskEntity;
import com.example.restApi.Exception.TaskNotFoundException;
import com.example.restApi.Exception.UserNotFoundException;
import com.example.restApi.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping
    public ResponseEntity createTask(@RequestBody TaskEntity taskEntity, @RequestParam Long userId) {
        try {
            return ResponseEntity.ok(taskService.createTask(taskEntity, userId));
        }
        catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @PutMapping
    public ResponseEntity updateTask(@RequestParam Long taskId) {
        try {
            return ResponseEntity.ok(taskService.updateTask(taskId));
        }
        catch (TaskNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }
}
