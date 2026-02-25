package dtos;

public class WordDTO {
    private int id;
    private String word;
    public WordDTO() {
        super();
    }
    public WordDTO(int id, String word) {
        this.id = id;
        this.word = word;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }
}
