package mypackage.controllers;

import jakarta.servlet.http.HttpServletRequest;
import mypackage.beans.Pracownik;
import mypackage.dao.PracownikDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import javax.naming.InvalidNameException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PracownikController {
    @Autowired
    PracownikDao dao;

    // Show form for adding a new employee
    @RequestMapping("/addForm")
    public String showform(Model m) {
        m.addAttribute("command", new Pracownik());
        return "addForm"; 
    }

    // Save new employee
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("command") Pracownik pr) {
        dao.save(pr);
        return "redirect:/viewAll"; 
    }

    // Get list of employees and add them to the model
    @RequestMapping("/viewAll")
    public String viewAll(Model m) {
        List<Pracownik> list = dao.getAll();
        m.addAttribute("list", list);
        return "viewAll";
    }

    // Delete employee by ID
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        dao.delete(id);
        return "redirect:/viewAll";
    }

    // Show form for editing an employee
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model m) {
        Pracownik pr = dao.getPracownikById(id);
        m.addAttribute("command", pr);
        return "editForm";
    }

    // Save updated employee
    @RequestMapping(value = "/saveedit", method = RequestMethod.POST)
    public String editsave(@ModelAttribute("command") Pracownik pr) {
        dao.update(pr);
        return "redirect:/viewAll";
    }
    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest req, Exception ex) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("errorPage");
        return mav;
    }
}
