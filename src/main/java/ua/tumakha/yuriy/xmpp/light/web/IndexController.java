package ua.tumakha.yuriy.xmpp.light.web;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Yuriy Tumakha.
 */
@Controller
@RequestMapping("/")
public class IndexController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @GetMapping
    public ModelAndView home() {
        return new ModelAndView("redirect:users");
    }

    @RequestMapping(value = ERROR_PATH)
    public String error(HttpServletRequest request, Model model) {
        model.addAttribute("statusCode", request.getAttribute("javax.servlet.error.status_code"));

        Object exceptionObject = request.getAttribute("javax.servlet.error.exception");
        String exception = exceptionObject == null ? "" : exceptionObject.toString();
        model.addAttribute("exception", exception);
        return "error";
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

}
