package com.example.marvel.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.example.marvel.R

@Composable
fun SearchBar(onSearch: (String) -> Unit) {
    var marvelName by rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    var isFocused by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        value = marvelName,
        onValueChange = { newValue ->
            marvelName = newValue
            if (newValue.isEmpty()) {
                isFocused = false
                keyboardController?.hide()
            } else {
                isFocused = true
            }
        },
        label = { if (!isFocused && marvelName.isEmpty()) Text(stringResource(id = R.string.hint_enter_name)) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch(marvelName)
                keyboardController?.hide()
            }
        ),
        singleLine = true
    )
}