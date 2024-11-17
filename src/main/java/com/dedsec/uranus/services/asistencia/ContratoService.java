package com.dedsec.uranus.services.asistencia;


import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dedsec.uranus.interfaces.asistencia.ContratoRepository;
import com.dedsec.uranus.models.asistencia.Contrato;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContratoService {

    private final Logger logger = LoggerFactory.getLogger(ContratoService.class);
    private final ContratoRepository contratoRepository;

    public Contrato guardarContrato(String tipoContrato){
        try {
            logger.info("[ METHOD: guardarContrato() ]: Generando nuevo contrato, tipo: " + tipoContrato);
            Contrato contrato = new Contrato();
            contrato.setTipoContrato(tipoContrato);
            contrato.setFechaContrato(new Date());
            return contratoRepository.save(contrato);
        } catch (Exception e){
            logger.error("[ METHOD: guardarContrato() ]: Ha ocurrido un error al guardar el contrato: " + e.getMessage());
            return null;
        }
    }

}
