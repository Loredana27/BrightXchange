package com.codecool.brightxchange.Service;

import com.codecool.brightxchange.Service.modelDAO.DAO.ChipsetProducerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service("ChipsetProducerService")
public class ChipsetProducerService {
    @Autowired
    @Qualifier("ChipsetProducerMemory")
    private ChipsetProducerDAO chipsetProducerDAO;
}