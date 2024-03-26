package com.project.shopapp.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDto {
    @JsonProperty("user_id")
    @Min(value = 1, message = "User id must be > 0")
    private Long userId;

    @JsonProperty("full_name")
    private String fullname;

    private String email;

    @JsonProperty("phone_number")
    @NotBlank(message = "phone number  required")
    @Size(min = 5, message = "phone number must be at least 5 char")
    private String phoneNumber;

    private String address;

    private String note;

    @JsonProperty("total_money")
    @Min(value = 0, message = "Total money must be > 0")
    private Float totalMoney;

    @JsonProperty("shipping_method")
    private String shippingMethod;

    @JsonProperty("shipping_address")
    private String shippingAddress;

    @JsonProperty("shipping_date")
    private LocalDate shippingDate;

    @JsonProperty("payment_method")
    private String paymentMethod;
}
