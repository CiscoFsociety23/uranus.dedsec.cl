package com.dedsec.uranus.services.asistencia;

import java.util.List;

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
        logger.info("[ METHOD: getAllEmpleados() ]: Obtiendo listados de empleados.");
        List<Empleado> empleados = empleadoRepository.findAll();
        return empleados;
    }

    public Empleado createEmpleado(Empleado dataEmpleado){
        logger.info("[ METHOD: createEmpleado() ]: Creando usuario en el sistema");
        Empleado empleado = empleadoRepository.save(dataEmpleado);
        return empleado;
    }
    
}
