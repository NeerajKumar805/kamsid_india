package com.india.kamsid.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Using AUTO_INCREMENT for MySQL
    private Long id;

    private String name;
    private String des;
    
    @Column(name = "category_id")
    private Long categoryId;  // Foreign key reference to Category
    
    private String image;
}
