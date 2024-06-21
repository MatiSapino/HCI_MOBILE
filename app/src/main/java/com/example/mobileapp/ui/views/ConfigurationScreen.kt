package com.example.mobileapp.ui.views

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ConfigurationScreen(
//    onBack: () -> Unit,
//    onLanguageChange: (String) -> Unit
) {
    val languages = listOf("English", "Español")
    var selectedLanguage by remember { mutableStateOf("English") }

    Column(
        modifier = Modifier
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color.White, Color(0xFF87CEEB))
                )
            )
            .fillMaxSize()
            .padding(16.dp)
    ) {
//        IconButton(onClick = onBack, modifier = Modifier.align(Alignment.Start)) {
//            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
//        }

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .shadow(10.dp, RoundedCornerShape(16.dp))
                .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Settings", fontSize = 20.sp, color = Color.Black)

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Text(text = "Select Language:", fontSize = 16.sp, color = Color.Black)
                    Spacer(modifier = Modifier.height(8.dp))

                    languages.forEach { language ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
//                                .clickable {
//                                    selectedLanguage = language
//                                    onLanguageChange(language)
//                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
//                            RadioButton(
//                                selected = selectedLanguage == language,
//                                onClick = {
//                                    selectedLanguage = language
//                                    onLanguageChange(language)
//                                },
//                                colors = RadioButtonDefaults.colors(
//                                    selectedColor = Color(0xFF87CEEB),
//                                    unselectedColor = Color.Gray
//                                )
//                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = language, fontSize = 16.sp, color = Color.Black)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ConfigurationScreenPreview() {
    ConfigurationScreen(
//        onBack = {},
//        onLanguageChange = {}
    )
}
