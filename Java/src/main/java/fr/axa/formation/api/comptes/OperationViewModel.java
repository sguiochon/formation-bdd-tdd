package fr.axa.formation.api.comptes;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OperationViewModel implements Serializable {

    private final String id;
    private final String label;
    private final BigDecimal montant;
    private final LocalDateTime date;

    public OperationViewModel(final String id,
                              final String label,
                              final BigDecimal montant,
                              final LocalDateTime date) {
        this.id = id;
        this.label = label;
        this.montant = montant;
        this.date = date;
    }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public BigDecimal getMontant() {
        return this.montant;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    public LocalDateTime getDate() {
        return this.date;
    }
}
