package fr.axa.formation.api.comptes;

import fr.axa.formation.api.core.ResponseViewModel;
import fr.axa.formation.api.core.ViewModelFactory;
import fr.axa.formation.api.errors.NoDataFoundException;
import fr.axa.formation.domain.comptes.Compte;
import fr.axa.formation.domain.comptes.GetCompteQuery;
import fr.axa.formation.domain.comptes.GetComptesQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static fr.axa.formation.api.Url.BASE_URL;

@RestController
public class ComptesController implements ComptesApi {

    public static final String GET_COMPTES_URL = BASE_URL + "/comptes";

    private final GetComptesQuery getComptesQuery;
    private final GetCompteQuery getCompteQuery;
    private final ComptesViewModelMapper viewModelMapper;

    public ComptesController(final GetComptesQuery getComptesQuery,
                             final GetCompteQuery getCompteQuery,
                             final ComptesViewModelMapper viewModelMapper) {
        this.getComptesQuery = getComptesQuery;
        this.getCompteQuery = getCompteQuery;
        this.viewModelMapper = viewModelMapper;
    }

    @GetMapping(value = GET_COMPTES_URL, produces = "application/json; charset=utf-8")
    @Override
    public ResponseEntity<ResponseViewModel<List<CompteViewModel>>> getComptes() {
        final List<Compte> comptes = getComptesQuery.execute(null);
        return ViewModelFactory.success(viewModelMapper.toCompteViewModel(comptes), null);
    }

    @GetMapping(value = GET_COMPTES_URL + "/" + "{id}", produces = "application/json; charset=utf-8")
    @Override
    public ResponseEntity<ResponseViewModel<CompteViewModel>> getCompte(@PathVariable(name = "id") String compteId) throws NoDataFoundException {
        Optional<Compte> optionalCompte = getCompteQuery.execute(compteId);
        if (!optionalCompte.isPresent()){
            throw new NoDataFoundException("compte", compteId);
        }
        return ViewModelFactory.success(viewModelMapper.toCompteViewModel(optionalCompte.get()), null);
    }

}
