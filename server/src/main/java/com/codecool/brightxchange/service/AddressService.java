package com.codecool.brightxchange.service;

import com.codecool.brightxchange.model.Address;
import com.codecool.brightxchange.repositories.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {
    private final AddressRepository addressRepository;


    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address saveAndFlush(Address address){
        Optional<Address> optionalAddress = addressRepository.findAddress(address.getRecipientName(), address.getCity(), address.getCountryCode(), address.getPostalCode(), address.getState());
        return optionalAddress.orElseGet(() -> addressRepository.saveAndFlush(address));
    }
}
