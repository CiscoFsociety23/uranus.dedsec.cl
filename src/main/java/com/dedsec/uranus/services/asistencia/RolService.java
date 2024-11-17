package com.dedsec.uranus.services.asistencia;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dedsec.uranus.interfaces.asistencia.RolRepository;
import com.dedsec.uranus.models.asistencia.Rol;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RolService {

    private final Logger logger = LoggerFactory.getLogger(RolService.class);
    private final RolRepository rolRepository;

    public Rol obtenerRol(String rolName){
        try {
            logger.info("[ METHOD: obtenerRol() ]: Obteniendo rol " + rolName);
            Optional<Rol> getRol = rolRepository.findByRol(rolName);
            if(getRol.isPresent()){
                logger.info("[ METHOD: obtenerRol() ]: Rol " + rolName + " encontrado con exito");
                Rol rol = getRol.get();
                return rol;
            } else {
                logger.error("[ METHOD: obtenerRol() ]: El rol " + rolName + " no existe");
                return null;
            }
        } catch (Exception e) {
            logger.error("[ METHOD: obtenerRol() ]: Ha occurido un error al obtener el rol " + rolName);
            return null;
        }
    }

}
