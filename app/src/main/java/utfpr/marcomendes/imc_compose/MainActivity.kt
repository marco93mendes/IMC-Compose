package utfpr.marcomendes.imc_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import utfpr.marcomendes.imc_compose.ui.theme.IMCComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IMCComposeTheme {
                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
                    IMCScreen( modifier = Modifier.padding(innerPadding) )
                }
            }
        }
    }
}

@Composable
fun IMCScreen(modifier: Modifier = Modifier) {
    var peso by rememberSaveable { mutableStateOf("") }
    var altura by rememberSaveable { mutableStateOf("") }
    var imc by rememberSaveable { mutableStateOf("0.0") }
    val focusManager = LocalFocusManager.current

    val btCalcular: () -> Unit = {
        val p = peso.replace(',', '.').toDoubleOrNull()
        val a = altura.replace(',', '.').toDoubleOrNull()
        if (p != null && a != null) {
            val res = (p / (a * a))
            imc = String.format("%.2f", res)
        }
    }

    val btLimpar: () -> Unit = {
        peso = ""
        altura = ""
        imc = "0.0"
        focusManager.clearFocus()
    }

    Box(
        modifier = modifier.fillMaxSize().padding(top = 32.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(256.dp)
        ) {
            InputPanel(
                peso = peso,
                onPesoChange = { peso = it },
                altura = altura,
                onAlturaChange = { altura = it }
            )
            ButtonsPanel(
                onCalcularClick = btCalcular,
                onLimparClick = btLimpar
            )
            ResultPanel(imc = imc)
        }
    }
}

@Composable
fun InputPanel(
    peso: String,
    onPesoChange: (String) -> Unit,
    altura: String,
    onAlturaChange: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(32.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Peso:",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.width(64.dp)
            )
            OutlinedTextField(
                value = peso,
                onValueChange = {
                    if (it.matches(Regex("^\\d*[,.]?\\d*\$"))) {
                        onPesoChange(it)
                    }
                },
                label = { Text("kg") },
                modifier = Modifier.width(160.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Altura:",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.width(64.dp)
            )
            OutlinedTextField(
                value = altura,
                onValueChange = {
                    if (it.matches(Regex("^\\d*[,.]?\\d*\$"))) {
                        onAlturaChange(it)
                    }
                },
                label = { Text("mt") },
                modifier = Modifier.width(160.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
    }
}


@Composable
fun ButtonsPanel(
    onCalcularClick: () -> Unit,
    onLimparClick: () -> Unit
) {
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = onCalcularClick,
            modifier = Modifier.weight(1f)
        ) {
            Text(text = "Calcular")
        }

        Button(
            onClick = onLimparClick,
            modifier = Modifier.weight(1f)
        ) {
            Text(text = "Limpar")
        }
    }
}

@Composable
fun ResultPanel(imc: String) {
    Row (
        modifier = Modifier
            .background(Color.LightGray)
            .padding(16.dp),
    ) {
        Text(
            text = "IMC = ",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = imc,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun IMCScreenPreview() {
    IMCComposeTheme {
        IMCScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun InputPanelPreview() {
    IMCComposeTheme {
        InputPanel(
            peso = "90",
            onPesoChange = {},
            altura = "1.95",
            onAlturaChange = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonsPanelPreview() {
    IMCComposeTheme {
        ButtonsPanel(
            onCalcularClick = {},
            onLimparClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ResultPanelPreview() {
    IMCComposeTheme {
        ResultPanel(imc = "23.67")
    }
}
