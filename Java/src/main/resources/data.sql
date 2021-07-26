INSERT INTO compte (numero, label, plafond_virement)
VALUES ('0000145577', 'Compte de chèque', 50000);

INSERT INTO compte (numero, label, plafond_compte)
VALUES ('0000770286', N'Livret A', 2295000);

INSERT INTO compte (numero, label, plafond_compte)
VALUES ('0000933153', N'Livret de développement durable et solidaire (LDDS)', 1200000);

INSERT INTO operation (id_compte, label, montant, date)
VALUES ('0000145577', N'Ouverture compte', 90000, {ts '2020-02-17 15:47:52.69'});

INSERT INTO operation (id_compte, label, montant, date)
VALUES ('0000770286', N'Ouverture compte', 1255631, {ts '2019-12-17 15:47:52.69'});

INSERT INTO operation (id_compte, label, montant, date)
VALUES ('0000933153', N'Ouverture compte', 1200000, {ts '2020-02-17 15:47:52.69'});
