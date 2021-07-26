package fr.axa.formation.domain.comptes;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Compte {

    private final String id;
    private final String label;
    private final Set<Operation> operations;
    private final BigDecimal plafondVirement;
    private final BigDecimal plafondCompte;

    // Lazy loaded
    private BigDecimal montant;

    public Compte(final String id,
                  final String label,
                  final Set<Operation> operations,
                  final BigDecimal plafondVirement,
                  final BigDecimal plafondCompte) {
        this.id = id;
        this.label = label;
        this.operations = operations;
        this.plafondVirement = plafondVirement;
        this.plafondCompte = plafondCompte;
    }

    private Compte(final String id,
                   final String label,
                   final Set<Operation> operations,
                   final BigDecimal plafondVirement,
                   final BigDecimal plafondCompte,
                   final BigDecimal montant) {
        this.id = id;
        this.label = label;
        this.operations = operations;
        this.plafondVirement = plafondVirement;
        this.plafondCompte = plafondCompte;
        this.montant = montant;
    }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public Set<Operation> getOperations() {
        return this.operations != null ?
                new HashSet<>(this.operations) :
                new HashSet<>();
    }

    public BigDecimal getMontant() {
        if (this.montant != null) {
            return this.montant;
        }

        if (this.operations == null) {
            return BigDecimal.ZERO;
        }

        this.montant = operations
                .stream()
                .map(Operation::getMontant)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return this.montant;
    }

    public BigDecimal getPlafondVirement() {
        return this.plafondVirement;
    }

    public BigDecimal getPlafondCompte() {
        return this.plafondCompte;
    }

    public boolean hasPlafondVirement() {
        return this.plafondVirement != null;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Compte compte = (Compte) o;
        return Objects.equals(id, compte.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {

        private String id;
        private String label;
        private Set<Operation> operations;
        private BigDecimal plafondVirement;
        private BigDecimal plafondCompte;
        private BigDecimal montant;

        public Builder() {
            this.operations = new HashSet<>();
        }

        public Builder(Compte compte) {
            this.id = compte.id;
            this.label = compte.label;
            this.operations = compte.operations;
            this.plafondVirement = compte.plafondVirement;
            this.plafondCompte = compte.plafondCompte;
            this.montant = compte.montant;
        }

        public Builder withId(final String id) {
            this.id = id;
            return this;
        }

        public Builder withLabel(final String label) {
            this.label = label;
            return this;
        }

        public Builder addOperation(final Operation operation) {
            this.operations.add(operation);
            return this;
        }

        public Builder withOperations(final Set<Operation> operations) {
            this.operations = operations;
            return this;
        }

        public Builder withPlafondVirement(final BigDecimal plafondVirement) {
            this.plafondVirement = plafondVirement;
            return this;
        }

        public Builder withPlafondCompte(final BigDecimal plafondCompte) {
            this.plafondCompte = plafondCompte;
            return this;
        }

        /**
         * Force un montant, il ne sera pas calculé à partir de la liste des opérations
         */
        public Builder withMontant(final BigDecimal montant) {
            this.montant = montant;
            return this;
        }

        public Compte build() {
            return new Compte(
                    this.id,
                    this.label,
                    this.operations,
                    this.plafondVirement,
                    this.plafondCompte,
                    this.montant
            );
        }
    }
}
