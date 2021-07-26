package fr.axa.formation.repository.comptes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "compte")
public class CompteDao {

    @Id
    @Column(name = "numero", nullable = false)
    private final String numero;

    @Column(name = "label", nullable = false)
    private final String label;

    @Column(name = "plafond_compte")
    private final BigInteger plafondCompte;

    @Column(name = "plafond_virement")
    private final BigInteger plafondVirement;

    @OneToMany(mappedBy = "compte", fetch = FetchType.EAGER)
    private final Set<OperationDao> operations;

    protected CompteDao() {
        // no-args constructor required by JPA spec
        // this one is protected since it shouldn't be used directly
        this(null, null, null, null, null);
    }

    public CompteDao(final String numero,
                     final String label,
                     final BigInteger plafondCompte,
                     final BigInteger plafondVirement,
                     final Set<OperationDao> operations) {
        this.numero = numero;
        this.label = label;
        this.plafondCompte = plafondCompte;
        this.plafondVirement = plafondVirement;
        this.operations = operations;
    }

    public String getNumero() {
        return this.numero;
    }

    public String getLabel() {
        return this.label;
    }

    public BigInteger getPlafondCompte() {
        return this.plafondCompte;
    }

    public BigInteger getPlafondVirement() {
        return this.plafondVirement;
    }

    public Set<OperationDao> getOperations() {
        return this.operations != null ?
                new HashSet<>(this.operations) :
                new HashSet<>();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {

        private String numero;
        private String label;
        private BigInteger plafondCompte;
        private BigInteger plafondVirement;
        private Set<OperationDao> operations;

        public Builder() {
            this.operations = new HashSet<>();
        }

        public Builder(final CompteDao compteDao) {
            this.numero = compteDao.numero;
            this.label = compteDao.label;
            this.plafondCompte = compteDao.plafondCompte;
            this.plafondVirement = compteDao.plafondVirement;
            this.operations = compteDao.operations;
        }

        public Builder withNumero(final String numero) {
            this.numero = numero;
            return this;
        }

        public Builder withLabel(final String label) {
            this.label = label;
            return this;
        }

        public Builder withPlafondCompte(final BigInteger plafondCompte) {
            this.plafondCompte = plafondCompte;
            return this;
        }

        public Builder withPlafondVirement(final BigInteger plafondVirement) {
            this.plafondVirement = plafondVirement;
            return this;
        }

        public Builder addOperation(final OperationDao operation) {
            this.operations.add(operation);
            return this;
        }

        public Builder withOperations(final Set<OperationDao> operations) {
            this.operations = operations;
            return this;
        }

        public CompteDao build() {
            return new CompteDao(
                    this.numero,
                    this.label,
                    this.plafondCompte,
                    this.plafondVirement,
                    this.operations
            );
        }
    }
}
