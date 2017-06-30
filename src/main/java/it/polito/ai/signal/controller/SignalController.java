package it.polito.ai.signal.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins="*", allowedHeaders={"Accept", "Authorization", "Content-Type"})
@RequestMapping("/signal")
public class SignalController {

}
