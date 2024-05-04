package dk.lyngby.dao;

import dk.lyngby.exception.ApiException;

import java.util.List;

public interface IDao<T, D> {

    T read(D d) throws ApiException;
    List<T> readAll();
    T create(T t);
    T update(D d, T t);
    void delete(D d);
    boolean validatePrimaryKey(D d);

}
