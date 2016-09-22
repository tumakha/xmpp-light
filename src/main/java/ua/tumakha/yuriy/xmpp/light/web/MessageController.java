package ua.tumakha.yuriy.xmpp.light.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.tumakha.yuriy.xmpp.light.domain.Message;
import ua.tumakha.yuriy.xmpp.light.service.MessageService;

/**
 * @author Yuriy Tumakha.
 */
@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/messages")
    public ModelAndView list() {
        Page<Message> messages = messageService.findAll(new PageRequest(0, 100));
        return new ModelAndView("messages", "messages", messages);
    }

}
