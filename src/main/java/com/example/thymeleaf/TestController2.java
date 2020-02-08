package com.example.thymeleaf;

import com.example.thymeleaf.model.SkierowanieDoLekarza;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

@Controller
public class TestController2 {

    public class NotFoundException extends RuntimeException{
        @GetMapping("/listSkierowanie")
        public String test(Model model) {
            model.addAttribute("patients", skierowanieDoLekarzaService.listSkierowanieDoLekarza());
            return "list-patients-view";
        }

        @GetMapping("/getSkierowanie/{id}")
        public String getUser(@PathVariable String id, Model model){
            int i = Integer.parseInt(id);
            SkierowanieDoLekarza skierowanieDoLekarza = skierowanieDoLekarzaService.getSkierowanieDoLekarza(i);
            if (skierowanieDoLekarza == null){
                throw new TestController2.NotFoundException();
            }
            model.addAttribute("user",user);
            return "user-details";
        }

        @ExceptionHandler(NumberFormatException.class)
        public String notNumber() {
            return "error";
        }

        @GetMapping("/addSkierowanie")
        public String addUser(Model model) {
            model.addAttribute("user", new User());
            return "add-user";
        }

        @PostMapping("/addSkierowanie")
        public String createUser(@ModelAttribute User user, BindingResult bindingResult, Model model) {
            validate(user, bindingResult);
            if (bindingResult.hasErrors()) {
                model.addAttribute("user", user);
                return "redirect:/addUser";
            }
            userService.createUser(user.getImie(), user.getNazwisko(), user.getWiek());
            return "redirect:/listUsers";
        }

        @ExceptionHandler(TestController.NotFoundException.class)
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

}
