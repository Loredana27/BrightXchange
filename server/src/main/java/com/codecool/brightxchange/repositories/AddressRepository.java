package com.codecool.brightxchange.repositories;

import com.codecool.brightxchange.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
    @Query("select a from Address a where a.recipientName = ?1 and a.city = ?2 and a.countryCode = ?3 and a.postalCode = ?4 and a.state = ?5")
    Optional<Address> findAddress(String recipientName,String city,String countryCode, String postalCode,String state );
}
