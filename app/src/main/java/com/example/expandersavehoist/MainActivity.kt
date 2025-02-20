package com.example.expandersavehoist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import com.example.expandersavehoist.ui.theme.ExpanderSaveHoistTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            ExpanderSaveHoistTheme () {
                Surface (
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.fillMaxSize())
                {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        ExpanderPreview(
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun ExpanderGuide(modifier: Modifier){
    val names : List<String> = List(1000) { "$it"}

    LazyColumn (modifier
        .background(Color.LightGray)
        .fillMaxSize()){


        items(items = names) { name ->
            ExpanderBox(text = name)
        }
    }
}


@Composable
fun ExpanderBox(text : String){

    var expanded by remember { mutableStateOf(false)}
    val extraPadding by animateDpAsState(
                                if (expanded) 50.dp else 0.dp,
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow
                                )
                                        )

    Box (Modifier.padding(vertical = 5.dp, horizontal = 5.dp)) {
        Row (
            Modifier
                .background(MaterialTheme.colorScheme.primary)
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            Column (Modifier.weight(1f)) {
                Text(

                    modifier = Modifier.padding(bottom = extraPadding.coerceAtLeast(0.dp)),
                    text = text, style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            ElevatedButton(
                onClick = {expanded = !expanded}
            ) {
                Text(if (expanded) ("Show less") else "Show more")
            }
        }
    }
}

@Composable
fun ElementoEntrada(modifier: Modifier = Modifier,
                    onToggleEntrada : () -> Unit){

    Column (Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
        Text(
            text = "Está pronto para o melhor app ever? "
        )
        ElevatedButton(
                onClick = {onToggleEntrada()}
        ) {
            Text( text = "Não! ")
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
    name = "GreetingPreviewDark"
)
@Composable
fun ExpanderPreview(modifier: Modifier = Modifier){
    var notEntradaApp by remember {mutableStateOf(true)}

    if (notEntradaApp){
        ElementoEntrada(
                    modifier,
                    onToggleEntrada = {notEntradaApp = !notEntradaApp}
        )
    } else{
        ExpanderGuide(modifier = modifier)
    }
}