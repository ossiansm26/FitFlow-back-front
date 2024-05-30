package com.ossian.FitFlow.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
}
