package com.dedsec.uranus.services.asistencia;

import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dedsec.uranus.interfaces.asistencia.AsistenciaRepository;
import com.dedsec.uranus.models.asistencia.Asistencia;
import com.dedsec.uranus.models.asistencia.Atrasos;
import com.dedsec.uranus.models.asistencia.Empleado;
import com.dedsec.uranus.models.asistencia.SalidaAnticipada;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AsistenciaService {

    private final Logger logger = LoggerFactory.getLogger(AsistenciaService.class);
    private final EmpleadosService empleadosService;
    private final AsistenciaRepository asistenciaRepository;
    private final AtrasosService atrasosService;
    private final SalidaAnticipadaService salidaAnticipadaService;
    private final NotificacionService notificacionService;

    public Boolean registarMarca(String rut, String tipoMarca) {
        try {
            logger.info("[ METHOD: registarMarca() ]: Registrando la marca " + tipoMarca);
            Empleado empleado = empleadosService.obtenerEmpleadoRut(rut);
            if (tipoMarca.equals("Entrada")) {
                logger.info("[ METHOD: registarMarca() ]: Procesando el objeto asistencia para el empleado " + empleado.getNombre());
                Asistencia asistencia = new Asistencia();
                asistencia.setFechaMarcaje(new Date());
                asistencia.setHoraEntrada(Time.valueOf(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")).substring(0, 8)));
                asistencia.setEmpleado(empleado);
                asistenciaRepository.save(asistencia);
                verificarAtraso(Time.valueOf(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")).substring(0, 8)), empleado);
                notificacionService.enviarNotificacion(empleado, tipoMarca);
                return true;
            } else if (tipoMarca.equals("Salida")) {
                logger.info("[ METHOD: registarMarca() ]: Obteniendo registro del usuario " + empleado.getNombre());
                List<Asistencia> getAsistencia = asistenciaRepository.findAllByEmpleado(empleado);
                if (!getAsistencia.isEmpty()) {
                    Asistencia asistencia = getAsistencia.get(0);
                    asistencia.setHoraSalida(Time.valueOf(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")).substring(0, 8)));
                    asistenciaRepository.save(asistencia);
                    verificarSalidaAnticipada(Time.valueOf(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")).substring(0, 8)), empleado);
                    notificacionService.enviarNotificacion(empleado, tipoMarca);
                    return true;
                } else {
                    logger.error("[ METHOD: registarMarca() ]: El usuario no ha registrado la entrada");
                    return false;
                }
            } else {
                logger.error("[ METHOD: registarMarca() ]: El tipo de marca solicitado no es válido");
                return false;
            }
        } catch (Exception e) {
            logger.error("[ METHOD: registarMarca() ]: Ha ocurrido un error al registrar la marca", e);
            return false;
        }
    }
    
    public void verificarAtraso(Time horaEntrada, Empleado empleado){
        try {
            logger.info("[ METHOD: verificarAtraso() ]: Verificando si el empleado esta atrasado");
            Time horaEntradaTurno = empleado.getTurno().getHoraTurnoEntrada();
            if (horaEntrada.after(horaEntradaTurno)) {
                logger.warn("[ METHOD: verificarAtraso() ]: El empleado está atrasado. Hora actual: " + horaEntrada + ", Hora de entrada: " + horaEntradaTurno);
                Atrasos atraso = new Atrasos();
                atraso.setFechaRegistro(new Date());
                atraso.setHoraEntrada(horaEntrada);
                atraso.setEmpleado(empleado);
                atrasosService.guardarAtraso(atraso);
            } else {
                logger.info("[ METHOD: verificarAtraso() ]: El empleado no está atrasado. Hora actual: " + horaEntrada + ", Hora de entrada: " + horaEntradaTurno);
            }
        } catch (Exception e) {
            logger.error("[ METHOD: verificarAtraso() ]: Ha ocurrido un error al realizar la verificacion", e);
        }
    }

    public void verificarSalidaAnticipada(Time horaSalida, Empleado empleado){
        try {
            logger.info("[ METHOD: verificarSalidaAnticipada() ]: Verificando si el empleado esta saliendo anticipadamente");
            Time horaSalidaTurno = empleado.getTurno().getHoraTurnoSalida();
            if(horaSalida.before(horaSalidaTurno)){
                logger.warn("[ METHOD: verificarSalidaAnticipada() ]: El empleado ha registrado la salida anticipada, Hora actual " + horaSalida + ", Salida turno: " + horaSalidaTurno);
                SalidaAnticipada salidaAnticipada = new SalidaAnticipada();
                salidaAnticipada.setFechaRegistro(new Date());
                salidaAnticipada.setHoraSalida(horaSalida);
                salidaAnticipada.setEmpleado(empleado);
                salidaAnticipadaService.guardarSalidaAnticipada(salidaAnticipada);
            } else {
                logger.info("[ METHOD: verificarSalidaAnticipada() ]: El empleado ha registrado la salida de manera normal");
            }
        } catch (Exception e) {
            logger.error("[ METHOD: verificarSalidaAnticipada() ]: Ha ocurrido un error al realizar la verificacion", e);
        }
    }

    public List<Asistencia> obtenerAsistencias(){
        try {
            logger.info("[ METHOD: obtenerAsistencias() ]: Obteniendo listado de asistencias");
            List<Asistencia> asistencias = asistenciaRepository.findAll();
            return asistencias;
        } catch (Exception e) {
            logger.error("[ METHOD: obtenerAsistencias() ]: Ha ocurrido un error al obtener las asistencias", e);
            return null;
        }
    }

    

}
