package com.projects.fin_track.domain.transacao;

import com.projects.fin_track.domain.categoria.Categoria;
import com.projects.fin_track.domain.conta.Conta;
import com.projects.fin_track.domain.transacao.dto.TipoTransacao;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transacao")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private TipoTransacao tipo;

    @Column(nullable = false)
    private LocalDateTime data;

    @Column(nullable = false)
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "conta_origem_id")
    private Conta contaOrigem;

    @ManyToOne
    @JoinColumn(name = "conta_destino_id")
    private Conta contaDestino;

}
