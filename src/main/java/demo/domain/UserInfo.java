package demo.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.internal.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;
import sun.text.normalizer.IntTrie;

import javax.persistence.*;

/**
 * Created by vagrant on 4/10/17.
 */
@Embeddable
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class UserInfo {
    //fields of embedded object cannot be marked with @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) private int userId;
    private String name;
    private String address;

    @JsonCreator public UserInfo(@JsonProperty("userId") @Nullable Integer userId,
                                 @JsonProperty("username") String username,
                                 @JsonProperty("address") String address) {
        if(userId != null) this.userId = userId;
        this.name = username;
        this.address = address;
    }
}
