package com.example.restApi.Repository;

import com.example.restApi.Entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends PagingAndSortingRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
}
