package io.flowtrack.accountmanager.entity;

import io.flowtrack.shared.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "account",
        indexes = {
                @Index(name = UniqueConstraints.ACCOUNT_EMAIL, columnList = Account_.EMAIL, unique = true)
        })
@SequenceGenerator(name = "id_sequence_generator", sequenceName = "account_seq", allocationSize = 1)
public class Account extends AbstractEntity {

    @Column(updatable = false, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;
}
