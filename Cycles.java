package com.example.learningcycles.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="Cycles")
public class Cycles {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private int id;
  @Column
  private String name;
  @Column
  private boolean taken;
  
  @Column(name = "stock")
  private int stock;

  public int getStock() {
      return stock;
  }

  public void setStock(int stock) {
      this.stock = stock;
  }
  
}
