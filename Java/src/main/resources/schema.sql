CREATE TABLE compte
(
    numero nvarchar not null PRIMARY KEY,
    label nvarchar(250) not null,
    plafond_compte bigint,
    plafond_virement bigint
);

CREATE TABLE operation
(
    id bigint auto_increment PRIMARY KEY,
    id_compte nvarchar not null,
    label nvarchar(250) not null,
    montant bigint not null,
    date timestamp default CURRENT_TIMESTAMP,
    foreign key (id_compte) references compte(numero)
);
