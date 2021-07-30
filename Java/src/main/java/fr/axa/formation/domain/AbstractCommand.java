package fr.axa.formation.domain;

public interface AbstractCommand<I, O> {

    O execute(I query);

}
