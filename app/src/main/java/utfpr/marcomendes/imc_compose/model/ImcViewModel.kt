package utfpr.marcomendes.imc_compose.model

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf

class ImcViewModel: ViewModel() {
    var peso by mutableStateOf("")
        private set
    var altura by mutableStateOf("")
        private set
    var imc by mutableStateOf("0.0")
        private set

    fun onPesoChanged(value: String) {
        peso = value
    }

    fun onAlturaChanged(value: String) {
        altura = value
    }

    fun calcularIMC() {
        val p = peso.replace(',', '.').toDoubleOrNull()
        val a = altura.replace(',', '.').toDoubleOrNull()
        if (p != null && a != null) {
            val res = (p / (a * a))
            imc = "%.2f".format(res)
        }
    }

    fun limpar() {
        peso = ""
        altura = ""
        imc = "0.0"
    }

}