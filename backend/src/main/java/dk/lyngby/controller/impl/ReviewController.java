package dk.lyngby.controller.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dk.lyngby.config.HibernateConfig;
import dk.lyngby.controller.IController;
import dk.lyngby.dao.impl.ReviewDAO;
import dk.lyngby.dao.impl.RouteDao;
import dk.lyngby.dao.impl.UserMockDAO;
import dk.lyngby.dto.ReviewDTO;
import dk.lyngby.exception.ApiException;
import dk.lyngby.model.Review;
import dk.lyngby.model.Route;
import dk.lyngby.model.UserMock;
import dk.lyngby.util.PasOnRideValUtil;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

public class ReviewController implements IController<Review, Integer> {

    private final ReviewDAO reviewDAO;
    private final RouteDao routeDao;
    private final UserMockDAO userDAO;

    public ReviewController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.reviewDAO = ReviewDAO.getInstance(emf);
        this.routeDao = RouteDao.getInstance(emf);
        this.userDAO = UserMockDAO.getInstance(emf);
        PasOnRideValUtil.setEntityManagerFactory(emf);
    }

    @Override
    public void create(Context ctx) throws ApiException {
        Gson gson = new Gson();
        JsonObject json = gson.fromJson(ctx.body(), JsonObject.class);
        int routeId = json.get("routeId").getAsInt();
        int userId = json.get("userId").getAsInt();
        String title = json.get("title").getAsString();
        String description = json.get("description").getAsString();
        double rating = json.get("rating").getAsDouble();

        Route route = routeDao.read(routeId);
        UserMock user = userDAO.read(userId);

        try {
            if (PasOnRideValUtil.isUserPassengerOnRide(userId, routeId)) {
                Review review = new Review(route, user, title, description, rating);
                reviewDAO.create(review);
                ReviewDTO reviewDto = new ReviewDTO(review);
                ctx.json(reviewDto).status(201);
            } else {
                throw new ApiException(400, "User is not a passenger on this ride");
            }
        } catch (ApiException e) {
            ctx.status(e.getStatusCode()).result(e.getMessage());
        }
    }

    @Override
    public void read(Context ctx) throws ApiException {
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        Review review = reviewDAO.read(id);
        ReviewDTO reviewDto = new ReviewDTO(review);
        ctx.res().setStatus(200);
        ctx.json(reviewDto);
    }

    @Override
    public void readAll(Context ctx) {
        ctx.res().setStatus(200);
        ctx.json(reviewDAO.readAll(), ReviewDTO.class);
    }

    @Override
    public void update(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        Review update = reviewDAO.update(id, ctx.bodyAsClass(Review.class));
        ReviewDTO reviewDto = new ReviewDTO(update);
        ctx.res().setStatus(200);
        ctx.json(reviewDto);
    }

    @Override
    public void delete(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        reviewDAO.delete(id);
        ctx.res().setStatus(204);
    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        return reviewDAO.validatePrimaryKey(integer);
    }

    @Override
    public Review validateEntity(Context ctx) {
        return ctx.bodyValidator(Review.class)
                .check(r -> r.getRating() >= 0 && r.getRating() <= 5, "Rating must be between 0 and 5")
                .check(r -> r.getTitle() != null, "Title must be at least 1 character")
                .check(r -> r.getDescription() != null, "Description must be at least 1 character")
                .check(r -> routeDao.validatePrimaryKey(r.getRoute().getId()), "Route does not exist")
                .check(r -> userDAO.validatePrimaryKey(r.getUserMock().getEmail()), "User does not exist")
                .get();
    }
}
