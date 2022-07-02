package pl.sda.arppl4.hibernaterental.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity


public class Car {

    @Id

    private Long id;

    private String name;
    private String brand;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private BodyType bodyType;
    private Double seats;
    @Enumerated(EnumType.STRING)
    private Transmission transmission;
    private Double capacity;




}
