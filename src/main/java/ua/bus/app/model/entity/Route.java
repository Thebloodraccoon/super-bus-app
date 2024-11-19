package ua.bus.app.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString(exclude = "stops")
@RequiredArgsConstructor
@Entity(name = "Route")
@Table(name = "t_route")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "startLocation", nullable = false)
    private String startLocation;

    @Column(name = "endLocation", nullable = false)
    private String endLocation;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price", nullable = false)
    private Long price;

    @ManyToOne
    @JoinColumn(name = "partner_id", nullable = false)
    private User partner;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stop> stops;
}
