package ru.t1.java.demo.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;
import ru.t1.java.demo.enums.AccountType;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class Account extends AbstractPersistable<Long> {
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Client client;
}
