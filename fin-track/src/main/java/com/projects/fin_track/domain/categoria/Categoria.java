package com.projects.fin_track.domain.categoria;

import com.projects.fin_track.domain.categoria.dto.Categorias;
import com.projects.fin_track.domain.transacao.Transacao;
import com.projects.fin_track.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.SoftDeleteType;

import java.util.List;

@Entity
@Table(name = "categoria")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@SoftDelete(columnName = "ativo", strategy = SoftDeleteType.ACTIVE)
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @OneToMany(mappedBy = "categoria")
    private List<Transacao> transacao;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
