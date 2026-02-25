package Domain;

import jakarta.persistence.*;

@jakarta.persistence.Entity
@Table(name="word")
public class Word extends Entity<Integer>{
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "text", nullable = false)
    private String word;
    public Word(){

    }
    public Word(String word){
        this.word = word;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }
}
