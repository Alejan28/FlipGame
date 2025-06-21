package dtos;

public class PlayerDTO {
    private Integer id;
    private String nickname;
    private String nume;
    private String prenume;

    public PlayerDTO(){

    }
    public PlayerDTO(Integer id, String nickname, String nume, String prenume) {
        this.id = id;
        this.nickname = nickname;
        this.nume = nume;
        this.prenume = prenume;
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
        this.nickname = nickname;
    }
    public String getNume() {
        return nume;
    }
    public void setNume(String nume) {
        this.nume = nume;
    }
    public String getPrenume() {
        return prenume;
    }
    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

}
