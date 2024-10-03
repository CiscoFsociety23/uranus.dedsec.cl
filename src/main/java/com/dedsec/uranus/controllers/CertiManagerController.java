package com.dedsec.uranus.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;

import com.dedsec.uranus.models.Certificado;
import com.dedsec.uranus.models.LlavePrivada;
import com.dedsec.uranus.models.SolicitudCertificado;
import com.dedsec.uranus.services.CertificadosService;
import com.dedsec.uranus.services.LlavePrivadaService;
import com.dedsec.uranus.services.SolicitudCertificadoService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/CertiManager")
public class CertiManagerController {

    private final Logger logger = LoggerFactory.getLogger(CertiManagerController.class);

    private final CertificadosService certificadosService;
    private final LlavePrivadaService llavePrivadaService;
    private final SolicitudCertificadoService solicitudCertificadoService;

    @GetMapping("/obtenerCertificados")
    public List<Certificado> obtenerCertificados() {
        logger.info("[GET: /obtenerCertificados ]: Ingresando solicitud para listar certificados");
        return certificadosService.obtenerCertificados();
    }

    @GetMapping("/obtenerLlaves")
    public List<LlavePrivada> obtenerLlaves() {
        logger.info("[GET: /obtenerLlaves ]: Ingresando solicitud para listar llaves");
        return llavePrivadaService.obtenerLlavesPrivadas();
    }
    
    @GetMapping("/obtenerSolicitudes")
    public List<SolicitudCertificado> obtenerSolicitudes() {
        logger.info("[GET: /obtenerSolicitudes ]: Ingresando solicitud para listar solicitudes de certificado");
        return solicitudCertificadoService.obtenerSolicitudesCertificados();
    }

}
