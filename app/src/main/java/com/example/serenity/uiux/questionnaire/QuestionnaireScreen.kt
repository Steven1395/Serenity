package com.example.serenity.uiux.questionnaire

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.serenity.R
import com.example.serenity.viewmodel.journal.JournalViewModel
import com.example.serenity.viewmodel.SleepViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun QuestionnaireScreen(
    onFinished: () -> Unit,
    viewModel: JournalViewModel = viewModel(),
    sleepViewModel: SleepViewModel = viewModel()
) {
    val DeepPurple = Color(0xFF422C73)
    val LightPurple = Color(0xFF9279F8)
    val PinkHighlight = Color(0xFFE91E63)

    val PoppinsFont = try {
        FontFamily(
            Font(R.font.poppins_regular, FontWeight.Normal),
            Font(R.font.poppins_bold, FontWeight.Bold)
        )
    } catch (e: Exception) {
        FontFamily.Default
    }

    val questions = remember {
        listOf(
            "Apakah kamu makan berat atau mengonsumsi alkohol dalam 3 jam sebelum tidur?",
            "Apakah kamu mengonsumsi kafein dalam 6 jam sebelum tidur?",
            "Apakah kamu sempat merasa cemas, stres, atau overthinking semalam sebelum memejamkan mata?",
            "Apakah kamu merasa segar dan berenergi saat bangun tidur pagi ini?"
        )
    }

    val answers = remember { mutableStateListOf(*Array(questions.size) { "" }) }
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var isFinished by remember { mutableStateOf(false) }

    var sleepHours by remember { mutableFloatStateOf(7f) }
    var screenTimeHours by remember { mutableFloatStateOf(2f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!isFinished) {
            Text(
                text = "Kuesioner Harian",
                fontFamily = PoppinsFont,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = DeepPurple,
                modifier = Modifier.padding(top = 24.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = questions[currentQuestionIndex],
                fontFamily = PoppinsFont,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                color = DeepPurple,
                modifier = Modifier.padding(bottom = 40.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = (answers[currentQuestionIndex] == "Ya"),
                        onClick = { answers[currentQuestionIndex] = "Ya" },
                        colors = RadioButtonDefaults.colors(selectedColor = DeepPurple, unselectedColor = DeepPurple)
                    )
                    Text(text = "Ya", fontFamily = PoppinsFont, color = DeepPurple)
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = (answers[currentQuestionIndex] == "Tidak"),
                        onClick = { answers[currentQuestionIndex] = "Tidak" },
                        colors = RadioButtonDefaults.colors(selectedColor = DeepPurple, unselectedColor = DeepPurple)
                    )
                    Text(text = "Tidak", fontFamily = PoppinsFont, color = DeepPurple)
                }
            }

            Spacer(modifier = Modifier.weight(1.2f))

            val onNextClick: () -> Unit = {
                if (currentQuestionIndex < questions.size - 1) {
                    currentQuestionIndex++
                } else {
                    isFinished = true
                }
            }

            if (currentQuestionIndex == 0) {
                Button(
                    onClick = onNextClick,
                    enabled = answers[currentQuestionIndex].isNotEmpty(),
                    colors = ButtonDefaults.buttonColors(containerColor = DeepPurple),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(0.6f).height(56.dp)
                ) {
                    Text(text = "Selanjutnya", fontFamily = PoppinsFont, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(
                        onClick = { currentQuestionIndex-- },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f).height(56.dp).padding(end = 8.dp)
                    ) {
                        Text(text = "Kembali", fontFamily = PoppinsFont, color = DeepPurple, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = onNextClick,
                        enabled = answers[currentQuestionIndex].isNotEmpty(),
                        colors = ButtonDefaults.buttonColors(containerColor = DeepPurple),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f).height(56.dp).padding(start = 8.dp)
                    ) {
                        Text(
                            text = if (currentQuestionIndex == questions.size - 1) "Selesai" else "Selanjutnya",
                            fontFamily = PoppinsFont,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

        } else {
            Text(
                text = "Satu Langkah Lagi!",
                fontFamily = PoppinsFont,
                color = DeepPurple,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Lengkapi data tidurmu agar Cherry AI bisa bekerja lebih optimal.",
                fontFamily = PoppinsFont,
                color = DeepPurple.copy(alpha = 0.7f),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 32.dp)
            )

            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                Text(text = "Berapa jam kamu tidur semalam?", fontFamily = PoppinsFont, color = DeepPurple, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                Text(text = String.format("%.1f Jam", sleepHours), fontFamily = PoppinsFont, color = LightPurple, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Slider(
                    value = sleepHours,
                    onValueChange = { sleepHours = it },
                    valueRange = 0f..12f,
                    steps = 11,
                    colors = SliderDefaults.colors(thumbColor = LightPurple, activeTrackColor = LightPurple, inactiveTrackColor = LightPurple.copy(alpha = 0.3f))
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                Text(text = "Berapa jam main HP sebelum tidur?", fontFamily = PoppinsFont, color = DeepPurple, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                Text(text = String.format("%.1f Jam", screenTimeHours), fontFamily = PoppinsFont, color = PinkHighlight, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Slider(
                    value = screenTimeHours,
                    onValueChange = { screenTimeHours = it },
                    valueRange = 0f..8f,
                    steps = 7,
                    colors = SliderDefaults.colors(thumbColor = PinkHighlight, activeTrackColor = PinkHighlight, inactiveTrackColor = PinkHighlight.copy(alpha = 0.3f))
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    // A. Simpan data kuesioner psikologis dengan logika baru
                    viewModel.saveRealQuestionnaireResult(answers.toList())

                    // B. LOGIKA MENGHITUNG KUALITAS TIDUR BERDASARKAN SAINS MEDIS
                    val finalQualityResult = if (sleepHours in 7f..9f && screenTimeHours <= 4f) {
                        "Bagus" // Tidur ideal dan screen time wajar
                    } else if (sleepHours < 6f || screenTimeHours >= 8f) {
                        "Buruk" // Kurang tidur kronis ATAU screen time sangat parah
                    } else {
                        "Buruk" // Sisanya dikategorikan kurang sehat
                    }

                    // C. DAPATKAN SINGKATAN HARI YANG SERAGAM DENGAN VIEWMODEL (PERBAIKAN)
                    val dayFormat = SimpleDateFormat("EEE", Locale("id", "ID"))
                    val rawDayName = dayFormat.format(Date()).replace(".", "").take(3)
                    val todayName = rawDayName.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                    }

                    // D. Simpan data tidur
                    sleepViewModel.saveSleepData(
                        dayName = todayName,
                        sleepHours = sleepHours,
                        screenTimeHours = screenTimeHours,
                        sleepQuality = finalQualityResult
                    )

                    // E. Navigasi Selesai ke Dashboard
                    onFinished()
                },
                colors = ButtonDefaults.buttonColors(containerColor = DeepPurple),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(0.9f).height(56.dp)
            ) {
                Text(text = "Simpan & Masuk Dashboard", fontFamily = PoppinsFont, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}