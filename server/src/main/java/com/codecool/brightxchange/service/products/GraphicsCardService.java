package com.codecool.brightxchange.service.products;

import com.codecool.brightxchange.repositories.products.GraphicsCardDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("GraphicsCardService")
public class GraphicsCardService{
    @Autowired
    private GraphicsCardDAO graphicsCardDAO;
}