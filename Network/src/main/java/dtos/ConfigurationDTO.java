package dtos;

public class ConfigurationDTO {
    private Integer id;
    private Integer row;
    private Integer column;
    private String animal;
    private String image;
    public ConfigurationDTO() {

    }
    public ConfigurationDTO(Integer id, Integer row, Integer column, String animal, String image) {
        this.id = id;
        this.row = row;
        this.column = column;
        this.animal = animal;
        this.image = image;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getRow() {
        return row;
    }
    public void setRow(Integer row) {
        this.row = row;
    }
    public Integer getColumn() {
        return column;
    }
    public void setColumn(Integer column) {
        this.column = column;
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
