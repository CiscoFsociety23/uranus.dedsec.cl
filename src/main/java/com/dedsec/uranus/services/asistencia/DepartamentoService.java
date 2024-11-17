package com.dedsec.uranus.services.asistencia;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dedsec.uranus.interfaces.asistencia.DepartamentoRepository;
import com.dedsec.uranus.models.asistencia.Departamento;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartamentoService {

    private final Logger logger = LoggerFactory.getLogger(DepartamentoService.class);
    private final DepartamentoRepository departamentoRepository;

    public Departamento obtenerDepartamento(String nombreDepartamento){
        try {
            logger.info("[ METHOD: obtenerDepartamento() ]: Obteniendo departamento " + nombreDepartamento);
            Optional<Departamento> getDepartamento = departamentoRepository.findByNombreDepartamento(nombreDepartamento);
            if(getDepartamento.isPresent()){
                logger.info("[ METHOD: obtenerDepartamento() ]: Departamento " + nombreDepartamento + " encontrado con exito");
                Departamento departamento = getDepartamento.get();
                return departamento;
            } else {
                logger.error("[ METHOD: obtenerDepartamento() ]: El departamento " + nombreDepartamento + " no existe");
                return null;
            }
        } catch (Exception e) {
            logger.error("[ METHOD: obtenerDepartamento() ]: Ha ocurrido un error al obtener el departamento: " + e.getMessage());
            return null;
        }
    }

}
