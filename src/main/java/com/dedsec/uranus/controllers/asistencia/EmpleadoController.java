package com.dedsec.uranus.controllers.asistencia;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dedsec.uranus.dto.asistencia.EmpleadoCreationRequest;
import com.dedsec.uranus.dto.asistencia.EmpleadoResponse;
import com.dedsec.uranus.models.asistencia.Comuna;
import com.dedsec.uranus.models.asistencia.Contrato;
import com.dedsec.uranus.models.asistencia.Departamento;
import com.dedsec.uranus.models.asistencia.Direccion;
import com.dedsec.uranus.models.asistencia.Empleado;
import com.dedsec.uranus.models.asistencia.Rol;
import com.dedsec.uranus.models.asistencia.Turno;
import com.dedsec.uranus.services.asistencia.ComunaService;
import com.dedsec.uranus.services.asistencia.ContratoService;
import com.dedsec.uranus.services.asistencia.DepartamentoService;
import com.dedsec.uranus.services.asistencia.DireccionService;
import com.dedsec.uranus.services.asistencia.EmpleadosService;
import com.dedsec.uranus.services.asistencia.RolService;
import com.dedsec.uranus.services.asistencia.TurnoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/AsistenciaManager/Empleado")
public class EmpleadoController {

    private final Logger logger = LoggerFactory.getLogger(AsistenciaController.class);
    private final EmpleadosService empleadosService;
    private final ComunaService comunaService;
    private final DireccionService direccionService;
    private final ContratoService contratoService;
    private final DepartamentoService departamentoService;
    private final RolService rolService;
    private final TurnoService turnoService;

    @GetMapping("/getAllEmpleados")
    public ResponseEntity<?> getAllEmpleados(){
        try {
            logger.info("[ GET /AsistenciaManager/Empleado/getAllEmpleados ]: Solicitud de listado total empleados.");
            List<Empleado> empleados = empleadosService.getAllEmpleados();
            List<EmpleadoResponse> empleadoResponses = new ArrayList<>();
            for(Empleado empleado : empleados){
                EmpleadoResponse user = new EmpleadoResponse();
                user.setIdEmpleado(empleado.getIdEmpleado());
                user.setRut(empleado.getRut());
                user.setNombre(empleado.getNombre());
                user.setApellidoPaterno(empleado.getApellidoPaterno());
                user.setApellidoMaterno(empleado.getApellidoMaterno());
                user.setCorreo(empleado.getCorreo());
                user.setContrato(empleado.getContrato().getTipoContrato());
                user.setRol(empleado.getRol().getRol());
                user.setTurno(empleado.getTurno().getTurno());
                user.setHoraEntrada(empleado.getTurno().getHoraTurnoEntrada());
                user.setHoraSalida(empleado.getTurno().getHoraTurnoSalida());
                user.setDireccion(empleado.getDireccion().getDireccion() + ", " + empleado.getDireccion().getComuna().getNombreComuna() + "; " + empleado.getDireccion().getComuna().getRegion().getRegion());
                user.setDepartamento(empleado.getDepartamento().getNombreDepartamento());
                empleadoResponses.add(user);
            }
            return new ResponseEntity<>(empleadoResponses, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[ GET /AsistenciaManager/Empleado/getAllEmpleados ]: Ha occurrido un error al procesar la solicitud: " + e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getEmpleadoByCorreo")
    public ResponseEntity<?> getEmpleado(@RequestParam String correo){
        try {
            logger.info("[ GET /AsistenciaManager/Empleado/getEmpleadoByCorreo ]: Procesando obtencion de empleado " + correo);
            Empleado empleado = empleadosService.getEmpleadoByCorreo(correo);
            EmpleadoResponse user = new EmpleadoResponse();
            user.setIdEmpleado(empleado.getIdEmpleado());
            user.setRut(empleado.getRut());
            user.setNombre(empleado.getNombre());
            user.setApellidoPaterno(empleado.getApellidoPaterno());
            user.setApellidoMaterno(empleado.getApellidoMaterno());
            user.setCorreo(empleado.getCorreo());
            user.setContrato(empleado.getContrato().getTipoContrato());
            user.setRol(empleado.getRol().getRol());
            user.setTurno(empleado.getTurno().getTurno());
            user.setHoraEntrada(empleado.getTurno().getHoraTurnoEntrada());
            user.setHoraSalida(empleado.getTurno().getHoraTurnoSalida());
            user.setDireccion(empleado.getDireccion().getDireccion() + ", " + empleado.getDireccion().getComuna().getNombreComuna() + "; " + empleado.getDireccion().getComuna().getRegion().getRegion());
            user.setDepartamento(empleado.getDepartamento().getNombreDepartamento());
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[ GET /AsistenciaManager/Empleado/getEmpleadoByCorreo ]: Ha ocurrido un error en la obtencion del empleado: " + e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/createEmpleado")
    public ResponseEntity<?> createEmpleado(@RequestBody EmpleadoCreationRequest dataEmpleado) {
        try {
            logger.info("[ POST /AsistenciaManager/Empleado/createEmpleado ]: Procesando solicitud de creacion de empleado");
            Contrato contrato = contratoService.guardarContrato(dataEmpleado.getContrato());
            Rol rol = rolService.obtenerRol(dataEmpleado.getRol());
            Departamento departamento = departamentoService.obtenerDepartamento(dataEmpleado.getDepartamento());
            Turno turno = turnoService.obtenerTurno(dataEmpleado.getTurno());
            Comuna comuna = comunaService.getComuna(dataEmpleado.getComuna());
            Direccion direccion = direccionService.crearDireccion(dataEmpleado.getDireccion(), comuna);
            Empleado saveEmpleado = new Empleado();
            saveEmpleado.setRut(dataEmpleado.getRut());
            saveEmpleado.setNombre(dataEmpleado.getNombre());
            saveEmpleado.setApellidoPaterno(dataEmpleado.getApellidoPaterno());
            saveEmpleado.setApellidoMaterno(dataEmpleado.getApellidoMaterno());
            saveEmpleado.setCorreo(dataEmpleado.getCorreo());
            saveEmpleado.setContrasena(dataEmpleado.getContrasena());
            saveEmpleado.setContrato(contrato);
            saveEmpleado.setRol(rol);
            saveEmpleado.setDepartamento(departamento);
            saveEmpleado.setTurno(turno);
            saveEmpleado.setDireccion(direccion);
            Empleado empleado = empleadosService.createEmpleado(saveEmpleado);
            logger.info("[ POST /AsistenciaManager/Empleado/createEmpleado ]: Usuario creado con exito");
            EmpleadoResponse user = new EmpleadoResponse();
            user.setIdEmpleado(empleado.getIdEmpleado());
            user.setRut(empleado.getRut());
            user.setNombre(empleado.getNombre());
            user.setApellidoPaterno(empleado.getApellidoPaterno());
            user.setApellidoMaterno(empleado.getApellidoMaterno());
            user.setCorreo(empleado.getCorreo());
            user.setContrato(empleado.getContrato().getTipoContrato());
            user.setRol(empleado.getRol().getRol());
            user.setTurno(empleado.getTurno().getTurno());
            user.setHoraEntrada(empleado.getTurno().getHoraTurnoEntrada());
            user.setHoraSalida(empleado.getTurno().getHoraTurnoSalida());
            user.setDireccion(empleado.getDireccion().getDireccion() + ", " + empleado.getDireccion().getComuna().getNombreComuna() + "; " + empleado.getDireccion().getComuna().getRegion().getRegion());
            user.setDepartamento(empleado.getDepartamento().getNombreDepartamento());
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("[ POST /AsistenciaManager/Empleado/createEmpleado ]: Ha ocurrido un error al crear el usuario: " + e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/borrarEmpleado")
    public ResponseEntity<?> borrarEmpleado(@RequestParam Integer idEmpleado){
        try {
            logger.info("[ DELETE /AsistenciaManager/Empleado/borrarEmpleado ]: Solicitud de eliminacion de usuario con ID: " + idEmpleado);
            Boolean delete = empleadosService.borrarEmpleado(idEmpleado);
            if(delete) {
                return new ResponseEntity<>(delete, HttpStatus.OK);
            } else {
                logger.error("[ DELETE /AsistenciaManager/Empleado/borrarEmpleado ]: Usuario no eliminado");
                return new ResponseEntity<>(delete, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.error("[ DELETE /AsistenciaManager/Empleado/borrarEmpleado ]: Ha ocurrido un error al procesar la solicitud", e);
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

}
