package fr.axa.formation;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

public class VirementStepdefs {

	public VirementStepdefs() {
		MockitoAnnotations.initMocks(this);
	}

	@Given("j'ai {bigdecimal}€ sur un {string} numéro {string}")
	public void j_ai_Xeuros_sur_un_numero(BigDecimal montant, String libelleCompte, String compteId) {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}

	@When("je vire {bigdecimal}€ de mon compte {string} vers mon compte {string}")
	public void je_vire_Xeuros_de_mon_compte_vers_mon_compte(BigDecimal montant, String compteSourceId, String compteCibleId) {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}

	@Then("le statut du virement est {string}")
	public void le_statut_du_virement_est(String message) {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}

	@Then("j'ai maintenant {double}€ sur mon compte numéro {string}")
	public void j_ai_maintenant_Xeuros_sur_mon_compte_numero(BigDecimal montant, String compteId) {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}

	@Then("une opération de débit de {double}€ est créée sur le compte numéro {string}")
	public void une_operation_de_debit_de_Xeuros_est_creee_sur_le_compte_numero(BigDecimal montant, String compteId) {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}

	@Then("une opération de crédit de {double}€ est créée sur le compte {string}")
	public void une_operation_de_credit_de_Xeuros_est_creee_sur_le_compte(BigDecimal montant, String compteId) {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}

	@Given("je n'ai pas de compte numéro {string}")
	public void je_n_ai_pas_de_compte_numero(String compteId) {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}

	@Then("le virement échoue et son statut est {string}")
	public void le_virement_echoue_et_son_statut_est(String message) {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}

	@Then("aucune opération n'est créée sur le compte numéro {string}")
	public void aucune_operation_n_est_creee_sur_le_compte_numero(String compteId) {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}

	@Given("mes virements sont plafonnés à {bigdecimal}€")
	public void mes_virements_sont_plafonnes_a_Xeuros(BigDecimal montantPlafond) {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}

}
