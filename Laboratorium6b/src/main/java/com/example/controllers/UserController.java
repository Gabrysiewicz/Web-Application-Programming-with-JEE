package com.example.controllers;
import com.example.dao.UserDao;
import com.example.entity.User;
import com.example.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDao dao;
    @Autowired
    private UserService userService;
    @GetMapping("/login")
    public String loginPage() {
        //zwrócenie nazwy widoku logowania - login.html
        return "login";
    }
    @GetMapping("/register")
    public String registerPage(Model m) {
        // Add a new empty User object to the model
        m.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerPagePOST(@Valid @ModelAttribute User user, BindingResult bindingResult) {
        if (dao.findByLogin(user.getLogin()) != null) {
            // If login already exists, add an error message
            bindingResult.rejectValue("login", "error.login", "Login is already taken.");
        }
        if (bindingResult.hasErrors()) {
            return "register"; // Return to the register form if validation errors are found
        }
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Hash password
        dao.save(user); // Save the user to the database
        return "redirect:/login"; // Redirect to login page after successful registration
    }
    @GetMapping("/profile")
    public String profilePage(Model m, Principal principal) {
        //dodanie do modelu aktualnie zalogowanego użytkownika:
        m.addAttribute("user", dao.findByLogin(principal.getName()));
        //zwrócenie nazwy widoku profilu użytkownika - profile.html
        return "profile";
    }
    @GetMapping("/users")
    public String getUsers(Model model) {
        List<User> users = userService.getAllUsers();
        if (users == null || users.isEmpty()) {
            model.addAttribute("message", "No users found.");
            users = new ArrayList<>(); // Avoid passing null to the template
            return "users";
        }
        model.addAttribute("users", users);
        return "users";
    }
    @GetMapping("/edit")
    public String editUserForm(Model model, Principal principal) {
        // Get the currently logged-in user
        User currentUser = userService.getUserByLogin(principal.getName());
        model.addAttribute("user", currentUser);
        // Return the view for editing user information (edit.html)
        return "edit";
    }
    @PostMapping("/edit")
    public String editUser(@Valid @ModelAttribute User user, BindingResult bindingResult, @RequestParam("id") Integer id) {
        // Check if login is taken by another user excluding the current user
        User existingUser = dao.findByLoginAndIdNot(user.getLogin(), id);
        if (existingUser != null) {
            bindingResult.rejectValue("login", "error.login", "Login is already taken.");
        }

        if (bindingResult.hasErrors()) {
            return "edit";  // Return to the edit page if there are errors
        }

        // Hash password and save
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        dao.save(user);

        return "profile";  // Redirect to the profile page after successful update
    }
//    @PostMapping("/edit")
//    public String editUser(@ModelAttribute User user, Principal principal) {
//        // Ensure the update applies to the logged-in user
//        User currentUser = userService.getUserByLogin(principal.getName());
//        currentUser.setName(user.getName());
//        currentUser.setSurname(user.getSurname());
//        currentUser.setLogin(user.getLogin());
//
//        // Hash and update the password only if it has been changed
//        if (!user.getPassword().isEmpty()) {
//            currentUser.setPassword(passwordEncoder.encode(user.getPassword()));
//        }
//
//        userService.saveUser(currentUser);
//
//        // Redirect to the profile page after editing
//        return "redirect:/profile";
//    }

    @PostMapping("/delete")
    public String deleteUser(Principal principal) {
        // Get the currently logged-in user and delete them
        User currentUser = userService.getUserByLogin(principal.getName());
        userService.deleteUser(currentUser);

        // Redirect to the logout endpoint to log the user out
        return "redirect:/logout";
    }
    
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Perform logout using Spring Security's built-in functionality
        new SecurityContextLogoutHandler().logout(request, response, null);
        // Redirect to the login page or another page after logout
        return "redirect:/login";
    }
}
