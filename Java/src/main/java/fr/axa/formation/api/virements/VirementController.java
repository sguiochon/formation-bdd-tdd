package fr.axa.formation.api.virements;

import fr.axa.formation.api.core.ResponseViewModel;
import org.hibernate.bytecode.spi.NotInstrumentedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static fr.axa.formation.api.Url.BASE_URL;

@RestController
public class VirementController implements VirementsApi {

	public static final String POST_VIREMENT_URL = BASE_URL + "/virements";

	public VirementController() {
		// Ce constructeur sera enrichi ultérieurement...
	}

	@PostMapping(POST_VIREMENT_URL)
	@Override
	public ResponseEntity<ResponseViewModel<Object>> createVirement(@RequestBody @Valid final VirementRequest virementRequest) {
		throw new NotInstrumentedException("je ne sais pas quoi faire!");
	}
}
