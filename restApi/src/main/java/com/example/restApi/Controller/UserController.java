package com.example.restApi.Controller;

import com.example.restApi.Entity.UserEntity;
import com.example.restApi.Exception.EmptyUsersException;
import com.example.restApi.Exception.UserAlreadyExistsException;
import com.example.restApi.Exception.UserNotFoundException;
import com.example.restApi.Repository.UserRepo;
import com.example.restApi.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity register(@RequestBody UserEntity userEntity) {
        try {
            return ResponseEntity.ok(userService.register(userEntity));
        }
        catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @GetMapping
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity getOneUser(@RequestParam(required = false) Long id) {
        try {
            if(id == null) {
                return ResponseEntity.ok(userService.getAllUsers());
            }
            return ResponseEntity.ok(userService.getOneUser(id));
        }
        catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity deleteOneUser(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.deleteOneUser(id));
        }
        catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @GetMapping("/paged/{pageNumber}/{pageSize}/{sortProperty}")
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity getPagedUsers(@PathVariable(required = false) Integer pageNumber, @PathVariable(required = false) Integer pageSize,
                                        @PathVariable(required = false) String sortProperty) {
        try {
            return ResponseEntity.ok(userService.getPagedUsers(pageNumber, pageSize, sortProperty));
        }
        catch (EmptyUsersException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @GetMapping("/paged")
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity getAmbiguousPagedUsers() {
        return ResponseEntity.badRequest().body("Ambiguous");
    }


    // RestController always responses in form of json
    // That's why we cant send a view in return because it will result in json string
    // The solution is to user ModelAndView
    @GetMapping("/greeting")
    public ModelAndView index (@RequestParam Long id) {
//        ModelAndView modelAndView = new ModelAndView("greeting");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("greeting");
        try {
            modelAndView.addObject(userService.getOneUser(id));
        }
        catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping("/greeting/principal")
    public ResponseEntity greeting(Principal principal) {
        return ResponseEntity.ok("Hello, " + principal.getName());
    }
}