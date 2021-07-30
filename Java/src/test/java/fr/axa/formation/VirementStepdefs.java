package fr.axa.formation;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.axa.formation.api.core.ControllerExceptionHandler;
import fr.axa.formation.api.virements.VirementController;
import fr.axa.formation.api.virements.VirementRequest;
import fr.axa.formation.api.virements.VirementRequestMapper;
import fr.axa.formation.core.CurrencyUtils;
import fr.axa.formation.domain.comptes.Compte;
import fr.axa.formation.domain.comptes.GetCompteQuery;
import fr.axa.formation.domain.virements.VirementCommand;
import fr.axa.formation.repository.comptes.CompteDao;
import fr.axa.formation.repository.comptes.CompteRepository;
import fr.axa.formation.repository.comptes.OperationDao;
import fr.axa.formation.repository.comptes.OperationRepository;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VirementStepdefs {

	private static final ObjectMapper OBJECT_MAPPER = Jackson2ObjectMapperBuilder.json().build();

	@Autowired
	GetCompteQuery getCompteQuery;

	@Autowired
	CompteRepository compteRepository;

	@Autowired
	OperationRepository operationRepository;

	private BigInteger plafondVirement;

	private ResultActions performResult;

	@Autowired
	private VirementRequestMapper virementRequestMapper;

	@Autowired
	private VirementCommand virementCommand;

	public VirementStepdefs() {
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * L'annotation @After de cucumber permet d'executer des actions apres le dernier step de chaque Rule/Scenario
	 */
	@After
	public void tearDown() {
		operationRepository.deleteAll();
		compteRepository.deleteAll();
	}

	@Before
	public void setUp() {
		plafondVirement = null;
	}

	@Given("j'ai {bigdecimal}€ sur un {string} numéro {string}")
	public void j_ai_Xeuros_sur_un_numero(BigDecimal montant, String libelleCompte, String compteId) {
		// Création du compte:
		CompteDao compteDao = new CompteDao.Builder().withNumero(compteId).withLabel(libelleCompte).withPlafondVirement(plafondVirement).build();
		compteDao = compteRepository.save(compteDao);
		// Création de l'opération de versement initial:
		OperationDao operationDao = new OperationDao.Builder().withMontant(CurrencyUtils.toCents(montant)).withLabel("Versement initial").withCompte(compteDao).build();
		operationRepository.save(operationDao);

		// Assert
		Assert.assertNotNull("Compte créé", compteDao);
		Assert.assertEquals("Libelle du compte", libelleCompte, compteDao.getLabel());
	}

	@When("je vire {bigdecimal}€ de mon compte {string} vers mon compte {string}")
	public void je_vire_Xeuros_de_mon_compte_vers_mon_compte(BigDecimal montant, String compteSourceId, String compteCibleId) throws Exception {
		final VirementRequest request = new VirementRequest(compteSourceId, compteCibleId, montant);
		VirementController controller = new VirementController(virementRequestMapper, virementCommand);
		MockMvc mvc = MockMvcBuilders.standaloneSetup(controller)
				.setControllerAdvice(new ControllerExceptionHandler())
				.build();
		performResult = mvc.perform(post(VirementController.POST_VIREMENT_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(OBJECT_MAPPER.writeValueAsString(request)));
	}

	@Then("le statut du virement est {string}")
	public void le_statut_du_virement_est(String message) throws Exception {
		performResult.andExpect(status().is2xxSuccessful())
				.andExpect(jsonPath("$.message").value(message));
	}

	@Then("j'ai maintenant {bigdecimal}€ sur mon compte numéro {string}")
	public void j_ai_maintenant_Xeuros_sur_mon_compte_numero(BigDecimal montant, String compteId) {
		Optional<Compte> optionalCompte = getCompteQuery.execute(compteId);
		Assert.assertTrue("La vérification porte sur un compte préexistant", optionalCompte.isPresent());
		Assert.assertEquals("Le compte a bien maintenant le solde attendu", montant, optionalCompte.get().getMontant());
	}

	@Then("une opération de débit de {bigdecimal}€ est créée sur le compte numéro {string}")
	public void une_operation_de_debit_de_Xeuros_est_creee_sur_le_compte_numero(BigDecimal montantDebit, String compteId) {
		Optional<CompteDao> optionalCompteDao = compteRepository.findById(compteId);
		Assert.assertTrue("Le compte existe", optionalCompteDao.isPresent());

		Set<OperationDao> operations = optionalCompteDao.get().getOperations();
		OperationDao lastOperationDao = operations.stream().sorted(Comparator.comparing(OperationDao::getId).reversed()).findFirst().orElse(null);
		Assert.assertNotNull("Le compte comporte bien au moins une opération", lastOperationDao);
		Assert.assertEquals("La dernière opération du compte a le montant attendu", montantDebit.multiply(BigDecimal.valueOf(-1)), CurrencyUtils.toUnit(lastOperationDao.getMontant()));
	}

	@Then("une opération de crédit de {bigdecimal}€ est créée sur le compte {string}")
	public void une_operation_de_credit_de_Xeuros_est_creee_sur_le_compte(BigDecimal montantCredit, String compteId) {
		Optional<CompteDao> optionalCompteDao = compteRepository.findById(compteId);
		Assert.assertTrue("La vérification porte sur un compte préexistant", optionalCompteDao.isPresent());

		Set<OperationDao> operations = optionalCompteDao.get().getOperations();
		OperationDao lastOperationDao = operations.stream().sorted(Comparator.comparing(OperationDao::getId).reversed()).findFirst().orElse(null);
		Assert.assertNotNull("Le compte vérifié comporte bien au moins une opération", lastOperationDao);
		Assert.assertEquals("La dernière opération du compte vérifié a le montant attendu", montantCredit, CurrencyUtils.toUnit(lastOperationDao.getMontant()));

	}

	@Given("je n'ai pas de compte numéro {string}")
	public void je_n_ai_pas_de_compte_numero(String compteId) {
		// Je ne fais rien en fait...
	}

	@Then("le virement échoue et son statut est {string}")
	public void le_virement_echoue_et_son_statut_est(String messageRejet) throws Exception {
		performResult.andExpect(status().is4xxClientError()).andExpect(jsonPath("$.message").value(messageRejet));
	}

	@Then("aucune opération n'est créée sur le compte numéro {string}")
	public void aucune_operation_n_est_creee_sur_le_compte_numero(String compteId) {
		// Le test consiste à vérifier qu'il n'y a QU'UNE opération correspondant au versement initial:
		Optional<CompteDao> optionalCompteDao = compteRepository.findById(compteId);
		Assert.assertTrue("La vérification porte sur un compte préexistant", optionalCompteDao.isPresent());

		Set<OperationDao> operations = optionalCompteDao.get().getOperations();
		Assert.assertEquals("Le compte n'a pas d'autre opération que celle due à sa création", 1, operations.size());
	}

	@Given("mes virements sont plafonnés à {bigdecimal}€")
	public void mes_virements_sont_plafonnes_a_Xeuros(BigDecimal montantPlafond) {
		this.plafondVirement = montantPlafond.toBigInteger().multiply(BigInteger.valueOf(100));
	}

}
