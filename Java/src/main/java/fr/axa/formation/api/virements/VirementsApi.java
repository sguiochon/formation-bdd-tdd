package fr.axa.formation.api.virements;

import fr.axa.formation.api.core.ResponseViewModel;
import org.springframework.http.ResponseEntity;

public interface VirementsApi {

    ResponseEntity<ResponseViewModel<Object>> createVirement(VirementRequest parameter);

}
