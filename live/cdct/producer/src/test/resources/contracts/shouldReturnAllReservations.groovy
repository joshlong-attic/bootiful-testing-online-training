import org.springframework.cloud.contract.spec.Contract
import org.springframework.cloud.contract.spec.internal.HttpMethods

Contract.make {

    description("should return all Reservations")
    request {
        url("/reservations")
        method(HttpMethods.HttpMethod.GET)
    }
    response {
        body(

                """
                    [
                        {"id":"1", "reservationName":"Ai"},
                        {"id":"2", "reservationName":"Zhang"}
                    ]
                """
        )
        status(200)
        headers {
            contentType(applicationJsonUtf8())
        }
    }
}