package com.dedsec.uranus.interfaces.certimanager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dedsec.uranus.models.certimanager.Certificado;

@Repository
public interface CertificadoRepository extends JpaRepository<Certificado, Long> {

}
