package pl.kreft.thesis.ecr.centralsystem.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NonNull
    String name;

    @NonNull
    String surname;

    @NonNull
    String email;

    @NonNull
    String password;


    @NonNull
    String rePassword;

}
