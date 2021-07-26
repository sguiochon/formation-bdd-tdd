using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using System.Threading.Tasks;

namespace formation_tdd_bdd.Controllers
{
    [ApiController]
    [Route("/virement")]
    public class VirementController : ControllerBase
    {
        private readonly ILogger<VirementController> logger;

        public VirementController(ILogger<VirementController> logger)
        {
            this.logger = logger;
        }

        [HttpGet]
        [Route("/virement/isAlive")]
        public bool IsAlive()
        {
            return true;
        }

        [HttpPost]
        [Route("/virement")]
        public Task CreateVirementAsync()
        {
            this.logger.LogDebug("Debut CreateVirementAsync");
            return Task.CompletedTask;
        }
    }
}
