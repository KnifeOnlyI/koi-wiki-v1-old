package fr.koi.wikiapi.web.model.graphql.user;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class AuthorModel {
    private String id;

    private String username;
}
