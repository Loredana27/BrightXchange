package com.codecool.brightxchange.Service;

import com.codecool.brightxchange.Service.modelDAO.DAO.SsdDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("SsdService")
public class SsdService {

    @Autowired
    @Qualifier("SsdMemory")
    private SsdDAO ssdDAO;
}
