package com.example.naptune.uiux

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun QuestionnaireScreen() {
    // State untuk melacak opsi yang dipilih (Yes/No)
    var selectedOption by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Kuisioner Harian",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Text(
            text = "Apakah kamu sering menggunakan smartphone? (lebih dari 4 jam perhari)",
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = (selectedOption == "Yes"),
                    onClick = { selectedOption = "Yes" }
                )
                Text(text = "Yes")
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = (selectedOption == "No"),
                    onClick = { selectedOption = "No" }
                )
                Text(text = "No")
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = { println("Jawaban disimpan: $selectedOption") },
            enabled = selectedOption.isNotEmpty(), // Tombol mati kalau belum milih
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {

            Text(
                text = "Next Question",
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}