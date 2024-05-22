package dk.lyngby.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Dimitra Siskou
 * A Mock DTO for a user
 * TODO: replace the user mock dto with the real user dto

 */

@Getter
@Setter
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

    public UserMockDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;

    }


}
