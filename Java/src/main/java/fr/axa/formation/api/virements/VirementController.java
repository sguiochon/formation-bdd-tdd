package fr.axa.formation.api.virements;

import fr.axa.formation.api.core.ResponseViewModel;
import fr.axa.formation.api.core.ViewModelFactory;
import fr.axa.formation.domain.virements.VirementCommand;
import fr.axa.formation.domain.virements.VirementCommandRequest;
import fr.axa.formation.domain.virements.VirementStatus;
import org.hibernate.bytecode.spi.NotInstrumentedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static fr.axa.formation.api.Url.BASE_URL;

@RestController
public class VirementController implements VirementsApi {

	public static final String POST_VIREMENT_URL = BASE_URL + "/virements";

	private final VirementRequestMapper virementRequestMapper;

	private final VirementCommand virementCommand;

	@Autowired
	public VirementController(VirementRequestMapper virementRequestMapper, VirementCommand virementCommand) {
		this.virementRequestMapper = virementRequestMapper;
		this.virementCommand = virementCommand;
	}

	@PostMapping(POST_VIREMENT_URL)
	@Override
	public ResponseEntity<ResponseViewModel<Object>> createVirement(@RequestBody @Valid final VirementRequest virementRequest) {
		// Transformation de VirementRequest en VirementCommandRequest
		VirementCommandRequest virementCommandRequest = virementRequestMapper.toVirementCommandRequest(virementRequest);
		// Appel du service VirementCommand
		VirementStatus virementStatus = virementCommand.execute(virementCommandRequest);
		// Réponse:
		if (virementStatus == VirementStatus.CONFIRME) {
			return ViewModelFactory.success(virementStatus.label());
		} else{
			return ViewModelFactory.badRequest(virementStatus.label());
		}

	}
}
