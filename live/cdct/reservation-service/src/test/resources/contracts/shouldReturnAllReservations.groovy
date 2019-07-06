import org.springframework.cloud.contract.spec.Contract
import org.springframework.cloud.contract.spec.internal.HttpMethods
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

Contract.make {
    description("should return all Reservations")
    response {
        status(HttpStatus.OK.value())
        body([["id": "1", "name": "Jane"], ["id": "2", "name": "Joe"]])
        headers {
            contentType(MediaType.APPLICATION_JSON_VALUE)
        }
    }
    request {
        url("/reservations")
        method(HttpMethods.HttpMethod.GET)

    }
}