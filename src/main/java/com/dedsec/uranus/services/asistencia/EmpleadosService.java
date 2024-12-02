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

    public List<Empleado> obtenerEmpleados(){
        try {
            logger.info("[ METHOD: obtenerEmpleados() ]: Obtiendo listados de empleados.");
            List<Empleado> empleados = empleadoRepository.findAll();
            return empleados;
        } catch (Exception e) {
            logger.error("[ METHOD: obtenerEmpleados() ]: Ha ocurrido un error al obener los usuarios: " + e.getMessage());
            return null;
        }
    }

    public Empleado obtenerEmpleadoCorreo(String correo){
        try {
            logger.info("[ METHOD: obtenerEmpleadoCorreo() ]: Obteniendo empleado " + correo);
            Optional<Empleado> getEmpleado = empleadoRepository.findByCorreo(correo);
            if(getEmpleado.isPresent()){
                logger.info("[ METHOD: obtenerEmpleadoCorreo() ]: Empleado " + correo + " obtenido con exito");
                Empleado empleado = getEmpleado.get();
                return empleado;
            } else {
                logger.error("[ METHOD: obtenerEmpleadoCorreo() ]: El empleado " + correo + " no existe");
                return null;
            }
        } catch (Exception e) {
            logger.error("[ METHOD: obtenerEmpleadoCorreo() ]: Ha ocurrido un error ");
            return null;
        }
    }

    public Empleado obtenerEmpleadoRut(String rut){
        try {
            logger.info("[ METHOD: obtenerEmpleadoRut() ]: Obteniendo empleado con rut " + rut);
            Optional<Empleado> getEmpleado = empleadoRepository.findByRut(rut);
            if(getEmpleado.isPresent()){
                logger.info("[ METHOD: obtenerEmpleadoRut() ]: Empleado encrontrado con exito");
                Empleado empleado = getEmpleado.get();
                return empleado;
            } else {
                logger.error("[ METHOD: obtenerEmpleadoRut() ]: El empleado " + rut + " no existe");
                return null;
            }
        } catch (Exception e) {
            logger.error("[ METHOD: obtenerEmpleadoRut() ]: Ha ocurrido un error", e);
            return null;
        }
    }

    public Empleado crearEmpleado(Empleado dataEmpleado){
        try {
            logger.info("[ METHOD: crearEmpleado() ]: Creando usuario en el sistema");
            Empleado empleado = empleadoRepository.save(dataEmpleado);
            return empleado;
        } catch (Exception e) {
            logger.error("[ METHOD: crearEmpleado() ]: Ha ocurrido un error al crear el usuario: " + e.getMessage());
            return null;
        }
    }

    public Empleado actualizarEmpleado(Empleado empleado){
        try {
            logger.info("[ METHOD: actualizarEmpleado() ]: Actualizando el usuario con ID: " + empleado.getIdEmpleado());
            Empleado update = empleadoRepository.save(empleado);
            return update;
        } catch (Exception e) {
            logger.error("[ METHOD: actualizarEmpleado() ]: Ha ocurrido un error al actualizar el usuario", e);
            return null;
        }
    }

    public Boolean borrarEmpleado(Integer idEmpleado){
        try {
            logger.info("[ METHOD: borrarEmpleado() ]: Eliminado usuario con ID: " + idEmpleado);
            empleadoRepository.deleteById(idEmpleado);
            logger.info("[ METHOD: borrarEmpleado() ]: Empleado eliminado con exito");
            return true;
        } catch (Exception e) {
            logger.error("[ METHOD: borrarEmpleado() ]: Ha ocurrido un error al eleminiar el empleado", e);
            return false;
        }
    }

    public Boolean verificarIngreso(String dataRut){
        try {
            logger.info("[ METHOD: verificarIngreso() ]: Verificando el accesos del usuario " + dataRut);
            Optional<Empleado> empleado = empleadoRepository.findByRut(dataRut);
            if(empleado.isPresent()){
                return true;
            } else {
                logger.error("[ METHOD: verificarIngreso() ]: No existe usuario " + dataRut);
                return false;
            }
        } catch (Exception e) {
            logger.error("[ METHOD: verificarIngreso() ]: Ha ocurrido un error inesperado: " + e.getMessage());
            return false;
        }
    }
    
}
