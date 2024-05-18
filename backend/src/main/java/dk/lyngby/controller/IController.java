package dk.lyngby.controller;

import dk.lyngby.exception.ApiException;
import io.javalin.http.Context;

public interface IController<T, D> {
    void read(Context ctx) throws ApiException;
    void readAll(Context ctx) throws ApiException;
    void create(Context ctx) throws ApiException;
    void update(Context ctx);
    void delete(Context ctx);
    boolean validatePrimaryKey(D d);
    T validateEntity(Context ctx);

}
