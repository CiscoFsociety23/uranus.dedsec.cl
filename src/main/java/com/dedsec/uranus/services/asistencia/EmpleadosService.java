package com.dedsec.uranus.services.asistencia;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dedsec.uranus.interfaces.asistencia.EmpleadoRepository;
import com.dedsec.uranus.models.asistencia.Empleado;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpleadosService {

    private final Logger logger = LoggerFactory.getLogger(EmpleadosService.class);
    private final EmpleadoRepository empleadoRepository;

    public List<Empleado> getAllEmpleados(){
        try {
            logger.info("[ METHOD: getAllEmpleados() ]: Obtiendo listados de empleados.");
            List<Empleado> empleados = empleadoRepository.findAll();
            return empleados;
        } catch (Exception e) {
            logger.error("[ METHOD: getAllEmpleados() ]: Ha ocurrido un error al obener los usuarios: " + e.getMessage());
            return null;
        }
    }

    public Empleado createEmpleado(Empleado dataEmpleado){
        try {
            logger.info("[ METHOD: createEmpleado() ]: Creando usuario en el sistema");
            Empleado empleado = empleadoRepository.save(dataEmpleado);
            return empleado;
        } catch (Exception e) {
            logger.error("[ METHOD: createEmpleado() ]: Ha ocurrido un error al crear el usuario: " + e.getMessage());
            return null;
        }
    }

    public Boolean verificarCredenciales(String dataCorreo, String dataContrasena){
        try {
            logger.info("[ METHOD: verificarCredenciales() ]: Verificando credenciales del usuario " + dataCorreo);
            Optional<Empleado> empleado = empleadoRepository.findByCorreo(dataCorreo);
            if(empleado.isPresent()){
                Empleado user = empleado.get();
                logger.info("[ METHOD: verificarCredenciales() ]: Usuario encontrado con exito, nombre " + user.getNombre());
                logger.info("[ METHOD: verificarCredenciales() ]: Comparando contraseñas");
                if(user.getContrasena().equals(dataContrasena)){
                    logger.info("[ METHOD: verificarCredenciales() ]: Credenciales ok");
                    return true;
                } else {
                    logger.error("[ METHOD: verificarCredenciales() ]: Contraseña Incorrecta");
                    return false;
                }
            } else {
                logger.error("[ METHOD: verificarCredenciales() ]: No existe usuario " + dataCorreo);
                return false;
            }
        } catch (Exception e) {
            logger.error("[ METHOD: verificarCredenciales() ]: Ha ocurrido un error inesperado: " + e.getMessage());
            return false;
        }
    }
    
}
