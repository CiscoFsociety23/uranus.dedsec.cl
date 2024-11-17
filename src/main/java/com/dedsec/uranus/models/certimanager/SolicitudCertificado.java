package com.dedsec.uranus.models.certimanager;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "SolicitudCertificado")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudCertificado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdCSR")
    private Long idCSR;

    @Column(name = "NombreCompleto", nullable = false)
    private String nombreCompleto;

    @Column(name = "Correo", nullable = false)
    private String correo;

    @Lob
    @Column(name = "CSR", nullable = false, columnDefinition = "TEXT")
    private String csr;

    @ManyToOne
    @JoinColumn(name = "IdLlavePrivada", nullable = false)
    private LlavePrivada llavePrivada;
}
