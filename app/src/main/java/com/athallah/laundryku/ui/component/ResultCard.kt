package com.athallah.laundryku.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.athallah.laundryku.R
import com.athallah.laundryku.model.LaundryResult
import com.athallah.laundryku.util.formatCurrency

@Composable
fun ResultCard(result: LaundryResult) {
    Card {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(R.string.result_title),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(stringResource(R.string.result_name, result.customerName))
            Text(stringResource(R.string.result_service, result.serviceName))
            Text(stringResource(R.string.result_weight, result.weightInput))
            Text(
                text = stringResource(R.string.result_total, formatCurrency(result.totalPrice)),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
