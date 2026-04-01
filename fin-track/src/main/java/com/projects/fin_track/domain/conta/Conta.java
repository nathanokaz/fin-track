package com.projects.fin_track.domain.conta;

import com.projects.fin_track.domain.conta.dto.TipoConta;
import com.projects.fin_track.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "conta")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tipo_conta", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoConta tipoConta;

    @Column(nullable = false)
    private BigDecimal saldo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
