package fr.axa.formation.repository.comptes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompteRepository extends JpaRepository<CompteDao, String> {

    @Override
    List<CompteDao> findAll();

    Optional<CompteDao> findById(String numero);

}
