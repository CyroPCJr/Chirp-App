package br.com.cpcjrdev.chat.presentantion.components.managechat

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
import br.com.cpcjrdev.chat.presentantion.components.ChatParticipantSearchTextSection
import br.com.cpcjrdev.chat.presentantion.components.ChatParticipantsSelectionSection
import br.com.cpcjrdev.chat.presentantion.components.ManageChatButtonSection
import br.com.cpcjrdev.chat.presentantion.components.ManageChatHeaderRow
import br.com.cpcjrdev.core.designsystem.components.brand.ChirpHorizontalDivider
import br.com.cpcjrdev.core.designsystem.components.buttons.ChirpButton
import br.com.cpcjrdev.core.designsystem.components.buttons.ChirpButtonStyle
import br.com.cpcjrdev.core.designsystem.theme.ChirpTheme
import br.com.cpcjrdev.core.presentantion.util.DeviceConfiguration
import br.com.cpcjrdev.core.presentantion.util.clearFocusOnTap
import br.com.cpcjrdev.core.presentantion.util.currentDeviceConfiguration
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.cancel
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ManageChatScreen(
    headerText: String,
    primaryButtonText: String,
    state: ManageChatState,
    onAction: (ManageChatAction) -> Unit,
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
                    title = headerText,
                    onCloseClick = {
                        onAction(ManageChatAction.OnDismissDialog)
                    },
                    modifier = Modifier.fillMaxWidth(),
                )
                ChirpHorizontalDivider()
            }
        }
        ChatParticipantSearchTextSection(
            queryState = state.queryTextState,
            onAddClick = {
                onAction(ManageChatAction.OnAddClick)
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
            existingParticipants = state.existingChatParticipants,
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
                    text = primaryButtonText,
                    onClick = {
                        onAction(ManageChatAction.OnPrimaryActionClick)
                    },
                    enabled = state.selectedChatParticipants.isNotEmpty(),
                    isLoading = state.isSubmitting,
                )
            },
            secondaryButton = {
                ChirpButton(
                    text = stringResource(Res.string.cancel),
                    onClick = {
                        onAction(ManageChatAction.OnDismissDialog)
                    },
                    style = ChirpButtonStyle.SECONDARY,
                )
            },
            error = state.submitError?.asString(),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview
@Composable
private fun Preview() {
    ChirpTheme {
        ManageChatScreen(
            primaryButtonText = "Create Chat",
            state = ManageChatState(),
            onAction = {},
            headerText = "Create chat",
        )
    }
}
