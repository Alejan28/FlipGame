package Domain;
import jakarta.persistence.*;
@jakarta.persistence.Entity
@Table(name="moves")
public class Move extends Entity<Integer> {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;
    @Column(name = "x", nullable = false)
    private int x;
    @Column(name = "y", nullable = false)
    private int y;
    public Move() {

    }
    public Move(Integer id, Game game, int x, int y) {
        this.id = id;
        this.game = game;
        this.x = x;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Game getGame() {
        return game;
    }
    public void setGame(Game game) {
        this.game = game;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

}
