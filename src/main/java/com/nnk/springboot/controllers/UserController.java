package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.UserDomain;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

/**
 * Controller class for handling HTTP requests related to UserDomain entities.
 */
@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Handles the request to display the list of users.
     *
     * @param model the Model object to add attributes.
     * @return the view name for the user list page.
     */
    @RequestMapping("/user/list")
    public String home(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    /**
     * Handles the request to display the form for adding a new user.
     *
     * @param model the Model object to add attributes.
     * @return the view name for the add user form.
     */
    @GetMapping("/user/add")
    public String addUser(Model model) {
        model.addAttribute("user", new UserDomain());
        return "user/add";
    }

    /**
     * Handles the request to validate and save a new user.
     *
     * @param user the UserDomain object to validate and save.
     * @param result the BindingResult object to handle validation errors.
     * @param model the Model object to add attributes.
     * @return the view name for the user list page if successful, otherwise the add form.
     */
    @PostMapping("/user/validate")
    public String validate(@Valid UserDomain user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            System.out.println("Validation errors: " + result.getAllErrors());
            return "user/add";
        }

        // Hash the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save the user
        try {
            userRepository.save(user);
        } catch (Exception e) {
            System.out.println("Error saving user: " + e.getMessage());
            result.reject("error.user", "Could not save user. Please try again.");
            return "user/add";
        }

        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }

    /**
     * Handles the request to display the form for updating an existing user.
     *
     * @param id the ID of the user to update.
     * @param model the Model object to add attributes.
     * @return the view name for the update user form if the user exists, otherwise redirect to the list page.
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        UserDomain user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        user.setPassword("");
        model.addAttribute("user", user);
        return "user/update";
    }

    /**
     * Handles the request to update an existing user.
     *
     * @param id the ID of the user to update.
     * @param user the UserDomain object with updated data.
     * @param result the BindingResult object to handle validation errors.
     * @param model the Model object to add attributes.
     * @return the view name for the update user form if there are errors, otherwise redirect to the list page.
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid UserDomain user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setId(id);
        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }

    /**
     * Handles the request to delete a user.
     *
     * @param id the ID of the user to delete.
     * @param model the Model object to add attributes.
     * @return the view name to redirect to the user list page.
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        UserDomain user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }
}
