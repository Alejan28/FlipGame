package clientServer;

import Utils.LostGameDTO;
import clientServer.Repository.HibernateRepos.GameHibernateRepository;
import clientServer.Repository.Interfaces.GameRepoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@RestController
@RequestMapping("/animals/games")
@CrossOrigin(origins = "http://localhost:5173")
public class GameRestController {

    @Autowired
    private GameRepoInterface gameRepository;

    @GetMapping("/lost/{playerId}")
    public List<LostGameDTO> getLostGames(@PathVariable Integer playerId) {
        return gameRepository.getLostGamesForPlayer(playerId);
    }
}

