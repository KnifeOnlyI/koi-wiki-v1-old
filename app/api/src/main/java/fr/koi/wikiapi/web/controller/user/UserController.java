package fr.koi.wikiapi.web.controller.user;

import fr.koi.wikiapi.constants.Urls;
import fr.koi.wikiapi.service.user.UserService;
import fr.koi.wikiapi.web.model.user.UsernameModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller to manage users.
 */
@RestController
@RequiredArgsConstructor
@Transactional
public class UserController {
    /**
     * The service to manage users.
     */
    private final UserService userService;

    /**
     * Get the username of the specified user ID.
     *
     * @param id The ID of user to check
     *
     * @return The HTTP response that contains the corresponding username
     */
    @GetMapping(Urls.User.UNIQUE)
    public UsernameModel getUsername(@PathVariable final String id) {
        return this.userService.getUsername(id);
    }
}
