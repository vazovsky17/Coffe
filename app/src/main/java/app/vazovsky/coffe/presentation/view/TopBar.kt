package app.vazovsky.coffe.presentation.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    onBackPressed: (() -> Unit)? = null,
) {
    CenterAlignedTopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        navigationIcon = {
            onBackPressed?.let {
                IconButton(onClick = onBackPressed) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = null
                    )
                }
            }
        },
        title = {
            Text(
                text = title,
            )
        },
    )
}