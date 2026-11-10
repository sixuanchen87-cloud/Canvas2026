package tw.edu.pu.o365st.s1141837.canvas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.res.imageResource
import kotlinx.coroutines.delay
import tw.edu.pu.o365st.s1141837.canvas.ui.theme.CanvasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CanvasTheme {
                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
                    CanvasScreen(
                        modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}



@Composable
fun CanvasScreen(modifier: Modifier = Modifier) {

    var CanvasW by remember { mutableStateOf(0f) }
    var CanvasH by remember { mutableStateOf(0f) }

    var minionsX by remember { mutableStateOf(0f) }
    var minionsY by remember { mutableStateOf(0f) }

    var gameOver by remember { mutableStateOf(false) }

    val minionsImage: ImageBitmap =
        ImageBitmap.imageResource(id = R.drawable.minions)

    LaunchedEffect(gameOver) {
        while (!gameOver) {
            delay(80L) // 暫停 1 秒 (1000 毫秒)
            minionsX +=5
            minionsY +=10
            if (CanvasW > 0f && CanvasH > 0f) {
                if (minionsX >= CanvasW || minionsY >= CanvasH) {
                    minionsX = 0f
                    minionsY = 0f
                }
            }
        }
    }



    Box(

    ){Canvas(
        modifier = Modifier.fillMaxSize()

    ){

        val minionsWidth = minionsImage.width
        val minionsHeight = minionsImage.height

        val offsetX = (size.width - minionsWidth) / 2f
        val offsetY = (size.height - minionsHeight) / 2f

        translate(minionsX, minionsY) {
            drawImage(minionsImage)
        }


        drawCircle(
            Color.Magenta,
            100f,
            Offset(300f, 800f)
        )
        drawRect(
            Color.Blue,
            Offset(300f, 200f),
            Size(200f, 200f),
            style = Stroke(width = 10f) // 使用 style 控制外框樣式
        )

        CanvasW = size.width
        CanvasH = size.height

        drawLine(
            Color.Red,
            Offset(200f, 800f),
            Offset(size.width, size.height),
            strokeWidth = 10f
        )

    }
        Column{
            Text("畫布的寬度 $CanvasW,長度 $CanvasH")
            Button   (
                onClick = { gameOver = !gameOver}
            )
            {
                if (gameOver){Text("繼續遊戲")}
                else{Text ("停止遊戲")}
            }
        }
    }

}
