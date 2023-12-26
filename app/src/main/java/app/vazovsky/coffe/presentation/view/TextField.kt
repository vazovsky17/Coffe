package app.vazovsky.coffe.presentation.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import app.vazovsky.coffe.R
import app.vazovsky.coffe.extensions.orDefault
import app.vazovsky.coffe.presentation.ui.theme.Champagne
import app.vazovsky.coffe.presentation.ui.theme.CoyoteBrown
import app.vazovsky.coffe.presentation.ui.theme.PaleTaupe

@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    title: String? = null,
    value: String,
    placeholder: String? = null,
    onValueChange: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Default,
    onDoneClick: (KeyboardActionScope.() -> Unit)? = null,
) {
    if (title != null) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = CoyoteBrown,
        )
    }
    Space(8.dp)
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(48.dp),
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder.orDefault(),
                style = MaterialTheme.typography.bodyLarge,
                color = Champagne,
            )
        },
        shape = RoundedCornerShape(24.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = CoyoteBrown,
            unfocusedBorderColor = CoyoteBrown,
            focusedTextColor = PaleTaupe,
            unfocusedTextColor = PaleTaupe,
        ),
        singleLine = true,
        //overflow = TextOverflow.Visible,
        maxLines = 1,
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = onDoneClick,
        ),
        textStyle = MaterialTheme.typography.bodyLarge,
    )
}