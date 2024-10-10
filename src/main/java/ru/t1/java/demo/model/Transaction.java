package ru.t1.java.demo.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactions")
public class Transaction extends AbstractPersistable<Long> {
    @ManyToOne
    @JoinColumn(nullable = false)
    private Account account;

    @Column(name = "amount", precision = 19, scale = 2)
    private BigDecimal amount;

}
