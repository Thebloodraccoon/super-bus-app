package ua.bus.app.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;


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

    @Column(nullable = false)
    private LocalDateTime purchaseDate;

    @Column(nullable = false)
    private String routeName;

    @Column(length = 50, columnDefinition = "VARCHAR(50) DEFAULT 'куплений'")
    private String status = "куплений";

    @Column(nullable = false)
    private Integer seats;

    @Column(nullable = false)
    private Double price;


}
