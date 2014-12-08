package com.denispalchuk.epam.task.rest;

import com.denispalchuk.epam.task.domain.Message;
import com.denispalchuk.epam.task.service.MessageService;
import org.joda.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by denis on 11/26/14.
 */
@Controller
@RequestMapping("/messages")
public class MessageRestController {
    @Resource
    private MessageService messageService;

    public void setMessageService(MessageService userService) {
        this.messageService = messageService;
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) {
        try {
            Message message = messageService.getMessageById(id);
            return new ResponseEntity(message, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity("Message not found for id=" + id + " error:"
                    + ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Message>> getMessages() {
        List messages = messageService.getAllMessages();
        return new ResponseEntity(messages, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity updateMessage(@RequestBody Message message) {
        messageService.updateMessage(message);
        return new ResponseEntity("", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Long> addMessage(@RequestBody Message message) {
        Long id = messageService.addMessage(message);
        return new ResponseEntity(id, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity removeMessage(@PathVariable Long id) {
        messageService.removeMessage(id);
        return new ResponseEntity("", HttpStatus.OK);
    }

    @RequestMapping(value = "/bytime", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<Message>> getMessagesByTimePeriod(@RequestBody Map<String,LocalDateTime> params) {
        List messages = messageService.getAllMessagesByTimePeriod(params.get("startDateTime"),params.get("finishDateTime"));
        return new ResponseEntity(messages, HttpStatus.OK);
    }
}
