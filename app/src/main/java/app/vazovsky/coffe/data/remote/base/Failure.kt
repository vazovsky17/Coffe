package app.vazovsky.coffe.data.remote.base

sealed class Failure {
    data object NetworkConnection : Failure()
    data object ServerError : Failure()

    abstract class FeatureFailure : Failure()
}