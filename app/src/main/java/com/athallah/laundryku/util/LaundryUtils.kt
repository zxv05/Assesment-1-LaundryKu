package com.athallah.laundryku.util

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.athallah.laundryku.R
import com.athallah.laundryku.model.LaundryService
import java.text.NumberFormat
import java.util.Locale

@Composable
fun getLaundryServices(): List<LaundryService> {
    return listOf(
        LaundryService(stringResource(R.string.service_regular), 7000),
        LaundryService(stringResource(R.string.service_express), 10000)
    )
}

fun calculateTotalPrice(
    weightInput: String,
    selectedServiceName: String,
    services: List<LaundryService>
): Int {
    val weight = weightInput.toIntOrNull() ?: 0
    val selectedService = services.firstOrNull { it.name == selectedServiceName }
    return weight * (selectedService?.pricePerKg ?: 0)
}

fun formatCurrency(amount: Int): String {
    return NumberFormat.getCurrencyInstance(Locale("id", "ID")).format(amount)
}

fun shareResult(context: Context, message: String) {
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    context.startActivity(Intent.createChooser(sendIntent, null))
}
