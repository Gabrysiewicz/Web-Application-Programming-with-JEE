package bp.pai_security.controllers;
import bp.pai_security.dao.UserDao;
import bp.pai_security.entity.User;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    @GetMapping("/register")
    public String registerPage(Model m) {
        m.addAttribute("user", new User());
        return "register";
    }
    @PostMapping("/register")
    public String registerPagePOST(@ModelAttribute User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        dao.save(user);
        return "redirect:/login";
    }
    @GetMapping("/profile")
    public String profilePage(Model m, Principal principal) {
    //dodanie do modelu aktualnie zalogowanego użytkownika:
    m.addAttribute("user", dao.findByLogin(principal.getName()));
    //zwrócenie nazwy widoku profilu użytkownika - profile.html
    return "profile";
    }
    //@GetMapping("/users")
    //definicja metody, która zwróci do widoku users.html listę
    //użytkowników z bd
}
