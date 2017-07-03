package it.polito.ai.signal.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import it.polito.ai.signal.service.SignalService;

@Configuration
@EnableScheduling
public class SignalScheduler {

	@Autowired
    private SimpMessagingTemplate template;
    
    @Autowired
    SignalService service;

    @Scheduled(fixedRate = 5000)
    public void publishUpdates(){
       
        template.convertAndSend("/topic/signals", service.getAll());
        
    }
    
    @Scheduled(fixedRate = 60 * 1000)
    public void clearRatings() {
    	service.clearRatings();
    }
	
}
