package br.com.cpcjrdev.chat.data.network

actual class ConnectionErrorHandler {
    actual fun getConnectionStateForError(cause: Throwable): br.com.cpcjrdev.chat.domain.models.ConnectionState {
        TODO("Not yet implemented")
    }

    actual fun transformException(exception: Throwable): Throwable {
        TODO("Not yet implemented")
    }

    actual fun isRetriableError(cause: Throwable): Boolean {
        TODO("Not yet implemented")
    }
}