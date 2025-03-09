package ua.ithillel.javapro.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private Long id;
    private String fullName;
    private String email;
    private String socialSecurityNumber;
}
