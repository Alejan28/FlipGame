package clientServer;


import Domain.Move;
import clientServer.Repository.Interfaces.MoveRepoInterface;

import Utils.RepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;



import java.util.List;


//@CrossOrigin
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/animals/moves")
public class MoveRestController {

    private static final String template = "Hello, %s!";

    @Autowired
    private MoveRepoInterface moveRepository;

    @RequestMapping("/greeting")
    public  String greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return String.format(template, name);
    }


    @RequestMapping( value="/games/{id}",method=RequestMethod.GET)
    public List<Move> getAll(@PathVariable Integer id){
        System.out.println("Get all moves ..."+id);
        List<Move> moves = moveRepository.getMovesForLostGames(id);
        return moves;
    }
    @ExceptionHandler(RepositoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userError(RepositoryException e) {
        return e.getMessage();
    }
}
