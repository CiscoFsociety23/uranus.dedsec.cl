package com.dedsec.uranus.interfaces.certimanager;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dedsec.uranus.models.certimanager.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {

    Optional<Property> findByProp(String propName);

}