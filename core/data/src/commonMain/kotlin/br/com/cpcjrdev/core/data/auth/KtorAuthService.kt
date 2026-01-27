package br.com.cpcjrdev.core.data.auth

import br.com.cpcjrdev.core.data.dto.AuthInfoSerializable
import br.com.cpcjrdev.core.data.dto.requests.EmailRequest
import br.com.cpcjrdev.core.data.dto.requests.LoginRequest
import br.com.cpcjrdev.core.data.dto.requests.RegisterRequest
import br.com.cpcjrdev.core.data.dto.requests.ResetPasswordRequest
import br.com.cpcjrdev.core.data.mappers.toDomain
import br.com.cpcjrdev.core.data.networking.get
import br.com.cpcjrdev.core.data.networking.post
import br.com.cpcjrdev.core.domain.auth.AuthInfo
import br.com.cpcjrdev.core.domain.auth.AuthService
import br.com.cpcjrdev.core.domain.util.DataError
import br.com.cpcjrdev.core.domain.util.EmptyResult
import br.com.cpcjrdev.core.domain.util.Result
import br.com.cpcjrdev.core.domain.util.map
import io.ktor.client.HttpClient

class KtorAuthService(
    private val httpClient: HttpClient,
) : AuthService {
    override suspend fun login(
        email: String,
        password: String,
    ): Result<AuthInfo, DataError.Remote> =
        httpClient
            .post<LoginRequest, AuthInfoSerializable>(
                route = "/auth/login",
                body =
                    LoginRequest(
                        email = email,
                        password = password,
                    ),
            ).map { it.toDomain() }

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

    override suspend fun forgotPassword(email: String): EmptyResult<DataError.Remote> =
        httpClient.post<EmailRequest, Unit>(
            route = "/auth/forgot-password",
            body = EmailRequest(email),
        )

    override suspend fun resetPassword(
        newPassword: String,
        token: String,
    ): EmptyResult<DataError.Remote> =
        httpClient.post(
            route = "/auth/reset-password",
            body =
                ResetPasswordRequest(
                    newPassword = newPassword,
                    token = token,
                ),
        )
}
