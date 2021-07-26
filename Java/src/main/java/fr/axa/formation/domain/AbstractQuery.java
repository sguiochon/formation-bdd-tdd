package fr.axa.formation.domain;

public abstract class AbstractQuery<Input, Output> {

    public abstract Output execute(Input query);

}
