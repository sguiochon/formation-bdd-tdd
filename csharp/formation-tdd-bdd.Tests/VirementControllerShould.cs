using NUnit.Framework;

namespace formation_tdd_bdd.Tests
{
    using formation_tdd_bdd.Controllers;
    using Microsoft.Extensions.Logging;
    using Moq;

    public class VirementControllerShould
    {
        [SetUp]
        public void Setup()
        {
        }

        [Test]
        public void TellIsAliveIsTrue()
        {
            var controller = new VirementController(Mock.Of<ILogger<VirementController>>());
            var result = controller.IsAlive();
            Assert.IsTrue(result);
        }
    }
}