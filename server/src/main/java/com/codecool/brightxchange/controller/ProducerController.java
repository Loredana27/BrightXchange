package com.codecool.brightxchange.controller;

import com.codecool.brightxchange.model.Producer;
import com.codecool.brightxchange.service.ProducerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("producers")
public class ProducerController {

    private final ProducerService producerService;

    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }

    public void addProducer(Producer producer) {
        producerService.addProducerService(producer);
    }
}
