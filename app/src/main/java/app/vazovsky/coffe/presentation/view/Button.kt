package app.vazovsky.coffe.presentation.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.vazovsky.coffe.presentation.ui.theme.Champagne
import app.vazovsky.coffe.presentation.ui.theme.PullmanGreen

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(24.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = PullmanGreen,
            contentColor = Champagne,
            disabledContainerColor = Champagne,
            disabledContentColor = PullmanGreen,
        ),
        onClick = onClick,
    ) {
        Text(
            text = text,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.labelLarge
        )
    }
}