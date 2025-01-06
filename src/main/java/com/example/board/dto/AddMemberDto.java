package com.example.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AddMemberDto {

    @NotBlank
    private String name;

    @NotBlank
    private String password;
}
