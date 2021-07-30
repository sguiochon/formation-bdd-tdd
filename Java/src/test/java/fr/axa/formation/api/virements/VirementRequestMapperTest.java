package fr.axa.formation.api.virements;

import fr.axa.formation.domain.virements.VirementCommandRequest;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class VirementRequestMapperTest {

	VirementRequestMapper virementRequestMapper;

	@BeforeEach
	public void setUp(){
		virementRequestMapper = new VirementRequestMapper();
	}

	@Test
	public void testMappingProducesValidDataWhenVirementRequestContainsNotNullProperties(){
		// Arrange
		String compteSourceId = "id compte source";
		String compteCibleId = "id compte cible";
		BigDecimal montant = BigDecimal.valueOf(10000, 2);
		VirementRequest virementRequest = new VirementRequest(compteSourceId, compteCibleId, montant);
		// Act
		VirementCommandRequest virementCommandRequest = virementRequestMapper.toVirementCommandRequest(virementRequest);
		// Assert
		assertNotNull(virementCommandRequest, "VirementCommandRequest is not null");
		assertEquals(compteSourceId, virementCommandRequest.getDepuisCompte(), "VirementCommandRequest, depuisCompte");
		assertEquals(compteCibleId, virementCommandRequest.getVersCompte(), "VirementCommandRequest, versCompte");
		assertEquals(montant, virementCommandRequest.getMontant(), "VirementCommandRequest, montant");
	}

}