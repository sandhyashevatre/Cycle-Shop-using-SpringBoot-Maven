package com.example.learningcycles.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.learningcycles.entity.Cycles;

public interface CycleRepository extends CrudRepository<Cycles, Integer> {

   
}
