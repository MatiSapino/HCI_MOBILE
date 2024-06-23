import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileapp.R

@Composable
fun Logo() {
    val gradientColors = listOf(Color.Black, Color.Transparent,)

    Text(
        text = "SMARTHOME",
        style = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 50.sp,
            color = Color.Transparent,
            textAlign = TextAlign.Center
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .background(brush = Brush.verticalGradient(gradientColors))
    )
}

@Composable
fun MyLogo() {
    val gradientColors = listOf(Color.Black, Color.Transparent) // Example gradient colors

    Text(
        text = "SMARTHOME",
        style = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 50.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        ),
        modifier = Modifier
            .padding(top = 10.dp)
//            .background(
//                brush = Brush.verticalGradient(
//                    colors = gradientColors,
//                    startY = 0f,
//                    endY = 100f
//                ),
//            )
            .padding(10.dp),
        textAlign = TextAlign.Center
    )
}
