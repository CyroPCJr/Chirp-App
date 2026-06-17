package br.com.cpcjrdev.chat.presentantion.chatlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.cpcjrdev.chat.domain.chat.ChatRepository
import br.com.cpcjrdev.chat.presentantion.mappers.toUi
import br.com.cpcjrdev.core.domain.auth.SessionStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ChatListViewModel(
    private val repository: ChatRepository,
    private val sessionStorage: SessionStorage,
) : ViewModel() {
    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(ChatListState())
    val state =
        combine(
            _state,
            repository.getChats(),
            sessionStorage.observeAuthInfo(),
        ) { currentState, chats, authInfo ->
            if (authInfo == null) {
                return@combine ChatListState()
            }

            currentState.copy(
                chats = chats.map { it.toUi(authInfo.user.id) },
                localParticipant = authInfo.user.toUi(),
            )
        }.onStart {
            if (!hasLoadedInitialData) {
                loadChats()
                hasLoadedInitialData = true
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ChatListState(),
        )

    fun onAction(action: ChatListAction) {
        when (action) {
            is ChatListAction.OnSelectChat -> {
                _state.value = _state.value.copy(selectedChatId = action.chatId)
            }

            else -> {
                Unit
            }
        }
    }

    private fun loadChats() {
        viewModelScope.launch {
            repository.fetchChats()
        }
    }
}
