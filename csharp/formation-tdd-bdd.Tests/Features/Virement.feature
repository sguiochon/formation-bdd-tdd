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

  Scenario: Virement simple
    Given j'ai un compte courant avec 500€
    And j'ai un compte épargne avec 200€
    When je fais un virement de 100€ depuis mon compte courant vers mon compte épargne
    Then le status du virement est CONFIRME

  Scenario: Virement hors provision
    Given j'ai un compte courant avec 50€
    And j'ai un compte épargne avec 200€
    When je fais un virement de 100€ depuis mon compte courant vers mon compte épargne
    Then le status du virement est HORS_PROVISION

  Scenario: Virement plafonné
    Given j'ai un compte courant avec 1000€ et un plafond de virement de 500€
    And j'ai un compte épargne avec 0€
    When je fais un virement de 501€ depuis mon compte courant vers mon compte épargne
    Then le status du virement est PLAFOND_DEPASSE

  Scenario Outline: Montant négatif
    Given j'ai un compte courant avec 500€
    And j'ai un compte épargne avec 200€
    When je fais un virement de <montant>€ depuis mon compte courant vers mon compte épargne
    Then le status du virement est MONTANT_NEGATIF
    Examples:
      | montant |
      | 0       |
      | -100    |

  Scenario: Compte source n'existe pas
    Given j'ai un compte épargne avec 200€
    When je fais un virement de 100€ depuis mon compte courant vers mon compte épargne
    Then le status du virement est SOURCE_DOESNT_EXIST

  Scenario: Compte destination n'existe pas
    Given j'ai un compte courant avec 500€
    When je fais un virement de 100€ depuis mon compte courant vers mon compte épargne
    Then le status du virement est CIBLE_DOESNT_EXIST

  Scenario: Compte source et compte destination identiques
    Given j'ai un compte courant avec 500€
    When je fais un virement de 100€ depuis mon compte courant vers mon compte courant
    Then le status du virement est SOURCE_EQUALS_CIBLE

  Scenario: Opération écrite quand le statut est confirmé
    Given j'ai un compte courant avec 500€
    And j'ai un compte épargne avec 200€
    When je fais un virement de 100€ depuis mon compte courant vers mon compte épargne
    Then une opération comptable est enregistrée

  Scenario: Opération non écrite le statut n'est pas confirmé
    Given j'ai un compte courant avec 500€
    And j'ai un compte épargne avec 200€
    When je fais un virement de 501€ depuis mon compte courant vers mon compte épargne
    Then aucune opération comptable n'est enregistrée
