package org.mifos.mobile.core.ui.component


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.mifos.mobile.core.ui.theme.MifosMobileTheme

@Composable
fun MifosRadioButtonAlertDialog(
    setTitle: String,
    setSingleChoiceItems: Array<String>,
    onClick: (index: Int) -> Unit,
    onDismissRequest: () -> Unit,
    setSelectedItemValue: String,
) {

    AlertDialog(
        onDismissRequest = { onDismissRequest.invoke() },
        confirmButton = { },
        title = {
            Text(text = setTitle)
        },
        text = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 500.dp)
            ) {
                items(setSingleChoiceItems.size) { index ->
                    val option = setSingleChoiceItems[index]
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clickable {
                                onDismissRequest.invoke()
                                onClick.invoke(index)
                            }
                            .fillMaxWidth()
                    ) {
                        RadioButton(
                            selected = (option == setSelectedItemValue),
                            onClick = {
                                onDismissRequest.invoke()
                                onClick.invoke(index)
                            }
                        )
                        Text(
                            text = option,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }
        }
    )
}

@Preview(showSystemUi = true)
@Composable
fun PreviewRadioButtonDialog() {
    MifosMobileTheme {
        MifosRadioButtonAlertDialog(
            setTitle = "Select an Option",
            setSingleChoiceItems = arrayOf("1", "2", "3"),
            onClick = { },
            onDismissRequest = {  },
            setSelectedItemValue = "1"
        )
    }
}