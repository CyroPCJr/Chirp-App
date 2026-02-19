package br.com.cpcjrdev.chat.presentantion.createchat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.cpcjrdev.chat.domain.models.Chat
import br.com.cpcjrdev.chat.presentantion.components.ChatParticipantSearchTextSection
import br.com.cpcjrdev.chat.presentantion.components.ChatParticipantsSelectionSection
import br.com.cpcjrdev.chat.presentantion.components.ManageChatButtonSection
import br.com.cpcjrdev.chat.presentantion.components.ManageChatHeaderRow
import br.com.cpcjrdev.core.designsystem.components.brand.ChirpHorizontalDivider
import br.com.cpcjrdev.core.designsystem.components.buttons.ChirpButton
import br.com.cpcjrdev.core.designsystem.components.buttons.ChirpButtonStyle
import br.com.cpcjrdev.core.designsystem.components.dialogs.ChirpAdaptiveDialogSheetLayout
import br.com.cpcjrdev.core.designsystem.theme.ChirpTheme
import br.com.cpcjrdev.core.presentantion.util.DeviceConfiguration
import br.com.cpcjrdev.core.presentantion.util.ObserveAsEvents
import br.com.cpcjrdev.core.presentantion.util.clearFocusOnTap
import br.com.cpcjrdev.core.presentantion.util.currentDeviceConfiguration
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.cancel
import chirp.feature.chat.presentation.generated.resources.create_chat
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CreateChatRoot(
    onDismiss: () -> Unit,
    onChatCreated: (Chat) -> Unit,
    viewModel: CreateChatViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is CreateChatEvent.OnChatCreated -> onChatCreated(event.chat)
        }
    }

    ChirpAdaptiveDialogSheetLayout(
        onDismiss = onDismiss,
    ) {
        CreateChatScreen(
            state = state,
            onAction = { action ->
                when (action) {
                    CreateChatAction.OnDismissDialog -> onDismiss()
                    else -> Unit
                }
                viewModel.onAction(action)
            },
        )
    }
}

@Composable
fun CreateChatScreen(
    state: CreateChatState,
    onAction: (CreateChatAction) -> Unit,
) {
    var isTextFieldFocused by remember { mutableStateOf(false) }
    val imeHeight = WindowInsets.ime.getBottom(LocalDensity.current)
    val isKeyboardVisible = imeHeight > 0
    val configuration = currentDeviceConfiguration()

    val shouldHideHeader =
        configuration == DeviceConfiguration.MOBILE_LANDSCAPE ||
            (isKeyboardVisible && configuration != DeviceConfiguration.DESKTOP) || isTextFieldFocused

    Column(
        modifier =
            Modifier
                .clearFocusOnTap()
                .fillMaxWidth()
                .wrapContentHeight()
                .imePadding()
                .background(MaterialTheme.colorScheme.surface)
                .navigationBarsPadding(),
    ) {
        AnimatedVisibility(
            visible = !shouldHideHeader,
        ) {
            Column {
                ManageChatHeaderRow(
                    title = stringResource(Res.string.create_chat),
                    onCloseClick = {
                        onAction(CreateChatAction.OnDismissDialog)
                    },
                    modifier = Modifier.fillMaxWidth(),
                )
                ChirpHorizontalDivider()
            }
        }
        ChatParticipantSearchTextSection(
            queryState = state.queryTextState,
            onAddClick = {
                onAction(CreateChatAction.OnAddClick)
            },
            isSearchEnabled = state.canAddParticipant,
            isLoading = state.isSearching,
            modifier =
                Modifier
                    .fillMaxWidth(),
            error = state.searchError,
            onFocusChanged = {
                isTextFieldFocused = it
            },
        )
        ChirpHorizontalDivider()
        ChatParticipantsSelectionSection(
            selectedParticipants = state.selectedChatParticipants,
            modifier =
                Modifier
                    .fillMaxWidth(),
            searchResult = state.currentSearchResult,
        )
        ChirpHorizontalDivider()
        ManageChatButtonSection(
            primaryButton = {
                ChirpButton(
                    text = stringResource(Res.string.create_chat),
                    onClick = {
                        onAction(CreateChatAction.OnCreateChatClick)
                    },
                    enabled = state.selectedChatParticipants.isNotEmpty(),
                    isLoading = state.isCreatingChat,
                )
            },
            secondaryButton = {
                ChirpButton(
                    text = stringResource(Res.string.cancel),
                    onClick = {
                        onAction(CreateChatAction.OnDismissDialog)
                    },
                    style = ChirpButtonStyle.SECONDARY,
                )
            },
            error = state.createChatError?.asString(),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview
@Composable
private fun Preview() {
    ChirpTheme {
        CreateChatScreen(
            state = CreateChatState(),
            onAction = {},
        )
    }
}
