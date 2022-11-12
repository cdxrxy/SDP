package com.example.restApi.Service;

import com.example.restApi.Entity.TaskEntity;
import com.example.restApi.Exception.TaskNotFoundException;
import com.example.restApi.Exception.UserNotFoundException;
import com.example.restApi.Model.Task;
import com.example.restApi.Model.User;
import com.example.restApi.Repository.TaskRepo;
import com.example.restApi.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private UserRepo userRepo;

    public Task createTask(TaskEntity taskEntity, Long userId) throws UserNotFoundException {
        if(userRepo.findById(userId).isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        taskEntity.setUserEntity(userRepo.findById(userId).get());
        return Task.toModel(taskRepo.save(taskEntity));
    }

    public Task updateTask(Long taskId) throws TaskNotFoundException {
        if(taskRepo.findById(taskId).isEmpty()) {
            throw new TaskNotFoundException("Task not found");
        }
        TaskEntity task = taskRepo.findById(taskId).get();
        task.setCompleted(!task.getCompleted());
        taskRepo.save(task);
        return Task.toModel(task);
    }
}
