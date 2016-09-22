package ua.tumakha.yuriy.xmpp.light.web;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
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
    public ModelAndView error(HttpServletRequest request) {
        String error = request.getAttribute("javax.servlet.error.exception").toString();
        return new ModelAndView("error", "error", error);
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

}
