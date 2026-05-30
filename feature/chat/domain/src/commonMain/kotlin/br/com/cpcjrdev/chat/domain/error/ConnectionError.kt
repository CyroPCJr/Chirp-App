package br.com.cpcjrdev.chat.domain.error

import br.com.cpcjrdev.core.domain.util.Error

enum class ConnectionError : Error {
    NOT_CONNECTED,
    MESSAGE_SEND_FAILED
}