package com.dedsec.uranus.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dedsec.uranus.models.SolicitudCertificado;
import com.dedsec.uranus.repositories.SolicitudCertificadoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SolicitudCertificadoService {

    private final Logger logger = LoggerFactory.getLogger(SolicitudCertificadoService.class);

    private final SolicitudCertificadoRepository solicitudCertificadoRepository;

    public List<SolicitudCertificado> obtenerSolicitudesCertificados(){
        logger.info("[METHOD: obtenerSolicitudesCertificados() ]: Obteniendo listado de solicitudes CSR");
        return solicitudCertificadoRepository.findAll();
    }

}
