package com.dedsec.uranus.models.certimanager;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "LlavePrivada")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LlavePrivada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdLlavePrivada")
    private Long idLlavePrivada;

    @Lob
    @Column(name = "Llave", nullable = false, columnDefinition = "TEXT")
    private String llave;
}
