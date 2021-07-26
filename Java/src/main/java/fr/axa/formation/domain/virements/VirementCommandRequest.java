package fr.axa.formation.domain.virements;

import java.math.BigDecimal;

public class VirementCommandRequest {

    private final String depuisCompte;
    private final String versCompte;
    private final BigDecimal montant;

    public VirementCommandRequest(final String depuisCompte,
                                  final String versCompte,
                                  final BigDecimal montant) {
        this.depuisCompte = depuisCompte;
        this.versCompte = versCompte;
        this.montant = montant;
    }

    public String getDepuisCompte() {
        return this.depuisCompte;
    }

    public String getVersCompte() {
        return this.versCompte;
    }

    public BigDecimal getMontant() {
        return this.montant;
    }
}
