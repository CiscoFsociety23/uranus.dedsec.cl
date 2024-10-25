package com.dedsec.uranus.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dedsec.uranus.models.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {

    Optional<Property> findByProp(String propName);

}