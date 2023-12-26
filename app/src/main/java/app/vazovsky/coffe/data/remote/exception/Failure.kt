package app.vazovsky.coffe.data.remote.exception

sealed class Failure {
    abstract class FeatureFailure : Failure()
}