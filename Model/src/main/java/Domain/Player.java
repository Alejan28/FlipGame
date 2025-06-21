package Domain;
import jakarta.persistence.*;
@jakarta.persistence.Entity
@Table(name="players")
public class Player extends Entity<Integer> {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "nickname", nullable = false,unique = true)
    private String nickname;
    @Column(name = "surname", nullable = false)
    private String nume;
    @Column(name = "name", nullable = false)
    private String prenume;

    public Player(String nickname, String nume, String prenume) {
        this.nickname = nickname;
        this.nume = nume;
        this.prenume = prenume;
    }
    public Player(){

    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname=nickname;
    }

    public String getNume() {
        return nume;
    }
    public void setNume(String nume) {
        this.nume=nume;
    }
    public String getPrenume() {
        return prenume;
    }
    public void setPrenume(String prenume) {
        this.prenume=prenume;
    }

}
