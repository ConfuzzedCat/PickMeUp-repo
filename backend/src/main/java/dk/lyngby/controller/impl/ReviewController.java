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
import dk.lyngby.model.UserMock;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;


public class ReviewController implements IController<Review, Integer> {

    //TODO:fix this when we have user
    //private final TokenFactory TOKEN_FACTORY = TokenFactory.getInstance();

    private static ReviewDAO reviewDAO;
    private static RouteDao routeDao;
    private static UserMockDAO userDAO;
    private static UserMock userMock;

public ReviewController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.reviewDAO = ReviewDAO.getInstance(emf);
        this.routeDao = RouteDao.getInstance(emf);
        this.userDAO = UserMockDAO.getInstance(emf);
    }


    @Override
    public void read(Context ctx) throws ApiException {
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        Review review = reviewDAO.read(id);

        ReviewDTO reviewDto = new ReviewDTO(review);

        ctx.res().setStatus(200);
        ctx.json(reviewDto, ReviewDTO.class);

    }

    @Override
    public void readAll(Context ctx) {
        ctx.res().setStatus(200);
        ctx.json(reviewDAO.readAll(), ReviewDTO.class);
    }

    @Override
    public void create(Context ctx) throws ApiException {
      //  String token = ctx.header("Authorization").split(" ")[1];
        //TODO:fix this when we have user
       //UserMockDTO userMockDTO = TOKEN_FACTORY.verifyToken(token);
        //UserMock user = userDAO.read(userMockDTO.getEmail());

        Gson gson = new Gson();
        int route = gson.fromJson(ctx.body(), JsonObject.class).get("routeId").getAsInt();
        int userId = gson.fromJson(ctx.body(), JsonObject.class).get("userId").getAsInt();
        String title = gson.fromJson(ctx.body(), JsonObject.class).get("title").getAsString();
        String description = gson.fromJson(ctx.body(), JsonObject.class).get("description").getAsString();
        double rating = gson.fromJson(ctx.body(), JsonObject.class).get("rating").getAsDouble();



        Review review = reviewDAO.create(route, userId, title, description, rating);

        ReviewDTO reviewDto = new ReviewDTO(review);

        ctx.res().setStatus(201);
        ctx.json(reviewDto, ReviewDTO.class);

    }

    @Override
    public void update(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();

        Review update = reviewDAO.update(id, ctx.bodyAsClass(Review.class));

        ReviewDTO reviewDto = new ReviewDTO(update);

        ctx.res().setStatus(200);
        ctx.json(reviewDto, ReviewDTO.class);


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
                .check(r -> r.getTitle() !=null, "Title must be at least 1 character")
                .check(r -> r.getDescription() !=null, "Description must be at least 1 character")
                .check(r -> routeDao.validatePrimaryKey(r.getRoute().getId()), "Route does not exist")
                .check(r -> userDAO.validatePrimaryKey(r.getUserMock().getEmail()), "User does not exist")
                .get();
    }
}
