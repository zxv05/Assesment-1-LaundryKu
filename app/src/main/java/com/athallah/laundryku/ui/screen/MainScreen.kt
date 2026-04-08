package com.athallah.laundryku.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.athallah.laundryku.R
import com.athallah.laundryku.model.LaundryResult
import com.athallah.laundryku.navigation.Screen
import com.athallah.laundryku.ui.component.ErrorIcon
import com.athallah.laundryku.ui.component.ErrorText
import com.athallah.laundryku.ui.component.LaundryBanner
import com.athallah.laundryku.ui.component.ResultCard
import com.athallah.laundryku.util.calculateTotalPrice
import com.athallah.laundryku.util.formatCurrency
import com.athallah.laundryku.util.getLaundryServices
import com.athallah.laundryku.util.shareResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    val context = LocalContext.current
    val laundryServices = getLaundryServices()
    val regularService = laundryServices[0].name
    val expressService = laundryServices[1].name

    var customerName by rememberSaveable { mutableStateOf("") }
    var weightInput by rememberSaveable { mutableStateOf("") }
    var selectedLaundryType by rememberSaveable { mutableStateOf(regularService) }
    var isNameError by rememberSaveable { mutableStateOf(false) }
    var isWeightError by rememberSaveable { mutableStateOf(false) }
    var totalResult by rememberSaveable { mutableStateOf<Int?>(null) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.About.route) }) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = stringResource(R.string.about_title)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFE0F2FE), Color(0xFFF8FAFC))
                    )
                )
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            LaundryBanner(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp)
            )

            Text(
                text = stringResource(R.string.main_intro),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            OutlinedTextField(
                value = customerName,
                onValueChange = {
                    customerName = it
                    isNameError = false
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.input_name)) },
                singleLine = true,
                isError = isNameError,
                supportingText = {
                    ErrorText(isError = isNameError, text = stringResource(R.string.error_name))
                },
                trailingIcon = {
                    ErrorIcon(isError = isNameError)
                }
            )

            OutlinedTextField(
                value = weightInput,
                onValueChange = {
                    weightInput = it.filter(Char::isDigit)
                    isWeightError = false
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.input_weight)) },
                suffix = { Text(stringResource(R.string.weight_unit)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                isError = isWeightError,
                supportingText = {
                    ErrorText(isError = isWeightError, text = stringResource(R.string.error_weight))
                },
                trailingIcon = {
                    ErrorIcon(isError = isWeightError)
                }
            )

            Text(
                text = stringResource(R.string.choose_service),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedLaundryType == regularService,
                    onClick = { selectedLaundryType = regularService }
                )
                Text(
                    text = stringResource(
                        R.string.service_option_template,
                        regularService,
                        formatCurrency(7000)
                    )
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedLaundryType == expressService,
                    onClick = { selectedLaundryType = expressService }
                )
                Text(
                    text = stringResource(
                        R.string.service_option_template,
                        expressService,
                        formatCurrency(10000)
                    )
                )
            }

            Button(
                onClick = {
                    isNameError = customerName.isBlank()
                    isWeightError = (weightInput.toIntOrNull() ?: 0) <= 0

                    if (isNameError || isWeightError) {
                        totalResult = null
                        return@Button
                    }

                    totalResult = calculateTotalPrice(
                        weightInput = weightInput,
                        selectedServiceName = selectedLaundryType,
                        services = laundryServices
                    )
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(stringResource(R.string.calculate_button))
            }

            if (totalResult != null) {
                ResultCard(
                    result = LaundryResult(
                        customerName = customerName,
                        serviceName = selectedLaundryType,
                        weightInput = weightInput,
                        totalPrice = totalResult ?: 0
                    )
                )

                Button(
                    onClick = {
                        val shareText = context.getString(
                            R.string.share_template,
                            customerName,
                            selectedLaundryType,
                            weightInput,
                            formatCurrency(totalResult ?: 0)
                        )
                        shareResult(context = context, message = shareText)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(R.string.share_button))
                }
            }
        }
    }
}
