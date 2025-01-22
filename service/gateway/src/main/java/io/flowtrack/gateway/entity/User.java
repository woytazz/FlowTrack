package io.flowtrack.gateway.entity;

import io.flowtrack.shared.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "user",
        indexes = {
                @Index(name = UniqueConstraints.USER_USERNAME, columnList = User_.USERNAME, unique = true)
        })
@SequenceGenerator(name = "id_sequence_generator", sequenceName = "user_seq", allocationSize = 1)
public class User extends AbstractEntity {

    @Column(updatable = false, nullable = false)
    private String username;

    @Column(nullable = false)
    private String encryptedPassword;

    @Builder.Default
    @ManyToMany
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "user_x_role",
            joinColumns
                    = @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false,
                    foreignKey = @ForeignKey(name = "fk_user_id")),
            inverseJoinColumns
                    = @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false,
                    foreignKey = @ForeignKey(name = "fk_role_id"))
    )
    private Set<Role> roles = new HashSet<>();

}
