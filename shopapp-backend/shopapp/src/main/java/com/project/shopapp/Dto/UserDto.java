package com.project.shopapp.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserDto {
    @JsonProperty("fullname")
    private String fullName;

    @JsonProperty("phone_number")

    private String PhoneNumber;

    private String address;

    private String password;

    @JsonProperty("retype_password")
    private String retypePassword;

    @JsonProperty("date_of_birth")
    private Date dateOfBirth;

    @JsonProperty("facebook_account_id")
    private int facebookAccountId;

    @JsonProperty("google_account_id")
    private int googleAccountId;

    @JsonProperty("role_id")

    private Long roleId;
}
