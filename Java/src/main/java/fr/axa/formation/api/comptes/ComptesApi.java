package fr.axa.formation.api.comptes;

import fr.axa.formation.api.core.ResponseViewModel;
import fr.axa.formation.api.errors.NoDataFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ComptesApi {

    ResponseEntity<ResponseViewModel<List<CompteViewModel>>> getComptes();

    ResponseEntity<ResponseViewModel<CompteViewModel>> getCompte(String compteId) throws NoDataFoundException;

}
