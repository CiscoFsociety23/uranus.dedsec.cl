package com.dedsec.uranus.controllers.asistencia;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dedsec.uranus.dto.asistencia.AtrasosResponse;
import com.dedsec.uranus.dto.asistencia.AusenciaResponse;
import com.dedsec.uranus.dto.asistencia.CorreccionAsistenciaResponse;
import com.dedsec.uranus.dto.asistencia.CorreccionMarcaRequest;
import com.dedsec.uranus.dto.asistencia.RegistroAsistenciaResponse;
import com.dedsec.uranus.dto.asistencia.SalidasAnticipadasResponse;
import com.dedsec.uranus.models.asistencia.Asistencia;
import com.dedsec.uranus.models.asistencia.Atrasos;
import com.dedsec.uranus.models.asistencia.CorreccionAsistencia;
import com.dedsec.uranus.models.asistencia.Empleado;
import com.dedsec.uranus.models.asistencia.SalidaAnticipada;
import com.dedsec.uranus.services.asistencia.AsistenciaService;
import com.dedsec.uranus.services.asistencia.AtrasosService;
import com.dedsec.uranus.services.asistencia.CorreccionAsistenciaService;
import com.dedsec.uranus.services.asistencia.EmpleadosService;
import com.dedsec.uranus.services.asistencia.SalidaAnticipadaService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("/AsistenciaManager")
public class AsistenciaController {

    private final Logger logger = LoggerFactory.getLogger(AsistenciaController.class);
    private final EmpleadosService empleadosService;
    private final AsistenciaService asistenciaService;
    private final AtrasosService atrasosService;
    private final SalidaAnticipadaService salidaAnticipadaService;
    private final CorreccionAsistenciaService correccionAsistenciaService;

    @PostMapping("/verificarAcceso")
    public Boolean verificarAcceso(@RequestParam String rut) {
        try {
            logger.info("[ POST /AsistenciaManager/verificarAcceso ]: Verificando accesos del usuario");
            Boolean access = empleadosService.verificarIngreso(rut);
            return access;
        } catch (Exception e) {
            logger.info("[ POST /AsistenciaManager/verificarAcceso ]: Ha ocurrido un error al verificar el acceso: " + e.getMessage());
            return false;
        }
    }

    @PostMapping("/registarMarca")
    public ResponseEntity<?> registarMarca(@RequestParam String rut, @RequestParam String tipoMarca){
        try {
            logger.info("[ POST /AsistenciaManager/registarMarca ]: Procesando el registro de marca " + tipoMarca + " del usuario " + rut);
            Boolean marcar = asistenciaService.registarMarca(rut, tipoMarca);
            return new ResponseEntity<>(marcar, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("[ POST /AsistenciaManager/registarMarca ]: Ha ocurrido un error al procesar la solicitud", e);
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/obtenerAtrasos")
    public ResponseEntity<?> obtenerAtrasos(){
        try {
            logger.info("[ GET /AsistenciaManager/obtenerAtrasos ]: Procesando listado de atrasos");
            List<Atrasos> getAtrasos = atrasosService.obtenerAtrasos();
            List<AtrasosResponse> atrasos = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            for(Atrasos delay : getAtrasos){
                AtrasosResponse atrasosResponse = new AtrasosResponse();
                atrasosResponse.setFechaRegistro(delay.getFechaRegistro().toInstant().atZone(ZoneId.systemDefault()).format(formatter));
                atrasosResponse.setHoraEntrada(delay.getHoraEntrada());
                atrasosResponse.setNombreEmpleado(delay.getEmpleado().getNombre());
                atrasosResponse.setRutEmpleado(delay.getEmpleado().getRut());
                atrasosResponse.setTurnoEmpleado(delay.getEmpleado().getTurno().getTurno());
                atrasos.add(atrasosResponse);
            }
            return new ResponseEntity<>(atrasos, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[ GET /AsistenciaManager/obtenerAtrasos ]: Ha ocurrido un error al procesar la solicitud", e);
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/obtenerSalidasAnticipadas")
    public ResponseEntity<?> obtenerSalidasAnticipadas() {
        try {
            logger.info("[ GET /AsistenciaManager/obtenerSalidasAnticipadas ]: Procesando listado de salidas anticipadas");
            List<SalidaAnticipada> getSalidas = salidaAnticipadaService.obtenerSalidasAnticipadas();
            List<SalidasAnticipadasResponse> salidas = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            for(SalidaAnticipada ext : getSalidas){
                SalidasAnticipadasResponse exit = new SalidasAnticipadasResponse();
                exit.setFechaRegistro(ext.getFechaRegistro().toInstant().atZone(ZoneId.systemDefault()).format(formatter));
                exit.setNombreEmpleado(ext.getEmpleado().getNombre());
                exit.setRutEmpleado(ext.getEmpleado().getRut());
                exit.setTurnoEmpleado(ext.getEmpleado().getTurno().getTurno());
                exit.setHoraSalida(ext.getHoraSalida());
                salidas.add(exit);
            }
            return new ResponseEntity<>(salidas, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[ GET /AsistenciaManager/obtenerSalidasAnticipadas ]: Ha ocurrido un error al procesar la solicitud", e);
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/empleadosAusentes")
    public ResponseEntity<?> empleadosAusentes(@RequestParam String fecha) {
        try {
            logger.info("[ GET /AsistenciaManager/empleadosAusentes ]: Obteniendo listado de empleados ausentes");
            List<Empleado> empleados = empleadosService.obtenerEmpleadosSinAsistencia(new SimpleDateFormat("dd-MM-yyyy").parse(fecha));
            List<AusenciaResponse> ausencias = new ArrayList<>();
            for (Empleado empleado : empleados){
                AusenciaResponse aus = new AusenciaResponse();
                aus.setFechaRegistro(fecha);
                aus.setNombreEmpleado(empleado.getNombre());
                aus.setRut(empleado.getRut());
                aus.setTurno(empleado.getTurno().getTurno());
                ausencias.add(aus);
            }
            return new ResponseEntity<>(ausencias, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[ GET /AsistenciaManager/empleadosAusentes ]: Ha ocurrido un error al procesar la solicitud", e);
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/corregirMarca")
    public ResponseEntity<?> corregirMarca(@RequestBody CorreccionMarcaRequest correccion) {
        try {
            logger.info("[ POST /AsistenciaManager/corregirMarca ]: Procesando correccion de marca");
            Boolean realizarCorreccion = correccionAsistenciaService.corregirMarca(correccion);
            return new ResponseEntity<>(realizarCorreccion, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[ POST /AsistenciaManager/corregirMarca ]: Ha ocurrido un error al procesar la solicitud", e);
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/obtenerCorrecciones")
    public ResponseEntity<?> obtenerCorrecciones(){
        try {
            logger.info("[ GET /AsistenciaManager/obtenerCorrecciones ]: Procesando listado de correcciones");
            List<CorreccionAsistencia> getCorreccionAsistencias = correccionAsistenciaService.obtenerCorrecciones();
            List<CorreccionAsistenciaResponse> correcciones = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            for(CorreccionAsistencia correccion : getCorreccionAsistencias){
                CorreccionAsistenciaResponse correccionAsistencia = new CorreccionAsistenciaResponse();
                correccionAsistencia.setFechaCorreccion(correccion.getFechaCorreccion().toInstant().atZone(ZoneId.systemDefault()).format(formatter));
                correccionAsistencia.setNombre(correccion.getAsistencia().getEmpleado().getNombre());
                correccionAsistencia.setRut(correccion.getAsistencia().getEmpleado().getRut());
                correccionAsistencia.setHoraEntrada(correccion.getHoraEntrada());
                correccionAsistencia.setHoraSalida(correccion.getHoraSalida());
                correccionAsistencia.setMotivo(correccion.getTipoCorreccion());
                correcciones.add(correccionAsistencia);
            }
            return new ResponseEntity<>(correcciones, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[ GET /AsistenciaManager/obtenerCorrecciones ]: Ha ocurrido un error al procesar la solicitud", e);
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/obtenerAsistencias")
    public ResponseEntity<?> obtenerAsistencias(){
        try {
            logger.info("[ GET /AsistenciaManager/obtenerAsistencias ]: Procesando listado de asistencias");
            List<Asistencia> asistencias = asistenciaService.obtenerAsistencias();
            List<RegistroAsistenciaResponse> registroAsistencia = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            for(Asistencia asistencia : asistencias){
                RegistroAsistenciaResponse asis = new RegistroAsistenciaResponse();
                asis.setIdAsistencia(asistencia.getIdAsistencia());
                asis.setFechaMarca(asistencia.getFechaMarcaje().toInstant().atZone(ZoneId.systemDefault()).format(formatter));
                asis.setNombre(asistencia.getEmpleado().getNombre());
                asis.setRut(asistencia.getEmpleado().getRut());
                asis.setHoraEntrada(asistencia.getHoraEntrada());
                asis.setHoraSalida(asistencia.getHoraSalida());
                asis.setTurno(asistencia.getEmpleado().getTurno().getTurno());
                registroAsistencia.add(asis);
            }
            return new ResponseEntity<>(registroAsistencia, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[ GET /AsistenciaManager/obtenerAsistencias ]: Ha ocurrido un error al procesar la solicitud", e);
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

}
