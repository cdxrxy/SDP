package com.example.restApi.Model;

import com.example.restApi.Entity.TaskEntity;

public class Task {
    private Long id;
    private String title;
    private Boolean completed;

    public Task() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public static Task toModel(TaskEntity taskEntity) {
        Task model = new Task();
        model.setId(taskEntity.getId());
        model.setTitle(taskEntity.getTitle());
        model.setCompleted(taskEntity.getCompleted());
        return model;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", completed=" + completed +
                '}';
    }
}
