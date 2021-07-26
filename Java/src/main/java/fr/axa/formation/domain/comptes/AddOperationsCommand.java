package fr.axa.formation.domain.comptes;

import fr.axa.formation.domain.AbstractCommand;
import fr.axa.formation.repository.comptes.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AddOperationsCommand extends AbstractCommand<List<Operation>, Boolean> {

    private final OperationRepository operationRepository;
    private final OperationMapper operationMapper;

    @Autowired
    public AddOperationsCommand(final OperationRepository operationRepository, final OperationMapper operationMapper) {
        this.operationRepository = operationRepository;
        this.operationMapper = operationMapper;
    }

    @Transactional
    @Override
    public Boolean execute(final List<Operation> operations) {
        for (Operation operation : operations) {
            operationRepository.save(operationMapper.toRepository(operation));
        }
        return true;
    }


}
