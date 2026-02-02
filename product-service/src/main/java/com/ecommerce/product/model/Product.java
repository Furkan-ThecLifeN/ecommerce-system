package com.ecommerce.product.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable; // BU ÖNEMLİ
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "products")
@Data
@Builder
@NoArgsConstructor // Redis ve JPA için şart
@AllArgsConstructor
public class Product implements Serializable { // BU ÖNEMLİ
    
    // SerialVersionUID eklemek versiyon uyumsuzluklarını önler
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private BigDecimal price;
    private Integer stock;
}