package com.dedsec.uranus.services.asistencia;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dedsec.uranus.interfaces.asistencia.TurnoRepository;
import com.dedsec.uranus.models.asistencia.Turno;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TurnoService {

    private final Logger logger = LoggerFactory.getLogger(TurnoService.class);
    private final TurnoRepository turnoRepository;

    public Turno obtenerTurno(String nombreTurno){
        try {
            logger.info("[ METHOD: obtenerTurno() ]: Obteniendo turno " + nombreTurno);
            Optional<Turno> getTurno = turnoRepository.findByTurno(nombreTurno);
            if(getTurno.isPresent()){
                logger.info("[ METHOD: obtenerTurno() ]: Turno " + nombreTurno + " obtenido con exito");
                Turno turno = getTurno.get();
                return turno;
            } else {
                logger.error("[ METHOD: obtenerTurno() ]: No existe el turno " + nombreTurno);
                return null;
            }
        } catch (Exception e) {
            logger.error("[ METHOD: obtenerTurno() ]: Ha ocurrido un error al obtener el turno: " + e.getMessage());
            return null;
        }
    }

}