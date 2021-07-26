using System;
using TechTalk.SpecFlow;

namespace formation_tdd_bdd.Tests.Steps
{
    [Binding]
    public class VirementInterneSteps
    {
        [Given(@"j'ai un compte courant avec (.*)€")]
        public void GivenJAiUnCompteCourantAvec(int p0)
        {
            ScenarioContext.Current.Pending();
        }
        
        [Given(@"j'ai un compte épargne avec (.*)€")]
        public void GivenJAiUnCompteEpargneAvec(int p0)
        {
            ScenarioContext.Current.Pending();
        }
        
        [Given(@"j'ai un compte courant avec (.*)€ et un plafond de virement de (.*)€")]
        public void GivenJAiUnCompteCourantAvecEtUnPlafondDeVirementDe(int p0, int p1)
        {
            ScenarioContext.Current.Pending();
        }
        
        [When(@"je fais un virement de (.*)€ depuis mon compte courant vers mon compte épargne")]
        public void WhenJeFaisUnVirementDeDepuisMonCompteCourantVersMonCompteEpargne(int p0)
        {
            ScenarioContext.Current.Pending();
        }
        
        [When(@"je fais un virement de (.*)€ depuis mon compte courant vers mon compte courant")]
        public void WhenJeFaisUnVirementDeDepuisMonCompteCourantVersMonCompteCourant(int p0)
        {
            ScenarioContext.Current.Pending();
        }
        
        [Then(@"le status du virement est CONFIRME")]
        public void ThenLeStatusDuVirementEstCONFIRME()
        {
            ScenarioContext.Current.Pending();
        }
        
        [Then(@"le status du virement est HORS_PROVISION")]
        public void ThenLeStatusDuVirementEstHORS_PROVISION()
        {
            ScenarioContext.Current.Pending();
        }
        
        [Then(@"le status du virement est PLAFOND_DEPASSE")]
        public void ThenLeStatusDuVirementEstPLAFOND_DEPASSE()
        {
            ScenarioContext.Current.Pending();
        }
        
        [Then(@"le status du virement est MONTANT_NEGATIF")]
        public void ThenLeStatusDuVirementEstMONTANT_NEGATIF()
        {
            ScenarioContext.Current.Pending();
        }
        
        [Then(@"le status du virement est SOURCE_DOESNT_EXIST")]
        public void ThenLeStatusDuVirementEstSOURCE_DOESNT_EXIST()
        {
            ScenarioContext.Current.Pending();
        }
        
        [Then(@"le status du virement est CIBLE_DOESNT_EXIST")]
        public void ThenLeStatusDuVirementEstCIBLE_DOESNT_EXIST()
        {
            ScenarioContext.Current.Pending();
        }
        
        [Then(@"le status du virement est SOURCE_EQUALS_CIBLE")]
        public void ThenLeStatusDuVirementEstSOURCE_EQUALS_CIBLE()
        {
            ScenarioContext.Current.Pending();
        }
        
        [Then(@"une opération comptable est enregistrée")]
        public void ThenUneOperationComptableEstEnregistree()
        {
            ScenarioContext.Current.Pending();
        }
        
        [Then(@"aucune opération comptable n'est enregistrée")]
        public void ThenAucuneOperationComptableNEstEnregistree()
        {
            ScenarioContext.Current.Pending();
        }
    }
}
