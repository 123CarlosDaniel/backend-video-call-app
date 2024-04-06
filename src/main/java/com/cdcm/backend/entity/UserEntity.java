package com.cdcm.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "user_tbl")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true)
    private String username;

    @JsonIgnore
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_contact",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "contact_id", referencedColumnName = "id")
    )
    @Builder.Default
    @ToString.Exclude
    private Set<UserEntity> contacts = new HashSet<>();

    @Builder.Default
    private long lastConnection = System.currentTimeMillis();

    @Builder.Default
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RoomEntity> rooms = new ArrayList<>();

    @Builder.Default
    private boolean isConnected = true;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private List<RecordingEntity> recordings = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity)) return false;
        UserEntity that = (UserEntity) o;
        return (getId() != null && getId().equals(that.getId()));
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
