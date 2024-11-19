package com.example.controllers;
import com.example.dao.UserDao;
import com.example.entity.User;
import com.example.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
        //dodanie do modelu nowego użytkownika
        m.addAttribute("user", new User());
        //zwrócenie nazwy widoku rejestracji - register.html
        return "register";
    }
    @PostMapping("/register")
    public String registerPagePOST(@ModelAttribute User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        dao.save(user);
        //przekierowanie do adresu url: /login
        return "redirect:/login";
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
    public String editUser(@ModelAttribute User user, Principal principal) {
        // Ensure the update applies to the logged-in user
        User currentUser = userService.getUserByLogin(principal.getName());
        currentUser.setName(user.getName());
        currentUser.setSurname(user.getSurname());
        currentUser.setLogin(user.getLogin());

        // Hash and update the password only if it has been changed
        if (!user.getPassword().isEmpty()) {
            currentUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        userService.saveUser(currentUser);

        // Redirect to the profile page after editing
        return "redirect:/profile";
    }

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
