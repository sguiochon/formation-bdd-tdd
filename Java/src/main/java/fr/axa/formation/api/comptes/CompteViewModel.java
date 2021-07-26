package fr.axa.formation.api.comptes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class CompteViewModel implements Serializable {

    private final String id;
    private final String label;
    private final BigDecimal montant;
    private final List<OperationViewModel> operations;

    public CompteViewModel(final String id,
                           final String label,
                           final BigDecimal montant,
                           final List<OperationViewModel> operations) {
        this.id = id;
        this.label = label;
        this.montant = montant;
        this.operations = operations;
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

    public List<OperationViewModel> getOperations() {
        return this.operations;
    }
}
