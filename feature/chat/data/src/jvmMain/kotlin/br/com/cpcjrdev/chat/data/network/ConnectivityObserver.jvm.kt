package br.com.cpcjrdev.chat.data.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

actual class ConnectivityObserver {
    actual val isConnected: Flow<Boolean> = callbackFlow {

    }

}