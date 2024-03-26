package com.project.shopapp.Dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    @NotEmpty(message = "category's name cannot empty")
    private String name;
}
