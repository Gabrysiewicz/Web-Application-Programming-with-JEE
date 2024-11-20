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
        m.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerPagePOST(@Valid @ModelAttribute User user, BindingResult bindingResult) {
        if (dao.findByLogin(user.getLogin()) != null) {
            bindingResult.rejectValue("login", "error.login", "Login is already taken.");
        }
        if (bindingResult.hasErrors()) {
            return "register";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        dao.save(user);
        return "redirect:/login";
    }
    @GetMapping("/profile")
    public String profilePage(Model m, Principal principal) {
        m.addAttribute("user", dao.findByLogin(principal.getName()));
        return "profile";
    }
    @GetMapping("/users")
    public String getUsers(Model model) {
        List<User> users = userService.getAllUsers();
        if (users == null || users.isEmpty()) {
            model.addAttribute("message", "No users found.");
            users = new ArrayList<>(); 
            return "users";
        }
        model.addAttribute("users", users);
        return "users";
    }
    @GetMapping("/edit")
    public String editUserForm(Model model, Principal principal) {
        User currentUser = userService.getUserByLogin(principal.getName());
        model.addAttribute("user", currentUser);
        return "edit";
    }
    @PostMapping("/edit")
    public String editUser(@Valid @ModelAttribute User user, BindingResult bindingResult, @RequestParam("id") Integer id) {
        User existingUser = dao.findByLoginAndIdNot(user.getLogin(), id);
        if (existingUser != null) {
            bindingResult.rejectValue("login", "error.login", "Login is already taken.");
        }

        if (bindingResult.hasErrors()) {
            return "edit"; 
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        dao.save(user);

        return "redirect:/logout";
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
        User currentUser = userService.getUserByLogin(principal.getName());
        userService.deleteUser(currentUser);
        return "redirect:/logout";
    }
    
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, null);
        return "redirect:/login";
    }
}
