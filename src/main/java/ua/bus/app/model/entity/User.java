package ua.bus.app.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;


@Data
@ToString
@RequiredArgsConstructor
@Entity(name = "User")
@Table(name = "t_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    @Column(nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public enum Role {
        CLIENT, PARTNER;

        public static Role fromString(String role) {
            if (role != null) {
                for (Role r : Role.values()) {
                    if (r.name().equalsIgnoreCase(role)) {
                        return r;
                    }
                }
            }
            throw new IllegalArgumentException("Unexpected value: " + role);
        }

        // Convert enum to string
        public String toString() {
            return name();
        }
    }
}