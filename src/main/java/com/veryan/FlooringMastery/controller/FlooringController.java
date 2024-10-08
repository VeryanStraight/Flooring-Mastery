package com.veryan.FlooringMastery.controller;

import com.veryan.FlooringMastery.serviceLayer.ServiceImpl;
import com.veryan.FlooringMastery.veiw.ConsoleVeiw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class FlooringController {
    private final ServiceImpl service;
    private final ConsoleVeiw consoleVeiw;

    @Autowired
    public FlooringController(ServiceImpl service, ConsoleVeiw consoleVeiw) {
        this.service = service;
        this.consoleVeiw = consoleVeiw;
    }

    public void run() {
    }
}
