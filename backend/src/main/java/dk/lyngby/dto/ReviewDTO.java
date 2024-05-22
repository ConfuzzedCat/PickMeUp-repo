package dk.lyngby.dto;

import dk.lyngby.model.Review;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Getter
@Setter
public class ReviewDTO {


    private Integer id;
    private String title;
    private String description;
    private double rating;

    //TODO: Implement UserDTO
    private UserMockDTO user;
    private RouteDTO route;
    private LocalDateTime createdAt;


    public ReviewDTO(Integer id, RouteDTO route,UserMockDTO user, String title, String description,
                     double rating,  LocalDateTime createdAt) {
        this.id = id;
        this.route = route;
        this.user = user;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.createdAt = createdAt;

    }

    public ReviewDTO(Review review) {
        this.id = review.getId();
        this.route = new RouteDTO(review.getRoute());
        this.user = new UserMockDTO(review.getUserMock().getFirstName(), review.getUserMock().getLastName());
        this.title = review.getTitle();
        this.description = review.getDescription();
        this.rating = review.getRating();
        this.createdAt = review.getCreatedAt();

    }

    public static List<ReviewDTO> toReviewDTOList(List<Review> reviews) {
        return reviews.stream().map(ReviewDTO::new).collect(Collectors.toList());
    }


}
