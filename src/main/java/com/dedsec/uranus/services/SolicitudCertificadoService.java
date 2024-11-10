package com.dedsec.uranus.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dedsec.uranus.models.LlavePrivada;
import com.dedsec.uranus.models.SolicitudCertificado;
import com.dedsec.uranus.repositories.SolicitudCertificadoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SolicitudCertificadoService {

    private final Logger logger = LoggerFactory.getLogger(SolicitudCertificadoService.class);

    private final SolicitudCertificadoRepository solicitudCertificadoRepository;
    private CSRGenerator csrGenerator = new CSRGenerator();

    public List<SolicitudCertificado> obtenerSolicitudesCertificados(){
        logger.info("[ METHOD: obtenerSolicitudesCertificados() ]: Obteniendo listado de solicitudes CSR");
        return solicitudCertificadoRepository.findAll();
    }

    public void generarCSR(LlavePrivada llave, String nombre, String email){
        try {
            logger.info("[ METHOD: generarCSR() ]: Solicitando CSR para " + email);
            String privateKey = llave.getLlave();
            String csr = csrGenerator.generarCSR(privateKey, nombre, email);
            SolicitudCertificado solicitudCertificado = new SolicitudCertificado();
            solicitudCertificado.setNombreCompleto(nombre);
            solicitudCertificado.setCorreo(email);
            solicitudCertificado.setCsr(csr);
            solicitudCertificado.setLlavePrivada(llave);
            logger.info("[ METHOD: generarCSR() ]: Guardando CSR");
            solicitudCertificadoRepository.save(solicitudCertificado);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
