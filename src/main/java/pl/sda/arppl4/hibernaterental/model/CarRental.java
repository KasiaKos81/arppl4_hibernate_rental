package pl.sda.arppl4.hibernaterental.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class CarRental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String clientName;
    private String clientLastName;
    private LocalDateTime rentDataTime;
    private LocalDateTime returnDateTime;

    // @OneToMany
    // @ManyToOne
    // @ManyToMany
    // @OneToOne

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Car car;

    public CarRental(String clientName, String clientLastName, LocalDateTime rentDataTime, Car car) {
        this.clientName = clientName;
        this.clientLastName = clientLastName;
        this.rentDataTime = rentDataTime;
        this.car = car;
    }
}
