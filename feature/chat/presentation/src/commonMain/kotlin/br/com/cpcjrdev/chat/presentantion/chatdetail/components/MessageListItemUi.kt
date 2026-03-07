package br.com.cpcjrdev.chat.presentantion.chatdetail.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.cpcjrdev.chat.domain.models.ChatMessageDeliveryStatus
import br.com.cpcjrdev.chat.presentantion.model.MessageUi
import br.com.cpcjrdev.chat.presentantion.model.MessageUi.DateSeparator
import br.com.cpcjrdev.chat.presentantion.model.MessageUi.LocalUserMessage
import br.com.cpcjrdev.chat.presentantion.model.MessageUi.OtherUserMessage
import br.com.cpcjrdev.core.designsystem.components.avatar.ChatParticipantUi
import br.com.cpcjrdev.core.designsystem.theme.ChirpTheme
import br.com.cpcjrdev.core.designsystem.theme.extended
import br.com.cpcjrdev.core.presentantion.util.UiText
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun MessageListItemUi(
    messageUi: MessageUi,
    onMessageLongClick: (LocalUserMessage) -> Unit,
    onDismissMessageMenu: () -> Unit,
    onDeleteClick: (LocalUserMessage) -> Unit,
    onRetryClick: (LocalUserMessage) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
    ) {
        when (messageUi) {
            is DateSeparator -> {
                DateSeparatorUi(
                    date = messageUi.date.asString(),
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            is LocalUserMessage -> {
                LocalUserMessage(
                    message = messageUi,
                    onMessageLongClick = { onMessageLongClick(messageUi) },
                    onDismissMessageMenu = onDismissMessageMenu,
                    onDeleteClick = { onDeleteClick(messageUi) },
                    onRetryClick = { onRetryClick(messageUi) },
                )
            }

            is OtherUserMessage -> {
                OtherUserMessage(
                    message = messageUi,
                )
            }
        }
    }
}

@Composable
private fun DateSeparatorUi(
    date: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        HorizontalDivider(modifier = Modifier.weight(1f))
        Text(
            text = date,
            modifier =
                Modifier
                    .padding(horizontal = 40.dp),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.extended.textPlaceholder,
        )
        HorizontalDivider(modifier = Modifier.weight(1f))
    }
}

@Composable
@Preview
fun MessageListItemLocalMessageUiPreview() {
    ChirpTheme {
        MessageListItemUi(
            messageUi =
                LocalUserMessage(
                    id = "1",
                    content = "Hello world, this is a preview message that spans multiple lines",
                    deliveryStatus = ChatMessageDeliveryStatus.SENT,
                    isMenuOpen = true,
                    formattedSentTime = UiText.DynamicString("Friday 2:20pm"),
                ),
            onRetryClick = {},
            onMessageLongClick = {},
            onDismissMessageMenu = {},
            onDeleteClick = {},
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(200.dp),
        )
    }
}

@Composable
@Preview
fun MessageListItemLocalMessageRetryUiPreview() {
    ChirpTheme {
        MessageListItemUi(
            messageUi =
                LocalUserMessage(
                    id = "1",
                    content = "Hello world, this is a preview message that spans multiple lines",
                    deliveryStatus = ChatMessageDeliveryStatus.FAILED,
                    isMenuOpen = false,
                    formattedSentTime = UiText.DynamicString("Friday 2:20pm"),
                ),
            onRetryClick = {},
            onMessageLongClick = {},
            onDismissMessageMenu = {},
            onDeleteClick = {},
            modifier =
                Modifier
                    .fillMaxWidth(),
        )
    }
}

@Composable
@Preview
fun MessageListItemOtherMessageUiPreview() {
    ChirpTheme {
        MessageListItemUi(
            messageUi =
                OtherUserMessage(
                    id = "1",
                    content = "Hello world, this is a preview message that spans multiple lines",
                    formattedSentTime = UiText.DynamicString("Friday 2:20pm"),
                    sender =
                        ChatParticipantUi(
                            id = "1",
                            username = "Philipp",
                            initials = "PH",
                        ),
                ),
            onRetryClick = {},
            onMessageLongClick = {},
            onDismissMessageMenu = {},
            onDeleteClick = {},
            modifier =
                Modifier
                    .fillMaxWidth(),
        )
    }
}
