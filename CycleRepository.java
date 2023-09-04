package com.example.learningcycles.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import com.example.learningcycles.entity.Cycles;

public interface CycleRepository extends CrudRepository<Cycles, Integer> {

    // @Transactional
    // @Modifying
    // @Query("UPDATE Cycles c SET c.taken = true, c.stock = c.stock - 1 WHERE c.id = :id AND c.stock > 0")
    // void borrowCycle(@Param("id") Integer id);

    // @Transactional
    // @Modifying
    // @Query("UPDATE Cycles c SET c.taken = false WHERE c.id = :id")
    // void returnCycle(@Param("id") Integer id);

    // @Transactional
    // @Modifying
    // @Query("UPDATE Cycles c SET c.stock = c.stock + :qty WHERE c.id = :id")
    // void restockCycle(@Param("id") Integer id, @Param("qty") Integer qty);

    @Transactional
    @Modifying
    @Query(value = "select * from Cycles where count > ?1")
    List<Cycles> findAllByStockGreaterThan(int i);
}
