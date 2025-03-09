package ua.ithillel.javapro.order;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Order {
    private  int orderNumber;
    private  String customerName;
}
