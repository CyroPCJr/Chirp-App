package br.com.cpcjrdev.chat.data.lifecycle

import kotlinx.coroutines.flow.Flow

actual class AppLifecycleObserver {
    actual val isInForeground: Flow<Boolean>
        get() = TODO("Not yet implemented")
}