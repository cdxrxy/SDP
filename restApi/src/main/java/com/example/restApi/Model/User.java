package com.example.restApi.Model;

import com.example.restApi.Entity.TaskEntity;
import com.example.restApi.Entity.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class User {
    private Long id;
    private String username;

    private List<Task> taskList;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static User toModel(UserEntity userEntity) {
        User model = new User();
        model.setId(userEntity.getId());
        model.setUsername(userEntity.getUsername());
        if(!(userEntity.getTaskEntityList() == null)) {
            model.setTaskList(userEntity.getTaskEntityList().stream().map(Task::toModel).collect(Collectors.toList()));
        }
        return model;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", taskList=" + taskList +
                '}';
    }
}
