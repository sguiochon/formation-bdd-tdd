package fr.axa.formation.repository.comptes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity(name = "operation")
public class OperationDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private final BigInteger id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_compte", nullable = false)
    private final CompteDao compte;

    @Column(name = "label", nullable = false)
    private final String label;

    @Column(name = "montant", nullable = false)
    private final BigInteger montant;

    @Column(name = "date", nullable = false)
    private final LocalDateTime date;

    protected OperationDao() {
        // no-args constructor required by JPA spec
        // this one is protected since it shouldn't be used directly
        this(null, null, null, null, null);
    }

    public OperationDao(final BigInteger id,
                        final CompteDao compte,
                        final String label,
                        final BigInteger montant,
                        final LocalDateTime date) {
        this.id = id;
        this.compte = compte;
        this.label = label;
        this.montant = montant;
        this.date = date;
    }

    public BigInteger getId() {
        return this.id;
    }

    public CompteDao getCompte() {
        return this.compte;
    }

    public String getLabel() {
        return this.label;
    }

    public BigInteger getMontant() {
        return this.montant;
    }

    public LocalDateTime getDate() {
        return this.date;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {

        private BigInteger id;
        private CompteDao compte;
        private String label;
        private BigInteger montant;
        private LocalDateTime date;

        public Builder() {
        }

        public Builder(final OperationDao operationDao) {
            this.id = operationDao.id;
            this.compte = operationDao.compte;
            this.label = operationDao.label;
            this.montant = operationDao.montant;
            this.date = operationDao.date;
        }

        public Builder withId(final BigInteger id) {
            this.id = id;
            return this;
        }

        public Builder withCompte(final CompteDao compte) {
            this.compte = compte;
            return this;
        }

        public Builder withLabel(final String label) {
            this.label = label;
            return this;
        }

        public Builder withMontant(final BigInteger montant) {
            this.montant = montant;
            return this;
        }

        public Builder withDate(final LocalDateTime date) {
            this.date = date;
            return this;
        }

        public OperationDao build() {
            return new OperationDao(
                    this.id,
                    this.compte,
                    this.label,
                    this.montant,
                    this.date
            );
        }
    }
}
