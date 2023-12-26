package app.vazovsky.coffe.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import app.vazovsky.coffe.R
import app.vazovsky.coffe.presentation.ui.theme.Champagne
import app.vazovsky.coffe.presentation.ui.theme.PaleTaupe

@Composable
fun InfoDialog(
    title: String? = null,
    text: String? = null,
    confirmText: String = stringResource(R.string.dialog_ok),
    onConfirmClick: () -> Unit,
    onDismiss: () -> Unit,
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(horizontal = 16.dp)
                .background(PaleTaupe, RoundedCornerShape(12.dp))
        ) {
            Column(
                modifier = Modifier.padding(
                    top = 24.dp,
                    start = 24.dp,
                    end = 24.dp,
                    bottom = 16.dp
                )
            ) {

                title?.let {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
                        color = Champagne,
                    )
                    Space(16.dp)
                }

                text?.let {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.sp),
                        color = Champagne,
                    )
                    Space(20.dp)
                }

                Box(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.align(Alignment.TopEnd),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Space(24.dp)
                        AppButton(
                            text = confirmText,
                            height = 40.dp,
                            onClick = onConfirmClick,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun UnauthorizedDialog(onExitDialog: () -> Unit) {
    InfoDialog(
        title = stringResource(R.string.unauthorized_dialog_title),
        text = stringResource(R.string.unauthorized_dialog_text),
        onConfirmClick = onExitDialog,
        onDismiss = onExitDialog
    )
}