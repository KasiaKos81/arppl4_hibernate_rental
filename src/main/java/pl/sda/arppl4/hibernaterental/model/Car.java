package pl.sda.arppl4.hibernaterental.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity


public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

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

    @OneToMany(mappedBy = "car", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude

    private Set<CarRental> carRentals;

    public Car(String name, String brand, LocalDate date, BodyType bodyType, Double seats, Transmission transmission, Double capacity) {
        this.name = name;
        this.brand = brand;
        this.date = date;
        this.bodyType = bodyType;
        this.seats = seats;
        this.transmission = transmission;
        this.capacity = capacity;
    }
}
