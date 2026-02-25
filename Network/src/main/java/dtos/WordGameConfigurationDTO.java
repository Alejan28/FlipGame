package dtos;

import java.util.List;

public class WordGameConfigurationDTO {
    private int id;
    private List<WordDTO> words;
    public WordGameConfigurationDTO() {

    }
    public WordGameConfigurationDTO(int id, List<WordDTO> words) {
        this.id = id;
        this.words = words;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public List<WordDTO> getWords() {
        return words;
    }
    public void setWords(List<WordDTO> words) {
        this.words = words;
    }
}
