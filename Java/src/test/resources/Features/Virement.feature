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

#  Rule: les anniversaires laissent d'inoubliables souvenirs en général
#    Example: mon cas personnel
#      Given j'habite à Rouen
#      And j'ai beaucoup d'amis
#      When je fête mon anniversaire chez moi
#      Then tous mes amis viennent à la maison avec des cadeaux et de l'alcool
#      And j'ai une sacrée gueule de bois le lendemain

