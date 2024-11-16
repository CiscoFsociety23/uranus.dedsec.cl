package com.dedsec.uranus.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dedsec.uranus.models.asistencia.Empleado;
import com.dedsec.uranus.services.asistencia.EmpleadosService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/AsistenciaManager")
public class AsistenciaController {

    private final Logger logger = LoggerFactory.getLogger(AsistenciaController.class);
    private final EmpleadosService empleadosService;

    @GetMapping("/getAllEmpleados")
    public List<Empleado> getAllEmpleados(){
        logger.info("[ GET /AsistenciaManager/getAllEmpleados ]: Solicitud de listado total empleados.");
        List<Empleado> empleados = empleadosService.getAllEmpleados();
        return empleados;
    }

    @PostMapping("/createEmpleado")
    public Empleado createEmpleado(@RequestBody Empleado dataEmpleado) {
        logger.info("[ POST /AsistenciaManager/createEmpleado ]: Procendando solicitud de creacion de empleado");
        Empleado empleado = empleadosService.createEmpleado(dataEmpleado);
        return empleado;
    }
    

}
