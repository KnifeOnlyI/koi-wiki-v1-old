package fr.koi.wikiapi.web.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Represent a username holder.
 */
@Getter
@Setter
@Accessors(chain = true)
public class UsernameModel {
    /**
     * The username.
     */
    private String username;
}
