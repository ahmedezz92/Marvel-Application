package com.example.marvel.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.marvel.R

@Composable
fun SearchBar(
    onSearch: (String) -> Unit, modifier: Modifier = Modifier
) {
    var marvelName by rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    var isFocused by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF2B2B2B)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,

        ) {
        OutlinedTextField(
            value = marvelName,
            onValueChange = {
                marvelName = it
                if (it.isEmpty()) {
                    isFocused = false
                    keyboardController?.hide()
                } else {
                    isFocused = true
                }
            },
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF2B2B2B),
                unfocusedBorderColor = Color(0xFF2B2B2B),
                cursorColor = Color(0xFFECECEC)
            ),
            shape = RoundedCornerShape(16.dp),
            singleLine = true,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.hint_enter_name),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White
                    )
                )
            },
            textStyle = TextStyle(Color.White),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch(marvelName)
                    keyboardController?.hide()
                }
            )
        )

        Spacer(modifier = Modifier.width(16.dp))

        IconButton(
            onClick = {
                onSearch(marvelName)
                keyboardController?.hide()
            },
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = Color(0xFF2B2B2B),
                    shape = CircleShape
                )
        ) {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_search),
                contentDescription = "Search",
                tint = Color.Red,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun AnimatedSearchBar(
    onSearch: (query: String, isCancel: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    var searchText by rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    AnimatedContent(
        targetState = expanded,
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp), label = ""
    ) { isExpanded ->
        if (isExpanded) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 10.dp, bottom = 10.dp, end = 20.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        cursorColor = Color(0xFFECECEC)
                    ),
                    shape = RoundedCornerShape(5.dp),
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.hint_enter_name),
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color.LightGray
                            ),
                            fontSize = TextUnit(12f, TextUnitType.Sp)
                        )
                    },
                    textStyle = TextStyle(
                        Color.DarkGray,
                        fontSize = TextUnit(12f, TextUnitType.Sp)
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        onSearch(searchText, false)
                        keyboardController?.hide()
                    })
                )

                Text(
                    text = stringResource(id = R.string.label_cancel),
                    modifier = Modifier.clickable {
                        expanded = false
                        searchText = ""
                        onSearch("", true)
                        keyboardController?.hide()
                    },
                    color = Color.Red
                )

//                IconButton(
//                    onClick = {
//                        expanded = false
//                        searchText = ""
//                        onSearch("")
//                        keyboardController?.hide()
//                    }
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Close,
//                        contentDescription = "Cancel",
//                        tint = Color.Red,
//                        modifier = Modifier.size(24.dp)
//                    )
//                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(horizontal = 8.dp),
            ) {
                Image(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .requiredHeight(100.dp),
                    painter = painterResource(id = R.drawable.bg_logo),
                    contentDescription = "Marvel Logo"
                )

                IconButton(
                    onClick = {
                        expanded = true
                    },
                    modifier = Modifier.align(Alignment.CenterEnd)

                ) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_menu_search),
                        contentDescription = "Search",
                        tint = Color.Red,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}