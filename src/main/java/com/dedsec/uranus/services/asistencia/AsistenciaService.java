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
import com.dedsec.uranus.models.asistencia.Empleado;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AsistenciaService {

    private final Logger logger = LoggerFactory.getLogger(AsistenciaService.class);
    private final EmpleadosService empleadosService;
    private final AsistenciaRepository asistenciaRepository;

    public Boolean registarMarca(String rut, String tipoMarca) {
        try {
            logger.info("[ METHOD: registarMarca() ]: Registrando la marca " + tipoMarca);
            Empleado empleado = empleadosService.obtenerEmpleadoRut(rut);
            if (tipoMarca.equals("Entrada")) {
                logger.info("[ METHOD: registarMarca() ]: Procesando el objeto asistencia para el empleado " + empleado.getNombre());
                Asistencia asistencia = new Asistencia();
                asistencia.setFechaMarcaje(new Date());
                asistencia.setHoraEntrada(Time.valueOf(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.SSSSSS")).substring(0, 8)));
                asistencia.setEmpleado(empleado);
                asistenciaRepository.save(asistencia);
                return true;
            } else if (tipoMarca.equals("Salida")) {
                logger.info("[ METHOD: registarMarca() ]: Obteniendo registro del usuario " + empleado.getNombre());
                List<Asistencia> getAsistencia = asistenciaRepository.findAllByEmpleado(empleado);
                if (!getAsistencia.isEmpty()) {
                    Asistencia asistencia = getAsistencia.get(0);
                    asistencia.setHoraSalida(Time.valueOf(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.SSSSSS")).substring(0, 8)));
                    asistenciaRepository.save(asistencia);
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
    

}
