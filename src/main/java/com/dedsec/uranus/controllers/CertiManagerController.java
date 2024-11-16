package com.dedsec.uranus.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.dedsec.uranus.middlewares.CertiManagerMiddleware;
import com.dedsec.uranus.models.certimanager.Certificado;
import com.dedsec.uranus.models.certimanager.LlavePrivada;
import com.dedsec.uranus.models.certimanager.SolicitudCertificado;
import com.dedsec.uranus.services.certimanager.CertificadosService;
import com.dedsec.uranus.services.certimanager.LlavePrivadaService;
import com.dedsec.uranus.services.certimanager.PropertyService;
import com.dedsec.uranus.services.certimanager.SolicitudCertificadoService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/CertiManager")
public class CertiManagerController {

    private final Logger logger = LoggerFactory.getLogger(CertiManagerController.class);

    private final CertificadosService certificadosService;
    private final LlavePrivadaService llavePrivadaService;
    private final SolicitudCertificadoService solicitudCertificadoService;
    private final PropertyService propertyService;
    private final CertiManagerMiddleware certiManagerMiddleware;

    @GetMapping("/redinessCerti")
    public ResponseEntity<?> redinessProbe(){
        try {
            logger.info("[ GET: /redinessCerti ]: Obteniendo rediness probe");
            String rediness_value = propertyService.getProperty("Certi Manager Rediness Value");
            Boolean rediness = Boolean.parseBoolean(rediness_value);
            return new ResponseEntity<>(rediness, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[ GET: /redinessCerti ]: Ha ocurrido un error el obtener rediness probe");
            logger.error("[ GET: /redinessCerti ]: " + e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

    @GetMapping("/obtenerCertificados")
    public List<Certificado> obtenerCertificados(@RequestHeader("Authorization") String bearerToken) {
        logger.info("[ GET: /obtenerCertificados ]: Verificando autorizacion del usuario");
        String token = bearerToken.split(" ")[1];
        if(certiManagerMiddleware.verificarAdminProfile(token)){
            logger.info("[ GET: /obtenerCertificados ]: Ingresando solicitud para listar certificados");
            return certificadosService.obtenerCertificados();
        } else {
            logger.error("[ GET: /obtenerCertificados ]: Error al verificar el perfil");
            return null;
        }
    }

    @GetMapping("/obtenerLlaves")
    public List<LlavePrivada> obtenerLlaves(@RequestHeader("Authorization") String bearerToken) {
        logger.info("[ GET: /obtenerLlaves ]: Verificando autorizacion del usuario");
        String token = bearerToken.split(" ")[1];
        if(certiManagerMiddleware.verificarAdminProfile(token)){
            logger.info("[ GET: /obtenerLlaves ]: Ingresando solicitud para listar llaves");
            return llavePrivadaService.obtenerLlavesPrivadas();
        } else {
            logger.error("[ GET: /obtenerLlaves ]: Error al verificar el perfil");
            return null;
        }
    }
    
    @GetMapping("/obtenerSolicitudes")
    public List<SolicitudCertificado> obtenerSolicitudes(@RequestHeader("Authorization") String bearerToken) {
        logger.info("[ GET: /obtenerSolicitudes ]: Verificando autorizacion del usuario");
        String token = bearerToken.split(" ")[1];
        if(certiManagerMiddleware.verificarAdminProfile(token)){
            logger.info("[ GET: /obtenerSolicitudes ]: Ingresando solicitud para listar solicitudes de certificado");
            return solicitudCertificadoService.obtenerSolicitudesCertificados();
        } else {
            logger.error("[ GET: /obtenerSolicitudes ]: Error al verificar el perfil");
            return null;
        }
    }

    @PostMapping("/generarSolicitud")
    public void generarSolicitud(@RequestParam String nombre, @RequestParam String email, @RequestHeader("Authorization") String bearerToken){
        logger.info("[ POST: /generarSolicitud ]: Verificando autorizacion del usuario");
        String token = bearerToken.split(" ")[1];
        if(certiManagerMiddleware.verificarAdminProfile(token)){
            logger.info("[ POST: /generarSolicitud ]: Generando solicitud de certificado");
            LlavePrivada llavePrivada = llavePrivadaService.generarLlave();
            solicitudCertificadoService.generarCSR(llavePrivada, nombre, email);
        } else {
            logger.error("[ POST: /generarSolicitud ]: Error al verificar el perfil");
        }
    }

}
