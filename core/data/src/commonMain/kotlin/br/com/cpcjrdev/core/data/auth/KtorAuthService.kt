package br.com.cpcjrdev.core.data.auth

import br.com.cpcjrdev.core.data.dto.requests.EmailRequest
import br.com.cpcjrdev.core.data.dto.requests.RegisterRequest
import br.com.cpcjrdev.core.data.networking.get
import br.com.cpcjrdev.core.data.networking.post
import br.com.cpcjrdev.core.domain.auth.AuthService
import br.com.cpcjrdev.core.domain.util.DataError
import br.com.cpcjrdev.core.domain.util.EmptyResult
import io.ktor.client.HttpClient

class KtorAuthService(
    private val httpClient: HttpClient,
) : AuthService {
    override suspend fun register(
        email: String,
        username: String,
        password: String,
    ): EmptyResult<DataError.Remote> =
        httpClient.post(
            route = "/auth/register",
            body =
                RegisterRequest(
                    email = email,
                    username = username,
                    password = password,
                ),
        )

    override suspend fun resendVerificationEmail(email: String): EmptyResult<DataError.Remote> =
        httpClient.post(
            route = "/auth/resend-verification",
            body = EmailRequest(email),
        )

    override suspend fun verifyEmail(token: String): EmptyResult<DataError.Remote> =
        httpClient.get(
            route = "/auth/verify",
            queryParams = mapOf("token" to token),
        )
}
