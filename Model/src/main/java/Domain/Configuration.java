package Domain;
import jakarta.persistence.*;
@jakarta.persistence.Entity
@Table(name="configurations")
public class Configuration extends Entity<Integer>{
    @jakarta.persistence.Id
    private Integer id;
    @Column(name = "column", nullable = false)
    private Integer column;
    @Column(name = "row", nullable = false)
    private Integer row;
    @Column(name = "animal", nullable = false)
    private String animal;
    @Column(name="image",nullable=false)
    private String image;

    public Configuration() {
    }
    public Configuration(Integer column, Integer row, String animal, String image) {
        this.column = column;
        this.row = row;
        this.animal = animal;
        this.image = image;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getColumn() {
        return column;
    }
    public void setColumn(Integer column) {
        this.column = column;
    }
    public Integer getRow() {
        return row;
    }
    public void setRow(Integer row) {
        this.row = row;
    }
    public String getAnimal() {
        return animal;
    }
    public void setAnimal(String animal) {
        this.animal = animal;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
}
