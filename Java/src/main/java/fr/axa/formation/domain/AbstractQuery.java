package fr.axa.formation.domain;

public interface AbstractQuery<I, O> {

    O execute(I query);

}
