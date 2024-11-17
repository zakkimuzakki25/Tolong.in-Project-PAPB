package com.papb.tolonginprojectpapb.ui.components.inputs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.papb.tolonginprojectpapb.R
import com.papb.tolonginprojectpapb.ui.theme.Based500
import com.papb.tolonginprojectpapb.ui.theme.Neutral100
import com.papb.tolonginprojectpapb.ui.theme.SetTypography

@Composable
fun InputBar(
    type: InputType,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeHolder: String,
    modifier: Modifier = Modifier
) {
    var isPasswordVisible by remember { mutableStateOf(false)}

    val keyboardType = when (type) {
        InputType.TEXT -> KeyboardType.Text
        InputType.EMAIL -> KeyboardType.Email
        InputType.PHONE -> KeyboardType.Phone
        InputType.DATE -> KeyboardType.Text
        InputType.PASSWORD -> KeyboardType.Password
    }

    val visualTransformation = if (type == InputType.PASSWORD && !isPasswordVisible) {
        PasswordVisualTransformation()
    } else {
        VisualTransformation.None
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 14.sp,
            lineHeight = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        OutlinedTextField(
            value = value,
            textStyle = SetTypography.bodyMedium,
            onValueChange = onValueChange,
            placeholder = { Text(text = placeHolder, style = SetTypography.bodyMedium, color = Based500) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
            visualTransformation = visualTransformation,
            trailingIcon = when (type) {
                InputType.PASSWORD -> {
                    {
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                            Icon(
                                painter = painterResource(
                                    id = if (isPasswordVisible) R.drawable.ic_visibility else R.drawable.ic_visibility_off
                                ),
                                contentDescription = if (isPasswordVisible) "Hide password" else "Show password",
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }
                else -> null
            },
            leadingIcon = if (type == InputType.PHONE) {
                {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "+62",
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            fontSize = 14.sp,
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        VerticalDivider(
                            color = Neutral100,
                            modifier = Modifier
                                .width(2.dp)
                                .fillMaxHeight()
                        )
                    }
                }
            } else if (type == InputType.DATE) {
                {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Spacer(modifier = Modifier.width(13.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.ic_calendar),
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(13.dp))
                        VerticalDivider(
                            color = Neutral100,
                            modifier = Modifier
                                .width(2.dp)
                                .fillMaxHeight()
                        )
                    }
                }
            } else null,
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Neutral100,
                unfocusedBorderColor = Neutral100
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
                .padding(vertical = 0.dp)
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun AllInputsPreview() {
    val text = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val date = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        InputBar(
            type = InputType.TEXT,
            value = text.value,
            onValueChange = { text.value = it },
            label = "Nama Lengkap",
            placeHolder = "Masukkan nama lengkap"
        )
        InputBar(
            type = InputType.EMAIL,
            value = email.value,
            onValueChange = { email.value = it },
            label = "Email",
            placeHolder = "Masukkan email"
        )
        InputBar(
            type = InputType.PHONE,
            value = phone.value,
            onValueChange = { phone.value = it },
            label = "Nomor Telepon",
            placeHolder = "Masukkan nomor telepon"
        )
        InputBar(
            type = InputType.DATE,
            value = date.value,
            onValueChange = { date.value = it },
            label = "Tanggal Lahir",
            placeHolder = "Masukkan tanggal lahir"
        )
        InputBar(
            type = InputType.PASSWORD,
            value = password.value,
            onValueChange = { password.value = it },
            label = "Password",
            placeHolder = "Masukkan password"
        )
    }
}
