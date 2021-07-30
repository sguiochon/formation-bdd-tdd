package fr.axa.formation.api.virements;

import fr.axa.formation.domain.virements.VirementCommandRequest;
import org.springframework.stereotype.Service;

@Service
public class VirementRequestMapper {

	public VirementCommandRequest toVirementCommandRequest(VirementRequest virementRequest) {
		return new VirementCommandRequest(virementRequest.getDepuisCompte(), virementRequest.getVersCompte(), virementRequest.getMontant());
	}

}
