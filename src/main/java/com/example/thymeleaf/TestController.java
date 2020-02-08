package com.example.thymeleaf;


import com.example.thymeleaf.model.User;
import com.example.thymeleaf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TestController {

    public class NotFoundException extends RuntimeException{

    }

    @Autowired
    UserService userService;

    @GetMapping("/listUsers")
    public String test(Model model) {
        //User user = new User(13,"Grzegorz","Majmurek",28);
        //List<User> users = new ArrayList<>();
        //users.add(new User(24,"Maciej", "Kowaslki" ,25));
        //users.add(new User(27,"Macigdfg", "Kowasdfghsfdlki" ,2554));
        //users.add(new User(2544,"Macgfdghshiej", "Kowfdsfsdaslki" ,263455));
        //users.add(new User(244,"Macgshiej", "Kowfsdaslki" ,255));
        model.addAttribute("users", userService.listUsers());
        return "list-users-view";
    }

    @GetMapping("/getUser/{id}")
    public String getUser(@PathVariable String id, Model model){
        int i = Integer.parseInt(id);
        User user = userService.getUser(i);
        if (user == null){
            throw new NotFoundException();
        }
        model.addAttribute("user",user);
        return "user-details";
    }

    @ExceptionHandler(NumberFormatException.class)
    public String notNumber() {
        return "error";
    }

    @GetMapping("/addUser")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "add-user";
    }

    @PostMapping("/addUser")
    public String createUser(@ModelAttribute User user, BindingResult bindingResult, Model model) {
        validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "redirect:/addUser";
        }
        userService.createUser(user.getImie(), user.getNazwisko(), user.getWiek());
        return "redirect:/listUsers";
    }

    @ExceptionHandler(NotFoundException.class)
    public String notFound() {
        return "404";
    }

    public void validate(User user, BindingResult bindingResult) {
        if (user.getImie() == null || user.getImie().isEmpty()) {
            bindingResult.addError(new ObjectError("imie", "Musisz podać imie"));
        }
        if (user.getNazwisko() == null || user.getNazwisko().isEmpty()) {
            bindingResult.addError(new ObjectError("nazwisko", "Musisz podać nazwisko"));
        }
        if (user.getWiek() < 0) {
            bindingResult.addError(new ObjectError("wiek", "Musisz podać wiek"));
        }
    }
}