package com.dedsec.uranus.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dedsec.uranus.models.certimanager.LlavePrivada;
import com.dedsec.uranus.repositories.LlavePrivadaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LlavePrivadaService {

    private final Logger logger = LoggerFactory.getLogger(LlavePrivadaService.class);

    private final LlavePrivadaRepository llavePrivadaRepository;
    private RSAKeyGenerator rsaKeyGenerator = new RSAKeyGenerator();

    public List<LlavePrivada> obtenerLlavesPrivadas(){
        logger.info("[ METHOD: obtenerLlavesPrivadas() ]: Obteniendo listado de llaves");
        return llavePrivadaRepository.findAll();
    }

    public LlavePrivada generarLlave(){
        try {
            logger.info("[ METHOD: generarLlave() ]: Solicitando llave privada");
            String llave = rsaKeyGenerator.genRSA();
            LlavePrivada llavePrivada = new LlavePrivada();
            llavePrivada.setLlave(llave);
            logger.info("[ METHOD: generarLlave() ]: Guardando Registro de la llave privada");
            llavePrivadaRepository.save(llavePrivada);
            return llavePrivada;
        } catch (Exception e) {
           e.printStackTrace();
           return null;
        }
    }

}

