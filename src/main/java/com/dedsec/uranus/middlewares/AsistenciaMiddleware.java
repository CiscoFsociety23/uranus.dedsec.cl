package com.dedsec.uranus.middlewares;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dedsec.uranus.interfaces.asistencia.EmpleadoRepository;
import com.dedsec.uranus.models.asistencia.Empleado;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AsistenciaMiddleware {

    private final Logger logger = LoggerFactory.getLogger(AsistenciaMiddleware.class);
    private final EmpleadoRepository empleadoRepository;

    public Boolean verificarSiExiste(String rut, String correo){
        try {
            logger.info("[ METHOD: verificarSiExiste() ]: Verificando si los datos " + rut + " y correo " + correo + " estan siendo ocupados");
            Optional<Empleado> getEmpleadoRut = empleadoRepository.findByRut(rut);
            Optional<Empleado> getEmpleadoCorreo = empleadoRepository.findByCorreo(correo);
            if(getEmpleadoRut.isPresent() || getEmpleadoCorreo.isPresent()){
                logger.info("[ METHOD: obtenerEmpleadoRut() ]: No se puede crear el usuario, los datos ya estan siendo opcupados");
                return false;
            } else {
                logger.error("[ METHOD: verificarSiExiste() ]: Los datos pueden ser asignados");
                return true;
            }
        } catch (Exception e) {
            logger.error("[ METHOD: verificarSiExiste() ]: Ha ocurrido un error", e);
            return false;
        }
    }

}
