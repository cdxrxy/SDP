package com.example.restApi.Repository;

import com.example.restApi.Entity.TaskEntity;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepo extends CrudRepository<TaskEntity, Long> {

}
