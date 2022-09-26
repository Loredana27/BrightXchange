package com.codecool.brightxchange.service;

import com.codecool.brightxchange.model.Producer;
import com.codecool.brightxchange.repositories.ProducerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProducerService {

    private final ProducerRepository repository;

    public ProducerService(ProducerRepository repository) {
        this.repository = repository;
    }

    public Producer addProducerService(Producer producer) {
        Optional<Producer> optionalProducer = repository.findByName(producer.getName());
        return optionalProducer.orElseGet(() -> repository.saveAndFlush(producer));
    }

    public List<Producer> getAll(){
        return repository.findAll();
    }
    public List<String> getAllNames(){
        return repository.findAll().stream().map(Producer::getName).collect(Collectors.toList());
    }

}
