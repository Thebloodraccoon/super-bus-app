package ua.bus.app.model.entity.enums;

import lombok.RequiredArgsConstructor;
import lombok.ToString;


@RequiredArgsConstructor
@ToString
public enum Role {
    CLIENT("Client"),
    PARTNER("Partner");

    private final String displayName;

    public String toString() {
        return name();
    }

    public static Role fromString(String roleName) {
        for (Role role : Role.values()) {
            if (role.displayName.equalsIgnoreCase(roleName)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unexpected value: " + roleName);
    }
}