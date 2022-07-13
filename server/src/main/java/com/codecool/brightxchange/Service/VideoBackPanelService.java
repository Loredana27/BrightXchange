package com.codecool.brightxchange.Service;

import com.codecool.brightxchange.Service.modelDAO.DAO.VideoBackPanelDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service("VideoBackPanelService")
public class VideoBackPanelService {
    @Autowired
    @Qualifier("VideoBackPanelMemory")
    private VideoBackPanelDAO videoBackPanelDAO;
}