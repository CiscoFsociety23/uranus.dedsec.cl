package com.dedsec.uranus.services.asistencia;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dedsec.uranus.interfaces.asistencia.NotificacionRepository;
import com.dedsec.uranus.models.asistencia.Empleado;
import com.dedsec.uranus.models.asistencia.Notificacion;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificacionService {

    private final Logger logger = LoggerFactory.getLogger(NotificacionService.class);
    private final NotificacionRepository notificacionRepository;
    private static final String URL = "http://dedsec.cl:8843/EmailDedsec/sendRegistroAsistencia";

    public void enviarNotificacion(Empleado empleado, String tipoMarca) {
        try {
            logger.info("[ METHOD: enviarNotificacion() ]: Procesando envío de notificación");
            String fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
            String body = """
                {
                    "reciever": "%s",
                    "subject": "Registro de asistencia",
                    "tipoRegistro": "%s",
                    "nombre": "%s",
                    "rut": "%s",
                    "fechaHora": "%s"
                }
            """.formatted(empleado.getCorreo(), tipoMarca, empleado.getNombre(), empleado.getRut(), fechaHora);
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                logger.info("[ METHOD: enviarNotificacion() ]: Notificación enviada exitosamente");
                Notificacion noti = new Notificacion();
                noti.setEmpleado(empleado);
                noti.setNotificacion(tipoMarca);
                notificacionRepository.save(noti);
                logger.info("[ METHOD: enviarNotificacion() ]: Notificacion guardada");
            } else {
                logger.warn("[ METHOD: enviarNotificacion() ]: Falló el envío. Código de estado: {}", response.statusCode());
            }
        } catch (Exception e) {
            logger.error("[ METHOD: enviarNotificacion() ]: Ha ocurrido un error al procesar el envío", e);
        }
    }
}
