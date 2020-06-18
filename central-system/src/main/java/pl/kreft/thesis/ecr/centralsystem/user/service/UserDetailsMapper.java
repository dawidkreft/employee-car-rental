package pl.kreft.thesis.ecr.centralsystem.user.service;

import pl.kreft.thesis.ecr.centralsystem.user.model.User;
import pl.kreft.thesis.ecr.centralsystem.user.model.UserDetailsProvider;
import pl.kreft.thesis.ecr.centralsystem.user.model.UserRole;

public class UserDetailsMapper {

    public static final String PREFIX_ROLE = "ROLE_";

    public static UserDetailsProvider getUserDetails(User user) {
        return UserDetailsProvider.builder()
                                  .id(user.getId())
                                  .email(user.getEmail())
                                  .role(userDetailsRoleMapper(user.getRole()))
                                  .isEnabled(!user.getRemoved())
                                  .password(user.getPassword())
                                  .build();
    }

    public static String userDetailsRoleMapper(UserRole role) {
        return PREFIX_ROLE + role.toString();
    }
}
