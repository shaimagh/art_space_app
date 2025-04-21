package com.example.art_space_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.art_space_app.ui.theme.Art_space_appTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Art_space_appTheme {
                ArtGalleryScreen()
            }
        }
    }
}
@Composable
fun ArtGalleryScreen() {
    val artworks = listOf(
        Artwork(R.drawable.flower, "Still Life of Blue Rose and Other Flowers", "Owen Scott", "2021"),
        Artwork(R.drawable.flower2, "Sunset Over the Mountains", "Emily Carter", "2019"),
        Artwork(R.drawable.flower3, "Abstract Thoughts", "Liam Smith", "2020")
    )

    var currentIndex by remember { mutableStateOf(0) }
    val currentArtwork = artworks[currentIndex]

    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp >= 600
    val imageSize = if (isTablet) 400.dp else 300.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .width(imageSize)
        ) {
            Card(
                elevation = CardDefaults.cardElevation(6.dp),
                modifier = Modifier.size(imageSize)
            ) {
                Image(
                    painter = painterResource(id = currentArtwork.imageRes),
                    contentDescription = currentArtwork.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .padding(8.dp),
            ) {
                Column {
                    Text(
                        text = currentArtwork.title,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        buildAnnotatedString {
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append(currentArtwork.artist)
                            }
                            append(" ")
                            withStyle(SpanStyle(fontStyle = FontStyle.Italic)) {
                                append("(${currentArtwork.year})")
                            }
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 30.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    if (currentIndex > 0) currentIndex--
                    else currentIndex = artworks.size - 1 // boucle
                },
                modifier = Modifier.width(120.dp)
            ) {
                Text("Previous")
            }
            Button(
                onClick = {
                    if (currentIndex < artworks.size - 1) currentIndex++
                    else currentIndex = 0 // boucle
                },
                modifier = Modifier.width(120.dp)
            ) {
                Text("Next")
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Art_space_appTheme {
        ArtGalleryScreen()
    }
}

data class Artwork(
    val imageRes: Int,
    val title: String,
    val artist: String,
    val year: String
)
