package com.projects.fin_track.domain.conta;

import com.projects.fin_track.domain.conta.dto.TipoConta;
import com.projects.fin_track.domain.transacao.Transacao;
import com.projects.fin_track.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.SoftDeleteType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "conta")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@SoftDelete(columnName = "ativo", strategy = SoftDeleteType.ACTIVE)
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "tipo_conta", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoConta tipoConta;

    @Column(nullable = false)
    private BigDecimal saldo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, name = "criado_em")
    private LocalDate criadoEm;

    @OneToMany(mappedBy = "contaOrigem")
    private List<Transacao> transacaoSaida;

    @OneToMany(mappedBy = "contaDestino")
    private List<Transacao> transacaoEntrada;

}
