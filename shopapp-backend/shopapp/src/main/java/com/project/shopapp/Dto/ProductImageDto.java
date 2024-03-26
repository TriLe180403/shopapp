package com.project.shopapp.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductImageDto {
    @JsonProperty("product_id")
    @Min(value = 1, message = "ProductId must be > 0")
    private Long productId;

    @JsonProperty("image_url")
    @Size(min = 3, max = 200, message = "image's name")
    private String imageUrl;
}
