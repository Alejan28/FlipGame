package clientServer;

import Domain.Configuration;
import Utils.LostGameDTO;
import clientServer.Repository.Interfaces.ConfigurationRepoInterface;
import clientServer.Repository.Interfaces.GameRepoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/animals/configs")
@CrossOrigin(origins = "http://localhost:5173")
public class ConfigurationRestController {

    @Autowired
    private ConfigurationRepoInterface configurationRepository;

    @RequestMapping(method = RequestMethod.POST)
    public Configuration create(@RequestBody Configuration configuration) {
        System.out.println(configuration.getAnimal());
        configurationRepository.add(configuration);
        return configuration;
    }
}
