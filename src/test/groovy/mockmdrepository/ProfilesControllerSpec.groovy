package mockmdrepository

import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import io.micronaut.test.annotation.MockBean
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
class ProfilesControllerSpec extends Specification {

    @Shared @AutoCleanup @Inject @Client("/")
    RxHttpClient client

    @Inject
    ProfileRepository repository

    void "test index"() {
        when:
        1 * repository.findAll() >> [new Profile('Profile 1'), new Profile('Profile 2')]
        HttpResponse<Iterable<Profile>> response = client.toBlocking().exchange("/profiles", Iterable)

        then:
        response.body().size() == 2
    }

    void "test get"() {
        when:
        1 * repository.findById(42) >> Optional.of(new Profile('Profile Forty Two'))
        HttpResponse<Iterable<Profile>> response = client.toBlocking().exchange("/profiles/42", Profile)

        then:
        response.body().name == 'Profile Forty Two'
    }

    @MockBean
    @Replaces(ProfileRepository)
    ProfileRepository mockRepo() {
        Mock(ProfileRepository)
    }
}

