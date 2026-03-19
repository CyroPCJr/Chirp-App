package br.com.cpcjrdev.chat.presentantion.managechat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.cpcjrdev.chat.presentantion.components.managechat.ManageChatAction
import br.com.cpcjrdev.chat.presentantion.components.managechat.ManageChatScreen
import br.com.cpcjrdev.core.designsystem.components.dialogs.ChirpAdaptiveDialogSheetLayout
import br.com.cpcjrdev.core.presentantion.util.ObserveAsEvents
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.chat_members
import chirp.feature.chat.presentation.generated.resources.save
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ManageChatRoot(
    chatId: String?,
    onDismiss: () -> Unit,
    onMembersAdded: () -> Unit,
    viewModel: ManageChatViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(chatId) {
        viewModel.onAction(ManageChatAction.ChatParticipants.OnSelectChat(chatId))
    }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is ManageChatEvent.OnMembersAdded -> onMembersAdded()
        }
    }

    ChirpAdaptiveDialogSheetLayout(
        onDismiss = onDismiss,
    ) {
        ManageChatScreen(
            headerText = stringResource(Res.string.chat_members),
            primaryButtonText = stringResource(Res.string.save),
            state = state,
            onAction = { action ->
                when (action) {
                    ManageChatAction.OnDismissDialog -> onDismiss()
                    else -> Unit
                }
                viewModel.onAction(action)
            },
        )
    }
}
