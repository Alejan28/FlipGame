package clientServer.Repository.Interfaces;

import Domain.Entity;

public interface IRepository <ID, T extends Entity<ID>>{
    void add(T elem);
    void update(ID id, T elem);
    Iterable<T> findAll();
}
