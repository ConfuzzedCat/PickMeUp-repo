package dk.lyngby.dao;

import dk.lyngby.exception.ApiException;
import dk.lyngby.model.Route;

import java.util.List;

public interface IDao<T, D> {

    T read(Integer d) throws ApiException;
    List<T> readAll();
    Route create(Route t);
    Route update(Long d, Route t);
    void delete(Long d);
    boolean validatePrimaryKey(D d);

}
