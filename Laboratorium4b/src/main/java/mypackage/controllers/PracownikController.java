package mypackage.controllers;

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

@Controller
public class PracownikController {
    @Autowired
    PracownikDao dao;

    // Show form for adding a new employee
    @RequestMapping("/addForm")
    public String showform(Model m) {
        m.addAttribute("command", new Pracownik());
        return "addForm"; // redirect to addForm.jsp
    }

    // Save new employee
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("command") Pracownik pr) {
        dao.save(pr);
        return "redirect:/viewAll"; // redirect to /viewAll endpoint
    }

    // Get list of employees and add them to the model
    @RequestMapping("/viewAll")
    public String viewAll(Model m) {
        List<Pracownik> list = dao.getAll();
        m.addAttribute("list", list);
        return "viewAll"; // redirect to viewAll.jsp
    }

    // Delete employee by ID
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        dao.delete(id);
        return "redirect:/viewAll"; // redirect back to the list of employees
    }

    // Show form for editing an employee
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model m) {
        Pracownik pr = dao.getPracownikById(id);
        m.addAttribute("command", pr);
        return "editForm"; // redirect to editForm.jsp
    }

    // Save updated employee
    @RequestMapping(value = "/saveedit", method = RequestMethod.POST)
    public String editsave(@ModelAttribute("command") Pracownik pr) {
        dao.update(pr);
        return "redirect:/viewAll"; // redirect to /viewAll after saving the edit
    }
}
