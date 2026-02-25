package dtos;

public class ChoiceDTO {
    private Integer id;
    private WordGameDTO wordGame;
    private String first_word;
    private String second_word;

    public ChoiceDTO() {

    }
    public ChoiceDTO(Integer id, WordGameDTO wordGame, String first_word, String second_word) {
        this.id = id;
        this.wordGame = wordGame;
        this.first_word = first_word;
        this.second_word = second_word;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public WordGameDTO getWordGame() {
        return wordGame;
    }
    public void setWordGame(WordGameDTO wordGame) {
        this.wordGame = wordGame;
    }
    public String getFirst_word() {
        return first_word;
    }
    public void setFirst_word(String first_word) {
        this.first_word = first_word;
    }
    public String getSecond_word() {
        return second_word;
    }
    public void setSecond_word(String second_word) {
        this.second_word = second_word;
    }

}
