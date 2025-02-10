package com.example.weatherappjc

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.WindowCompat
import com.example.weatherappjc.ui.theme.BlueJC
import com.example.weatherappjc.ui.theme.DarkBlueJC
import com.example.weatherappjc.ui.theme.OrangeJC
import com.example.weatherappjc.ui.theme.WeatherAppJCTheme

class IntroActivity2 : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            setContent {
                GoToMain(activity = this)
            }
        }
    }
}


@Composable
fun GoToMain(activity: ComponentActivity){

    Box(modifier = Modifier
        .fillMaxSize()
        .paint(
            painterResource(R.drawable.gradient_dark_blue),
            contentScale = ContentScale.FillBounds
        )){
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top) {
            Spacer(modifier = Modifier.height(60.dp))
            Image(painter = painterResource(R.drawable.sun_cloud_day),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(360.dp))
            Spacer(modifier = Modifier.height(51.dp))
            Text(text = "Tolebi's",
                fontSize = 64.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif
            )
            Text(text = "Weather App",
                fontSize = 54.sp,
                color = OrangeJC,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Cursive)
            Spacer(modifier = Modifier.height(51.dp))
            Button(onClick = {
                val intent = Intent(activity, MainActivity::class.java)
                activity.startActivity(intent)
            },
                modifier = Modifier.size(297.dp, 62.dp),
                colors = ButtonDefaults.buttonColors(OrangeJC)) {
                Text(text = "Get Start",
                    fontSize = 24.sp,
                    color = DarkBlueJC)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IntroPreview(){
    WeatherAppJCTheme {
        GoToMain(activity = FakeActivity())
    }
}

class FakeActivity : ComponentActivity() {

}
