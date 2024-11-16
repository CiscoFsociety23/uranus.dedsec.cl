package com.dedsec.uranus.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dedsec.uranus.interfaces.certimanager.CertificadoRepository;
import com.dedsec.uranus.models.certimanager.Certificado;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CertificadosService {

    private final Logger logger = LoggerFactory.getLogger(CertificadosService.class);

    private final CertificadoRepository certificadoRepository;

    public List<Certificado> obtenerCertificados(){
        logger.info("[ METHOD: obtenerCertificados() ]: Obteniendo listado de certificados");
        return certificadoRepository.findAll();
    }

}
