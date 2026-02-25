package Domain;

import jakarta.persistence.*;

@jakarta.persistence.Entity
@Table(name="choices")
public class Choice extends Entity<Integer>{
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "first_word", nullable = false)
    private String first_word;
    @Column(name = "second_word", nullable = false)
    private String second_word;
    @ManyToOne
    @JoinColumn(name = "word_game_id")
    private WordGame wordGame;
    public Choice() {

    }
    public WordGame getWordGame() {
        return wordGame;
    }

    public void setWordGame(WordGame wordGame) {
        this.wordGame = wordGame;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
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
