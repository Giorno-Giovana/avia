package space.colaba.config


sealed class ApiException : RuntimeException() {
    class ValidationException(override val message: String) : ApiException()
    class NotFoundException(override val message: String) : ApiException()
}
