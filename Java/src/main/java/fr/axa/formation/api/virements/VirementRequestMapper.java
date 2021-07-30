package fr.axa.formation.api.virements;

import fr.axa.formation.domain.virements.VirementCommandRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class VirementRequestMapper {

	public VirementCommandRequest toVirementCommandRequest(VirementRequest virementRequest){
		return new VirementCommandRequest("", "", BigDecimal.ZERO);
	}

}
