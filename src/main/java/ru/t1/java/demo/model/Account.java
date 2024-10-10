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

    @Column(name= "balance", precision = 19, scale = 2)
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Client client;
}
