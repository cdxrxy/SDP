package com.example.restApi.Service;

import com.example.restApi.Entity.UserEntity;
import com.example.restApi.Exception.EmptyUsersException;
import com.example.restApi.Exception.UserAlreadyExistsException;
import com.example.restApi.Exception.UserNotFoundException;
import com.example.restApi.Model.Role;
import com.example.restApi.Model.Status;
import com.example.restApi.Model.User;
import com.example.restApi.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Streamable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(@Qualifier("passwordEncoder") PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User register(UserEntity userEntity) throws UserAlreadyExistsException {
        if(userRepo.findByUsername(userEntity.getUsername()) != null) {
            throw new UserAlreadyExistsException("User already exists");
        }
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRole(Role.USER);
        userEntity.setStatus(Status.ACTIVE);
        return User.toModel(userRepo.save(userEntity));
    }

    public User getOneUser(Long id) throws UserNotFoundException {
        if(userRepo.findById(id).isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        return User.toModel(userRepo.findById(id).get());
    }

    public User deleteOneUser(Long id) throws UserNotFoundException {
        if(userRepo.findById(id).isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        UserEntity userToDelete = userRepo.findById(id).get();
        userRepo.deleteById(id);
        return User.toModel(userToDelete);
    }

    public List<User> getPagedUsers(Integer pageNumber, Integer pageSize, String sortProperty) throws EmptyUsersException {
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.ASC, sortProperty);
        Page<UserEntity> pagedUsers = userRepo.findAll(pageable);
        if(pagedUsers.isEmpty()) {
            throw new EmptyUsersException("There are no users");
        }
        return pagedUsers.getContent().stream().map(User::toModel).collect(Collectors.toList());
    }

    public List<User> getAllUsers() throws EmptyUsersException {
        if(!userRepo.findAll().iterator().hasNext()) {
            throw new EmptyUsersException("There are no users");
        }
        return Streamable.of(userRepo.findAll()).stream().map(User::toModel).toList();
    }
}