Feature: Virement interne
  Dans le but de pouvoir gérer mes comptes
  En tant que client banque
  Je souhaite pouvoir effectuer des virements entre mes comptes

  RG1: virement simple, je vire X€ d'un compte A vers un compte B, le solde est impacté dans les deux comptes
  RG2: virement hors provision, le solde du compte source est insuffisant
  RG3: virement plafonné, le montant du virement est supérieur au plafond du compte source
  RG4: le montant est négatif
  RG5: le compte source n'existe pas
  RG6: le compte destination n'existe pas
  RG7: le compte source et le compte destination sont identiques
  RG8: une opération est écrite quand le virement est confirmé
  RG9: aucune une opération n'est écrite quand le virement n'est pas confirmé

  Rule: RG1, RG8 - virement simple entre deux comptes
    Example: compte courant vers livret épargne
      Given j'ai 5000.00€ sur un 'Compte courant' numéro '00001'
      And j'ai 130.00€ sur un 'Livret épargne' numéro "00002"
      When je vire 500.00€ de mon compte "00001" vers mon compte "00002"
      Then le statut du virement est "Virement confirmé"
      And j'ai maintenant 4500.00€ sur mon compte numéro "00001"
      And j'ai maintenant 630.00€ sur mon compte numéro "00002"
      And une opération de débit de 500.00€ est créée sur le compte numéro "00001"
      And une opération de crédit de 500.00€ est créée sur le compte "00002"

  Rule: RG2, RG9 - virement hors provision
    Example: montant du transfert largement supérieur au solde du compte source
      Given j'ai 300.00€ sur un "Compte courant" numéro '00001'
      And j'ai 200.00€ sur un 'Livret épargne' numéro '00002'
      When je vire 500.00€ de mon compte '00001' vers mon compte '00002'
      Then le virement échoue et son statut est "Le virement est rejeté car le solde du compte source est insuffisant"
      And j'ai maintenant 300.00€ sur mon compte numéro '00001'
      And j'ai maintenant 200.00€ sur mon compte numéro '00002'
      And aucune opération n'est créée sur le compte numéro '00001'
      And aucune opération n'est créée sur le compte numéro '00002'

    @ignore
    Rule: RG3, RG9 - virement plafonné
    Example: plafond de 500€
      Given mes virements sont plafonnés à 500.00€
      And j'ai 10000.00€ sur un "compte courant" numéro "00001"
      And j'ai 0.00€ sur un "Compte épargne" numéro "00002"
      When je vire 500.01€ de mon compte "00001" vers mon compte "00002"
      Then le virement échoue et son statut est "Le virement est rejeté car le plafond de virement du compte source est dépassé"
      And j'ai maintenant 10000.00€ sur mon compte numéro "00001"
      And j'ai maintenant 0.00€ sur mon compte numéro "00002"
      And aucune opération n'est créée sur le compte numéro '00001'
      And aucune opération n'est créée sur le compte numéro '00002'

    @ignore
    Rule: RG4, RG9 - virement d'un montant négatif rejeté
    Example: virement de -500€
      Given j'ai 1300.00€ sur un "compte courant" numéro "00001"
      And j'ai 200.00€ sur un "compte épargne" numéro "00002"
      When je vire -500.00€ de mon compte "00001" vers mon compte "00002"
      Then le virement échoue et son statut est "Le virement est rejeté car le montant du transfert est négatif"
      And j'ai maintenant 1300.00€ sur mon compte numéro "00001"
      And j'ai maintenant 200.00€ sur mon compte numéro "00002"
      And aucune opération n'est créée sur le compte numéro '00001'
      And aucune opération n'est créée sur le compte numéro '00002'

    @ignore
    Rule: RG5, RG9 - le compte source n'existe pas
    Example: compte courant inexistant
      Given je n'ai pas de compte numéro "00003"
      And j'ai 200.00€ sur un "Compte épargne" numéro "00002"
      When je vire 50.00€ de mon compte "00003" vers mon compte "00002"
      Then le virement échoue et son statut est "Le virement est rejeté car le compte source est inexistant"
      And j'ai maintenant 200.00€ sur mon compte numéro "00002"
      And aucune opération n'est créée sur le compte numéro '00002'

    @ignore
    Rule: RG6, RG9 - le compte destination n'existe pas
    Example: compte livret inexistant
      Given je n'ai pas de compte numéro "00004"
      And j'ai 300.00€ sur un "compte courant" numéro "00001"
      When je vire 50.00€ de mon compte "00001" vers mon compte "00004"
      Then le virement échoue et son statut est "Le virement est rejeté car le compte destinataire est inexistant"
      And j'ai maintenant 300.00€ sur mon compte numéro "00001"
      And aucune opération n'est créée sur le compte numéro '00001'

    @ignore
    Rule: RG7, RG9 - source et cible identiques
    Example: le compte source et destination sont identiques
      Given j'ai 300.00€ sur un "compte courant" numéro "00001"
      When je vire 50.00€ de mon compte "00001" vers mon compte "00001"
      Then le virement échoue et son statut est "Le virement est rejeté car le compte source et le compte destinataire sont identiques"
      And j'ai maintenant 300.00€ sur mon compte numéro "00001"
      And aucune opération n'est créée sur le compte numéro '00001'