package ua.tumakha.yuriy.xmpp.light.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.tumakha.yuriy.xmpp.light.domain.User;
import ua.tumakha.yuriy.xmpp.light.service.UserService;

import javax.validation.Valid;

/**
 * @author Yuriy Tumakha.
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ModelAndView list(@SortDefault("username") Pageable pageable) {
        Page<User> users = userService.findAll(pageable);
        return new ModelAndView("users", "users", users);
    }

    @GetMapping("/user")
    public ModelAndView edit(@RequestParam(name = "userId", required = false) Long userId) {
        User user = userId == null ? new User() : userService.findById(userId);
        return new ModelAndView("user", "user", user);
    }

    @PostMapping("/saveUser")
    public String saveUser(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user";
        }
        if (user.getId() == null) {
            userService.createUser(user);
        } else {
            userService.updateUser(user);
        }
        return "redirect:users";
    }


    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam("userId") Long userId) {
        userService.removeUser(userId);
        return "redirect:users";
    }

}
