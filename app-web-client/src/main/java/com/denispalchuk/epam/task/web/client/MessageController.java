package com.denispalchuk.epam.task.web.client;

import com.denispalchuk.epam.task.domain.Message;
import com.denispalchuk.epam.task.domain.User;
import com.denispalchuk.epam.task.service.rest.MessageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by denis on 11/29/14.
 */
@Controller
public class MessageController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    MessageService messageService;

    @RequestMapping("/inputMessage")
    public ModelAndView launchInputForm() {
        return new ModelAndView("inputMessage", "message", new Message());
    }

    @RequestMapping("/submitMessage")
    public String getInputForm(@RequestParam("FromUserId")Long fromUserId, @RequestParam("ToUserId")Long toUserId,
                               @RequestParam("Text")String messageText) {
        Message message = new Message();
        message.setMessageFromUserId(fromUserId);
        message.setMessageToUserId(toUserId);
        message.setMessageText(messageText);
        messageService.addMessage(message);
        return "redirect:/admin/messagesList";
    }

    @RequestMapping("/deleteMessage")
    public String deleteUser(@RequestParam("messageId")Long messageId) {
        messageService.removeMessage(messageId);
        return "redirect:/admin/messagesList";
    }

    @RequestMapping("/admin/messagesList")
    public ModelAndView getListUsersView() {
        List<Message> messages = messageService.getAllMessages();
        LOGGER.debug("messages.size = " + messages.size());
        ModelAndView view = new ModelAndView("messagesList", "messages", messages);
        return view;
    }

    @RequestMapping("/admin/filterList")
    public ModelAndView getMessagesByPeriod(@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        List<Message> messages = messageService.getAllMessagesByTimePeriod(formatter.parseLocalDateTime(fromDate),
                formatter.parseLocalDateTime(toDate));
        LOGGER.debug("messages.size = " + messages.size());
        ModelAndView view = new ModelAndView("messagesList", "messages", messages);
        return view;
    }
}
