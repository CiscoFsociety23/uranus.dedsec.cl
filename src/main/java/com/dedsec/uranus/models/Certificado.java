package com.dedsec.uranus.models;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Certificado")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Certificado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdCertificado")
    private Long idCertificado;

    @Lob
    @Column(name = "Certificado_x509", nullable = false)
    private String certificadoX509;

    @ManyToOne
    @JoinColumn(name = "IdLlavePrivada", nullable = false)
    private LlavePrivada llavePrivada;

    @Column(name = "CorreoUsuario", nullable = false)
    private String correoUsuario;
}
