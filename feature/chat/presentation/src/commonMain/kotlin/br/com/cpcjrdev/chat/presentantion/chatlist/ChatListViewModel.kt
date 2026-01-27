package br.com.cpcjrdev.chat.presentantion.chatlist

import androidx.lifecycle.ViewModel
import br.com.cpcjrdev.core.domain.auth.SessionStorage

class ChatListViewModel(
    private val sessionStorage: SessionStorage,
) : ViewModel()
