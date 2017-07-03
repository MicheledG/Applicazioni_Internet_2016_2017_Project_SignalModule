package it.polito.ai.signal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import it.polito.ai.signal.model.Signal;
import it.polito.ai.signal.service.SignalService;

@Controller
public class SignalSocketController {
	
	@Autowired
    private SignalService service;
    
    @MessageMapping("/signal")
    @SendTo("/topic/signals")
    public List<Signal> getScores() {
        
        List<Signal> signals = service.getAll();
        
        return signals;
    }

}
