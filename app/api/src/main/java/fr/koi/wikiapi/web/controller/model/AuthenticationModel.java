package fr.koi.wikiapi.web.controller.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class AuthenticationModel {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
