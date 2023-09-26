package com.grae.boxobbackend.repo;

import com.grae.boxobbackend.entity.ActorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorRepo extends JpaRepository<ActorEntity, Integer> {

}
