package com.example.restApi.Security;

import com.example.restApi.Entity.UserEntity;
import com.example.restApi.Exception.UserNotFoundException;
import com.example.restApi.Model.User;
import com.example.restApi.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;

    @Autowired
    public UserDetailsServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepo.findByUsername(username);
        if(userEntity == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return SecurityUser.toUserDetails(userEntity);
    }
}
