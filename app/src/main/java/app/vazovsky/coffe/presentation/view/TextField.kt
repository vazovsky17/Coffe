package app.vazovsky.coffe.presentation.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(48.dp)
            .border(2.dp, CoyoteBrown, RoundedCornerShape(24.dp)),
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        maxLines = 1,
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = onDoneClick,
        ),
        textStyle = MaterialTheme.typography.titleSmall.copy(fontSize = 18.sp, color = PaleTaupe),
    ) { innerTextField ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(PaddingValues(18.dp, 13.dp))
        ) {
            if (value.isBlank()) {
                Text(
                    text = placeholder.orDefault(),
                    color = Champagne,
                    style = MaterialTheme.typography.titleSmall.copy(fontSize = 18.sp),
                )
            }
            innerTextField()
        }
    }
}