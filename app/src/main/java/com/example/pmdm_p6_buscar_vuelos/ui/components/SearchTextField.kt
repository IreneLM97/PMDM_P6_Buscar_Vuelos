package com.example.pmdm_p6_buscar_vuelos.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.pmdm_p6_buscar_vuelos.R

/**
 * Función que muestra el campo de texto de búsqueda.
 *
 * @param searchQuery Consulta de búsqueda actual.
 * @param onQueryChanged Función lambda que se ejecuta cuando cambia la consulta de búsqueda.
 */
@Composable
fun SearchTextField(
    searchQuery: String,
    onQueryChanged: (String) -> Unit
) {
    // Campo de texto para ingresar la consulta de búsqueda
    OutlinedTextField(
        value = searchQuery,
        onValueChange = { onQueryChanged(it) },
        singleLine = true,
        maxLines = 1,
        placeholder = {
            Text(text = stringResource(R.string.search_label))
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
            keyboardType = KeyboardType.Text
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(R.string.search_icon)
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 15.dp, top = 8.dp)
            .background(
                colorResource(R.color.my_light_green),
                shape = RoundedCornerShape(40.dp)
            )
            .clip(RoundedCornerShape(40.dp))
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.my_dark_green),
                shape = RoundedCornerShape(40.dp)
            )
    )
}