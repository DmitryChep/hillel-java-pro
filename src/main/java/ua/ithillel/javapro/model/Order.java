package ua.ithillel.javapro.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Order {
    private int id;
    private String date;
    private double cost;
    private List<Product> products;
}


