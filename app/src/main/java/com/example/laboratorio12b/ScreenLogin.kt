package com.example.laboratorio12b

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenLogin(navController: NavHostController) {
    val auth = Firebase.auth
    val contexto = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var logeado: String
    Box(modifier = Modifier.fillMaxSize()) {
        ClickableText(
            text = AnnotatedString("Regístrese aquí"),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp),
            onClick = {
                Toast.makeText(contexto, "Creando...",Toast.LENGTH_SHORT).show()
                if (username == "" || password == "") {
                    Toast.makeText(contexto, "No puede dejar las casillas vacías", Toast.LENGTH_SHORT).show()
                } else {
                    auth.createUserWithEmailAndPassword(username, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(contexto, "Usuario creado en firebase",
                                    Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(contexto, "Vuelva a intentarlo",
                                    Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            },
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily.Default,
                textDecoration = TextDecoration.Underline,
                color = Color.Blue
            )
        )
    }
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Laboratorio 12",
            style = TextStyle(
                fontSize = 40.sp,
                fontFamily = FontFamily.Cursive
            )
        )

        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Usuario") },
            value = username,
            onValueChange = { username = it })

        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Contraseña") },
            value = password,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { password = it })

        Spacer(modifier = Modifier.height(20.dp))
        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
            Button(
                onClick = {
                    if (username == "" || password == "") {
                        Toast.makeText(contexto, "No puede dejar las casillas vacias",Toast.LENGTH_SHORT).show()
                    } else {
                        auth.signInWithEmailAndPassword(username,password)
                            .addOnCompleteListener{task->
                                if(task.isSuccessful){
                                    logeado = username + ">" + password
                                    navController.navigate("response/$logeado")
                                }
                                else{
                                    Toast.makeText(contexto, "Usuario o contraseña incorrecto",Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                },

                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(Color(40, 52, 119, 255)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Iniciar sesión")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        ClickableText(
            text = AnnotatedString("¿Olvidaste la contraseña?"),
            onClick = { },
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily.Default
            )
        )
    }
}
