package fr.axa.formation.domain.virements;

public enum VirementStatus {

	CONFIRME("Virement confirmé"),
	REJETE_SOLDE_INSUFFISANT("Le virement est rejeté car le solde du compte source est insuffisant"),
	REJETE_COMPTES_SOURCE_ET_CIBLE_IDENTIQUES("Le virement est rejeté car le compte source et le compte destinataire sont identiques"),
	REJETE_MONTANT_NEGATIF("Le virement est rejeté car le montant du transfert est négatif"),
	REJETE_COMPTE_SOURCE_INEXISTANT("Le virement est rejeté car le compte source est inexistant"),
	REJETE_COMPTE_CIBLE_INEXISTANT("Le virement est rejeté car le compte destinataire est inexistant"),
	REJETE_PLAFOND_VIREMENT_COMPTE_SOURCE_DEPASSE("Le virement est rejeté car le plafond de virement du compte source est dépassé");

	private final String label;

	VirementStatus(String label){
		this.label = label;
	}

	public String label(){
		return label;
	}

}
