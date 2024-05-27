package dk.lyngby.dto;

import dk.lyngby.model.UserMock;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Dimitra Siskou
 * A Mock DTO for a user
 * TODO: replace the user mock dto with the real user dto

 */

@Getter
@Setter
@NoArgsConstructor
public class UserMockDTO {

    private Integer id;

    private String email;
    private String password;
    private String firstName;
    private String lastName;

    public UserMockDTO(Integer id, String email, String password, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserMockDTO(UserMock user) {
        //todo Should also include city and if user is verified
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
    }

    public UserMockDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;

    }

}
