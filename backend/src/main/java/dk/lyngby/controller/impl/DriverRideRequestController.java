package dk.lyngby.controller.impl;

import dk.lyngby.config.HibernateConfig;
import dk.lyngby.controller.IController;
import dk.lyngby.dao.impl.DriverRideRequestDAO;
import dk.lyngby.dto.DriverRideRequestDTO;
import dk.lyngby.exception.ApiException;
import io.javalin.http.Context;

public class DriverRideRequestController implements IController<Void, Void> {

    private DriverRideRequestDAO dao = DriverRideRequestDAO.getInstance(HibernateConfig.getEntityManagerFactory());

    public void acceptRideRequest(Context ctx) throws ApiException {
        DriverRideRequestDTO requestDTO = ctx.bodyAsClass(DriverRideRequestDTO.class);
        dao.acceptRideRequest(requestDTO.getRequestSenderID(), requestDTO.getRideID());
        ctx.status(200).result("Ride request accepted successfully");
    }

    public void declineRideRequest(Context ctx) throws ApiException {
        DriverRideRequestDTO requestDTO = ctx.bodyAsClass(DriverRideRequestDTO.class);
        dao.declineRideRequest(requestDTO.getRequestSenderID(), requestDTO.getRideID());
        ctx.status(200).result("Ride request declined successfully");
    }

    @Override
    public void read(Context ctx) throws ApiException {
        // Not applicable for this controller
    }

    @Override
    public void readAll(Context ctx) throws ApiException {
        // Not applicable for this controller
    }

    @Override
    public void create(Context ctx) throws ApiException {
        // Not applicable for this controller
    }

    @Override
    public void update(Context ctx) {
        // Not applicable for this controller
    }

    @Override
    public void delete(Context ctx) {
        // Not applicable for this controller
    }

    @Override
    public boolean validatePrimaryKey(Void aVoid) {
        return false;
    }

    @Override
    public Void validateEntity(Context ctx) {
        return null;
    }
}
