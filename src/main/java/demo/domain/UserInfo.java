package demo.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by vagrant on 4/10/17.
 */
@Embeddable
@Data
public class UserInfo {
    //fields of embedded object cannot be marked with @Id
    private int userId;
    private String name;
    private String address;
}
