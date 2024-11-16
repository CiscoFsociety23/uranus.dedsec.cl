package com.dedsec.uranus.services.certimanager;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dedsec.uranus.interfaces.certimanager.PropertyRepository;
import com.dedsec.uranus.models.certimanager.Property;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final Logger logger = LoggerFactory.getLogger(PropertyService.class);
    private final PropertyRepository propertyRepository;

    public String getProperty(String propName){
        logger.info("[ METHOD: getProperty() ]: Obteniendo propiedad: " + propName);
        Optional<Property> prop = propertyRepository.findByProp(propName);
        return prop.get().getValue();
    }

}