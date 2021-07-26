package fr.axa.formation.api.comptes;

import fr.axa.formation.domain.comptes.Compte;
import fr.axa.formation.domain.comptes.Operation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ComptesViewModelMapper {

    public List<CompteViewModel> toCompteViewModel(final List<Compte> from) {
        if (from == null) {
            return new ArrayList<>();
        }

        return from
                .stream()
                .map(this::toCompteViewModel)
                .collect(Collectors.toList());
    }

    public CompteViewModel toCompteViewModel(final Compte from) {
        return new CompteViewModel(
                from.getId(),
                from.getLabel(),
                from.getMontant(),
                toOperationViewModel(from.getOperations()));
    }

    public List<OperationViewModel> toOperationViewModel(final Set<Operation> from) {
        if (from == null) {
            return new ArrayList<>();
        }

        return from
                .stream()
                .map(this::toOperationViewModel)
                .collect(Collectors.toList());
    }

    public OperationViewModel toOperationViewModel(final Operation from) {
        return new OperationViewModel(
                from.getId(),
                from.getLabel(),
                from.getMontant(),
                from.getDate()
        );
    }
}
