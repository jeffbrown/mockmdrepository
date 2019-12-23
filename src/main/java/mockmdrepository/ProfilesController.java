package mockmdrepository;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import java.util.Optional;

@Controller("/profiles")
public class ProfilesController {

    private final ProfileRepository profileRepository;

    public ProfilesController(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Get("/")
    public Iterable<Profile> index() {
        return profileRepository.findAll();
    }

    @Get("/{id}")
    public Optional<Profile> get(Long id) {
        return profileRepository.findById(id);
    }
}