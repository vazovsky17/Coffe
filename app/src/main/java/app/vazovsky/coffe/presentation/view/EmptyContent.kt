package app.vazovsky.coffe.presentation.view

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import app.vazovsky.coffe.R

@Composable
fun EmptyContent(text: String = stringResource(R.string.empty_text)) {
    Text(text)
}