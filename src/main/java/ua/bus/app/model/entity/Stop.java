package ua.bus.app.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.security.Timestamp;



@Data
@ToString
@RequiredArgsConstructor
@Entity(name = "Stop")
@Table(name = "t_stop")
public class Stop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @Column(nullable = false)
    private String locationName;

    private Timestamp arrivalTime;

    private Timestamp departureTime;
}