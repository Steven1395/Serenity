package com.example.serenity.uiux.natunai

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.serenity.R
import com.example.serenity.data.natunai.ChatEntity
import com.example.serenity.viewmodel.natunai.AiViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiScreen(
    onNavigateBack: () -> Unit,
    viewModel: AiViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val inputText by viewModel.inputText.collectAsState()
    val chatHistory by viewModel.chatHistory.collectAsState()

    val allSessions by viewModel.allSessions.collectAsState()
    val currentSessionId by viewModel.currentSessionId.collectAsState()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val BackgroundColor = Color(0xFF231B45)
    val CardColor = Color(0xFF3F356B)
    val UserBubbleColor = Color(0xFFD8578A)
    val TextWhite = Color(0xFFFFFFFF)
    val TextGray = Color(0xFFBDB7D0)

    val PoppinsFont = try {
        FontFamily(
            Font(R.font.poppins_regular, FontWeight.Normal),
            Font(R.font.poppins_bold, FontWeight.Bold)
        )
    } catch (_: Exception) {
        FontFamily.Default
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = BackgroundColor,
                modifier = Modifier.width(280.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(24.dp)
                ) {
                    Text(
                        text = "Riwayat Chat 💬",
                        fontFamily = PoppinsFont,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )

                    Button(
                        onClick = {
                            viewModel.startNewSession()
                            scope.launch { drawerState.close() }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = UserBubbleColor),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                    ) {
                        Text(text = "+ Chat Baru", fontFamily = PoppinsFont, color = TextWhite)
                    }

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(allSessions) { sessionId ->
                            val isSelected = sessionId == currentSessionId

                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) UserBubbleColor.copy(alpha = 0.2f) else CardColor
                                ),
                                border = if (isSelected) BorderStroke(1.dp, UserBubbleColor) else null,
                                onClick = {
                                    viewModel.switchSession(sessionId)
                                    scope.launch { drawerState.close() }
                                }
                            ) {
                                Text(
                                    text = "Obrolan ${sessionId.take(6)}...",
                                    fontFamily = PoppinsFont,
                                    color = TextWhite,
                                    modifier = Modifier.padding(14.dp),
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    ) {
        Scaffold(
            containerColor = BackgroundColor,
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(BackgroundColor)
                        .statusBarsPadding()
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { scope.launch { drawerState.open() } },
                        modifier = Modifier.size(40.dp).background(CardColor, CircleShape)
                    ) {
                        Icon(imageVector = Icons.Rounded.Menu, contentDescription = "Menu", tint = TextWhite, modifier = Modifier.size(20.dp))
                    }

                    Text(
                        text = "CherryAI",
                        fontFamily = PoppinsFont,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite,
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.size(40.dp))
                }
            },
            bottomBar = {
                Surface(
                    color = BackgroundColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .imePadding() // <-- INI KUNCI UTAMANYA: Mengangkat input text ke atas keyboard
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .navigationBarsPadding() // Hanya menahan tombol navigasi bawah bawaan HP
                            .padding(bottom = 16.dp, top = 8.dp, start = 24.dp, end = 24.dp)
                            .background(CardColor, CircleShape)
                            .padding(horizontal = 8.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BasicTextField(
                            value = inputText,
                            onValueChange = { viewModel.updateInputText(it) },
                            textStyle = TextStyle(fontFamily = PoppinsFont, color = TextWhite, fontSize = 15.sp),
                            cursorBrush = SolidColor(UserBubbleColor),
                            modifier = Modifier.weight(1f).padding(horizontal = 16.dp),
                            decorationBox = { innerTextField ->
                                if (inputText.isEmpty()) {
                                    Text(text = "Ketik sesuatu...", fontFamily = PoppinsFont, color = TextGray, fontSize = 15.sp)
                                }
                                innerTextField()
                            }
                        )

                        IconButton(
                            onClick = { viewModel.sendMessage() },
                            modifier = Modifier
                                .size(44.dp)
                                .background(if (inputText.isNotBlank()) UserBubbleColor else Color(0xFF5A5086), CircleShape)
                        ) {
                            Icon(imageVector = Icons.Rounded.Send, contentDescription = "Send", tint = TextWhite, modifier = Modifier.size(20.dp))
                        }
                    }
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding) // <-- Ini otomatis mengecilkan area list chat tanpa mendorong Top Bar
                    .padding(horizontal = 24.dp)
            ) {
                if (chatHistory.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(imageVector = Icons.Rounded.AutoAwesome, contentDescription = "Sparkle", tint = UserBubbleColor, modifier = Modifier.size(48.dp))
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Halo! Aku CherryAI.", fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, color = TextWhite, fontSize = 24.sp)
                        Spacer(modifier = Modifier.height(32.dp))
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(24.dp),
                            colors = CardDefaults.cardColors(containerColor = CardColor.copy(alpha = 0.6f)),
                            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f))
                        ) {
                            Column(modifier = Modifier.padding(24.dp)) {
                                Text("Rekomendasi Harianmu ✨", fontFamily = PoppinsFont, fontWeight = FontWeight.Bold, color = TextWhite, fontSize = 16.sp)
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    "Tidurmu semalam baru dimulai pukul 01.30. Coba targetkan jam 22.30 malam ini ya. Aku mendeteksi sedikit pola stres akhir-akhir ini. Gimana kalau malam ini ditemani musik relaksasi dan teknik napas 4-7-8?",
                                    fontFamily = PoppinsFont, color = TextGray, fontSize = 14.sp, lineHeight = 22.sp
                                )
                            }
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(chatHistory) { message ->
                            val isUser = message.isFromUser
                            val bubbleShape = if (isUser) {
                                RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomStart = 20.dp, bottomEnd = 4.dp)
                            } else {
                                RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomStart = 4.dp, bottomEnd = 20.dp)
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
                            ) {
                                Box(
                                    modifier = Modifier
                                        .widthIn(max = 280.dp)
                                        .background(color = if (isUser) UserBubbleColor else CardColor, shape = bubbleShape)
                                        .padding(horizontal = 16.dp, vertical = 12.dp)
                                ) {
                                    Text(
                                        text = formatMarkdownText(message.text),
                                        color = TextWhite, fontFamily = PoppinsFont, fontSize = 14.sp, lineHeight = 22.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun formatMarkdownText(text: String): AnnotatedString {
    return buildAnnotatedString {
        val parts = text.split("**")
        for (i in parts.indices) {
            if (i % 2 != 0) {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(parts[i])
                }
            } else {
                append(parts[i])
            }
        }
    }
}