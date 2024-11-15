package ua.bus.app.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@ToString
@Entity(name = "Ticket")
@Table(name = "t_ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime purchaseDate;

    @Column(length = 50, columnDefinition = "VARCHAR(50) DEFAULT 'куплений'")
    private String status = "куплений";

    @Column(nullable = false)
    private Integer seats;

    @Column(nullable = false)
    private Integer durationHours;
}
