package utfpr.marcomendes.imc_compose.view

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import utfpr.marcomendes.imc_compose.model.ImcViewModel
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
fun IMCScreen(
    modifier: Modifier = Modifier,
    viewModel: ImcViewModel = viewModel(), ) {

    val focusManager = LocalFocusManager.current

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
                peso = viewModel.peso,
                onPesoChange = { viewModel.onPesoChanged(it) },
                altura = viewModel.altura,
                onAlturaChange = { viewModel.onAlturaChanged((it)) }
            )
            ButtonsPanel(
                onCalcularClick = { viewModel.calcularIMC() },
                onLimparClick = {
                    viewModel.limpar()
                    focusManager.clearFocus()
                }

            )
            ResultPanel(imc = viewModel.imc)
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
                    if (it.matches(Regex("""^\d*[,.]?\d*$"""))) {
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
                    if (it.matches(Regex("""^\d*[,.]?\d*$"""))) {
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
