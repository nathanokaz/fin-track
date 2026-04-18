package com.projects.fin_track.domain.transacao.dto;

import java.math.BigDecimal;

public record ResumoTransacoes(
        BigDecimal valorTotalTransferencias,
        BigDecimal valorTotalSaques,
        BigDecimal valorTotalDepositos
) {
}
