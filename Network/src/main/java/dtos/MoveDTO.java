package dtos;

public class MoveDTO {
    private int id;
    private GameDTO game;
    private Integer x;
    private Integer y;
    public MoveDTO() {

    }
    public MoveDTO(int id, GameDTO game, Integer x, Integer y) {
        this.id = id;
        this.game = game;
        this.x = x;
        this.y = y;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public GameDTO getGame() {
        return game;
    }
    public void setGame(GameDTO game) {
        this.game = game;
    }
    public Integer getX() {
        return x;
    }
    public void setX(Integer x) {
        this.x = x;
    }
    public Integer getY() {
        return y;
    }
    public void setY(Integer y) {
        this.y = y;
    }
}

