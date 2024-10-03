package com.dedsec.uranus.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dedsec.uranus.models.LlavePrivada;
import com.dedsec.uranus.repositories.LlavePrivadaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LlavePrivadaService {

    private final Logger logger = LoggerFactory.getLogger(LlavePrivadaService.class);

    private final LlavePrivadaRepository llavePrivadaRepository;

    public List<LlavePrivada> obtenerLlavesPrivadas(){
        logger.info("[METHOD: obtenerLlavesPrivadas() ]: Obteniendo listado de llaves");
        return llavePrivadaRepository.findAll();
    }

}

