package fr.axa.formation.api.virements;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class VirementRequest {

    private final String depuisCompte;
    private final String versCompte;
    private final BigDecimal montant;

    @JsonCreator
    public VirementRequest(@JsonProperty("depuisCompte") final String depuisCompte,
                           @JsonProperty("versCompte") final String versCompte,
                           @JsonProperty("montant") final BigDecimal montant) {
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
