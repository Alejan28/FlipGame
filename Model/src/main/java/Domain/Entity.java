package Domain;

public class Entity<T> {
    private T id;
    public void setId(T id){
        this.id = id;
    }
    public T getId(){
        return id;
    }
}
