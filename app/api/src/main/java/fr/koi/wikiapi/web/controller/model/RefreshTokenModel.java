package fr.koi.wikiapi.web.controller.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class RefreshTokenModel {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String token;
}
