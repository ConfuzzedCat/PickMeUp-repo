package dk.lyngby.dao.impl;

import dk.lyngby.dao.IDao;
import dk.lyngby.exception.ApiException;
import dk.lyngby.model.Review;
import dk.lyngby.model.Route;
import dk.lyngby.model.UserMock;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class ReviewDAO implements IDao<Review, Integer> {

    private static ReviewDAO reviewDAO;
    private static EntityManagerFactory emf;

    public static ReviewDAO getInstance(EntityManagerFactory _emf) {
        if (reviewDAO == null) {
            emf = _emf;
            reviewDAO = new ReviewDAO();
        }
        return reviewDAO;
    }

    @Override
    public Review read(Integer integer) throws ApiException {
        try (var em = emf.createEntityManager())
        {
            return em.find(Review.class, integer);
        }
    }

    @Override
    public List<Review> readAll() {
        try (var em = emf.createEntityManager())
        {
            var query = em.createQuery("SELECT r FROM Review r", Review.class);
            return query.getResultList();
        }
    }

    @Override
    public Review create(Review review) {
        try (var em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(review);
            em.getTransaction().commit();
            return review;
        }
    }

    public Review create (Integer routeId,Integer userId, String title, String description,
                          double stars  ) {
        try (var em = emf.createEntityManager()){
            em.getTransaction().begin();
            var u = em.find(UserMock.class, userId);
            var r = em.find(Route.class, routeId);
            var review = new Review(r,u,title, description, stars);
            em.persist(review);
            em.getTransaction().commit();
            return review;
        }
    }

    @Override
    public Review update(Integer integer, Review review) {
        try (var em = emf.createEntityManager())
        {
            em.getTransaction().begin();

            var r = em.find(Review.class, integer);
            r.setRoute(review.getRoute());
            r.setUserMock(review.getUserMock());
            r.setTitle(review.getTitle());
            r.setDescription(review.getDescription());
            r.setRating(review.getRating());


            em.getTransaction().commit();
            return r;
        }
    }

    @Override
    public void delete(Integer integer) {
        try (var em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            var review = em.find(Review.class, integer);
            em.remove(review);
            em.getTransaction().commit();
        }

    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        try (var em = emf.createEntityManager())
        {
            var review = em.find(Review.class, integer);
            return review != null;
        }
    }
 //TODO: change this from mock user to real user
    public List<Review> getReviewsByUserId(Integer userId) {
        try (var em = emf.createEntityManager())
        {
            var query = em.createQuery("SELECT r FROM Review r WHERE r.userMock.id = :userId", Review.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        }
    }

    public List<Review> getReviewsByRouteId(Integer routeId) {
        try (var em = emf.createEntityManager())
        {
            var query = em.createQuery("SELECT r FROM Review r WHERE r.route.id = :routeId", Review.class);
            query.setParameter("routeId", routeId);
            return query.getResultList();
        }
    }


}
