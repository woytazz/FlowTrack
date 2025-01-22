package io.flowtrack.gateway.entity;

import io.flowtrack.shared.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "role",
        indexes = {
                @Index(name = UniqueConstraints.ROLE_VALUE, columnList = Role_.VALUE, unique = true)
        })
@SequenceGenerator(name = "id_sequence_generator", sequenceName = "role_seq", allocationSize = 1)
public class Role extends AbstractEntity {

    private String value;

    @Builder.Default
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

}
