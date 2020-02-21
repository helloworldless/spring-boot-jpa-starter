package com.davidagood.springbootjpa.repository;

import com.davidagood.springbootjpa.entity.Relation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelationRepository extends JpaRepository<Relation, Integer> {
}

