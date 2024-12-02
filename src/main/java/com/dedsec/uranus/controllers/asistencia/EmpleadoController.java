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
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/obtenerEmpleados")
    public ResponseEntity<?> obtenerEmpleados(){
        try {
            logger.info("[ GET /AsistenciaManager/Empleado/obtenerEmpleados ]: Solicitud de listado total empleados.");
            List<Empleado> empleados = empleadosService.obtenerEmpleados();
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
            logger.error("[ GET /AsistenciaManager/Empleado/obtenerEmpleados ]: Ha occurrido un error al procesar la solicitud: " + e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/obtenerEmpleadoCorreo")
    public ResponseEntity<?> getEmpleado(@RequestParam String correo){
        try {
            logger.info("[ GET /AsistenciaManager/Empleado/obtenerEmpleadoCorreo ]: Procesando obtencion de empleado " + correo);
            Empleado empleado = empleadosService.obtenerEmpleadoCorreo(correo);
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
            logger.error("[ GET /AsistenciaManager/Empleado/obtenerEmpleadoCorreo ]: Ha ocurrido un error en la obtencion del empleado: " + e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/obtenerEmpleadoRut")
    public ResponseEntity<?> obtenerEmpleadoRut(@RequestParam String rut){
        try {
            logger.info("[ GET /AsistenciaManager/Empleado/obtenerEmpleadoRut ]: Procesando obtencion de empleado " + rut);
            Empleado empleado = empleadosService.obtenerEmpleadoRut(rut);
            EmpleadoResponse empleadoResponse = new EmpleadoResponse();
            empleadoResponse.setIdEmpleado(empleado.getIdEmpleado());
            empleadoResponse.setRut(empleado.getRut());
            empleadoResponse.setNombre(empleado.getNombre());
            empleadoResponse.setApellidoPaterno(empleado.getApellidoPaterno());
            empleadoResponse.setApellidoMaterno(empleado.getApellidoMaterno());
            empleadoResponse.setCorreo(empleado.getCorreo());
            empleadoResponse.setContrato(empleado.getContrato().getTipoContrato());
            empleadoResponse.setRol(empleado.getRol().getRol());
            empleadoResponse.setTurno(empleado.getTurno().getTurno());
            empleadoResponse.setHoraEntrada(empleado.getTurno().getHoraTurnoEntrada());
            empleadoResponse.setHoraSalida(empleado.getTurno().getHoraTurnoSalida());
            empleadoResponse.setDireccion(empleado.getDireccion().getDireccion() + ", " + empleado.getDireccion().getComuna().getNombreComuna() + "; " + empleado.getDireccion().getComuna().getRegion().getRegion());
            empleadoResponse.setDepartamento(empleado.getDepartamento().getNombreDepartamento());
            return new ResponseEntity<>(empleadoResponse, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[ GET /AsistenciaManager/Empleado/obtenerEmpleadoRut ]: Ha ocurrido un error en la obtencion del empleado: " + e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }
        

    @PostMapping("/crearEmpleado")
    public ResponseEntity<?> crearEmpleado(@RequestBody EmpleadoCreationRequest dataEmpleado) {
        try {
            logger.info("[ POST /AsistenciaManager/Empleado/crearEmpleado ]: Procesando solicitud de creacion de empleado");
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
            Empleado empleado = empleadosService.crearEmpleado(saveEmpleado);
            logger.info("[ POST /AsistenciaManager/Empleado/crearEmpleado ]: Usuario creado con exito");
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
            logger.error("[ POST /AsistenciaManager/Empleado/crearEmpleado ]: Ha ocurrido un error al crear el usuario: " + e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/actualizarEmpleado")
    public ResponseEntity<?> actualizarEmpleado(@RequestBody EmpleadoCreationRequest empleadoRequest){
        try {
            logger.info("[ PUT /AsistenciaManager/Empleado/actualizarEmpleado ]: Procesando solicitud de actualizacion usuario: " + empleadoRequest.getCorreo());
            Contrato contrato = contratoService.guardarContrato(empleadoRequest.getContrato());
            Rol rol = rolService.obtenerRol(empleadoRequest.getRol());
            Departamento departamento = departamentoService.obtenerDepartamento(empleadoRequest.getDepartamento());
            Turno turno = turnoService.obtenerTurno(empleadoRequest.getTurno());
            Comuna comuna = comunaService.getComuna(empleadoRequest.getComuna());
            Direccion direccion = direccionService.crearDireccion(empleadoRequest.getDireccion(), comuna);
            Empleado empleado = empleadosService.obtenerEmpleadoCorreo(empleadoRequest.getCorreo());
            empleado.setRut(empleadoRequest.getRut());
            empleado.setNombre(empleadoRequest.getNombre());
            empleado.setApellidoPaterno(empleadoRequest.getApellidoPaterno());
            empleado.setApellidoMaterno(empleadoRequest.getApellidoMaterno());
            empleado.setCorreo(empleadoRequest.getCorreo());
            empleado.setContrasena(empleadoRequest.getContrasena());
            empleado.setContrato(contrato);
            empleado.setRol(rol);
            empleado.setDepartamento(departamento);
            empleado.setTurno(turno);
            empleado.setDireccion(direccion);
            Empleado employee = empleadosService.actualizarEmpleado(empleado);
            EmpleadoResponse user = new EmpleadoResponse();
            user.setIdEmpleado(employee.getIdEmpleado());
            user.setRut(employee.getRut());
            user.setNombre(employee.getNombre());
            user.setApellidoPaterno(employee.getApellidoPaterno());
            user.setApellidoMaterno(employee.getApellidoMaterno());
            user.setCorreo(employee.getCorreo());
            user.setContrato(employee.getContrato().getTipoContrato());
            user.setRol(employee.getRol().getRol());
            user.setTurno(employee.getTurno().getTurno());
            user.setHoraEntrada(employee.getTurno().getHoraTurnoEntrada());
            user.setHoraSalida(employee.getTurno().getHoraTurnoSalida());
            user.setDireccion(employee.getDireccion().getDireccion() + ", " + employee.getDireccion().getComuna().getNombreComuna() + "; " + employee.getDireccion().getComuna().getRegion().getRegion());
            user.setDepartamento(employee.getDepartamento().getNombreDepartamento());
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[ PUT /AsistenciaManager/Empleado/actualizarEmpleado ]: Ha ocurrido un error al procesar la solicitud", e);
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
