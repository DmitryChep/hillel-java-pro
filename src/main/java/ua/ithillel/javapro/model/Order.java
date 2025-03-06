package ua.ithillel.javapro.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double totalCost;
    private LocalDateTime createdAt;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Product> products;

    @PrePersist
    @PreUpdate
    public void calculateTotalCost() {
        if (products != null) {
            totalCost = products.stream()
                    .mapToDouble(Product::getPrice)
                    .sum();
        } else {
            totalCost = 0;
        }
    }
}

