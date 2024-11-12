package ua.bus.app.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
@RequiredArgsConstructor
@Entity(name = "Schedule")
@Table(name = "t_schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @Column(nullable = false)
    private Timestamp departureTime;

    @Column(nullable = false)
    private Timestamp arrivalTime;
}
