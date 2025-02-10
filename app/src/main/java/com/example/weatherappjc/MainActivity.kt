package com.example.weatherappjc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModel
import com.example.weatherappjc.ui.theme.WeatherAppJCTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherappjc.ui.theme.BlueJC
import com.example.weatherappjc.ui.theme.DarkBlueJC

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            WeatherScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherPreview(){
    WeatherAppJCTheme {
        WeatherScreen()
    }
}

@Composable
fun WeatherCard(label: String, value: String,icon : ImageVector){
    Card(modifier = Modifier
        .padding(8.dp)
        .size(150.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ){
        Column(modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ){
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ){
                Icon(imageVector = icon, contentDescription = null,
                    tint = DarkBlueJC, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = label, fontSize = 14.sp, color = DarkBlueJC)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
                contentAlignment = Alignment.Center
            ){
                Text(text = value,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = BlueJC)
            }
        }
    }
}


@Composable
fun WeatherScreen(){
    val viewModel : WeatherVM = viewModel()
    val weatherData by viewModel.weatherData.collectAsState()
    var showWeatherData by remember { mutableStateOf(false) }
    var city by remember {
        mutableStateOf("")
    }
    val apiKey = "b09f7b9c312bb32b7a05b163b9f78db3"

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
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(46.dp))
                Text(text = "Weather App", color = Color.White, fontSize = 32.sp)
                Spacer(modifier = Modifier.height(50.dp))
                OutlinedTextField(
                    value = city,
                    onValueChange = {city = it},
                    label = {Text(
                        text = "Name of city",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(30.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.White,
                        focusedIndicatorColor = Color.White,
                        focusedLabelColor = DarkBlueJC,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    viewModel.fetchWeather(city, apiKey)
                    showWeatherData = true},
                    colors = ButtonDefaults.buttonColors(BlueJC)
                ){
                    Text(text = "Check Weather")
                }
                Spacer(modifier = Modifier.height(36.dp))
                if (showWeatherData && weatherData != null){
                    weatherData?.let {
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ){
                            WeatherCard(label = "City", value = it.name, icon = Icons.Default.Place)
                            WeatherCard(label = "Temperature", value = "${it.main.temp} °C", icon = Icons.Default.Star)
                        }
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            WeatherCard(label = "Humidity", value = "${it.main.humidity}%", icon = Icons.Default.Warning)
                            WeatherCard(label = "Description", value = it.weather[0].description, icon = Icons.Default.Info)
                        }
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            WeatherCard(label = "Wind speed", value = "${it.wind.speed} m/s", icon = Icons.Default.Warning)
                            WeatherCard(label = "Fils Like", value = "${it.main.feels_like} °C", icon = Icons.Default.Info)
                        }
                    }
                }
                else{
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ){
                        WeatherCard(label = "City", value = "...", icon = Icons.Default.Place)
                        WeatherCard(label = "Temperature", value = " °C", icon = Icons.Default.Star)
                    }
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        WeatherCard(label = "Humidity", value = "%", icon = Icons.Default.Warning)
                        WeatherCard(label = "Description", value = "nothing", icon = Icons.Default.Info)
                    }
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        WeatherCard(label = "Wind speed", value = "m/s", icon = Icons.Default.Warning)
                        WeatherCard(label = "Fils Like", value = " C", icon = Icons.Default.Star)
                    }

                }


        }
    }
}



