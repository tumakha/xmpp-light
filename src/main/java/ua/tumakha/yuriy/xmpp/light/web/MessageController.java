package ua.tumakha.yuriy.xmpp.light.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.tumakha.yuriy.xmpp.light.domain.Message;
import ua.tumakha.yuriy.xmpp.light.service.MessageService;

import javax.servlet.http.HttpSession;

/**
 * @author Yuriy Tumakha.
 */
@Controller
@RequestMapping("/messages")
@SessionAttributes("searchForm")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping
    public ModelAndView list(@RequestParam(name = "clear", required = false) String clear,
                             HttpSession httpSession, @SortDefault("time") Pageable pageable) {
        if (clear != null || httpSession.getAttribute("searchForm") == null) {
            httpSession.setAttribute("searchForm", new SearchForm());
        }
        SearchForm searchForm = (SearchForm) httpSession.getAttribute("searchForm");
        Specification<Message> specification = searchForm.toSpecification();
        Page<Message> messages = specification == null ? messageService.findAll(pageable)
                : messageService.findAll(specification, pageable);

        ModelAndView modelAndView = new ModelAndView("messages", "messages", messages);
        modelAndView.addObject("searchForm", searchForm);
        return modelAndView;
    }

    @PostMapping
    public String search(@ModelAttribute("searchForm") SearchForm searchForm) {

        return "redirect:/messages";
    }

}
