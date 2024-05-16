package dk.lyngby.model;


/**
 * @Author TobiasTonndorff
 *
 * Review model
 * We need to replace all UserMock implementations when a real user class has been implemented!
 * @TODO replace all UserMock annotations with real user class when implemented
 */

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.eclipse.jetty.server.Authentication;

import java.time.LocalDateTime;

@Entity
@Table(name = "review")
@Getter
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id")
    private Route route;

    @ManyToOne
    @JoinColumn(name = "userMock_id")
    private UserMock userMock;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Column(name = "description", length = 255, nullable = false)
    private String description;

    @Column(name = "rating", nullable = false)
    private double rating;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public Review(Route route, UserMock userMock, String title, String description, double rating) {
        this.route = route;
        this.userMock = userMock;
        this.title = title;
        this.description = description;
        this.rating = rating;

    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public UserMock getUserMock(){
        return userMock;
    }

    public void setUserMock(UserMock userMock) {
        this.userMock = userMock;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }



}
