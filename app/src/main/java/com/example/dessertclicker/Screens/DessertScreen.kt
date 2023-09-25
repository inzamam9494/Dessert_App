package com.example.dessertclicker.Screens

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dessertclicker.R
import com.example.dessertclicker.model.DessertViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DessertScreen(
    modifier: Modifier = Modifier
) {

    val vm:DessertViewModel = viewModel()
    val uiState = vm.uiState.collectAsState()

    Scaffold(topBar = {
        val internetContext = LocalContext.current
        TopBarDessertScreen(
            onShareButtonClicked = {
                shareSoldDessertInfo(
                    intentContext = internetContext,
                    dessertSold = uiState.value.dessertCount,
                    revenue = uiState.value.revenue
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
        )
    }) {

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            DessertClickerScreen()
        }
    }
}

@Composable
fun TopBarDessertScreen(
    modifier: Modifier = Modifier,
    onShareButtonClicked: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Dessert App",
            modifier = Modifier.padding(10.dp),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge,
        )
        IconButton(
            onClick = onShareButtonClicked,
            modifier = Modifier.padding(10.dp),
        ) {
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = "share",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }

}

@Composable
fun DessertClickerScreen(
    modifier: Modifier = Modifier
) {
    val vm:DessertViewModel = viewModel()
    val uiState = vm.uiState.collectAsState()
    Box() {
        Image(
            painter = painterResource(id = R.drawable.bakery_back),
            contentDescription = "Dessert Background",
            contentScale = ContentScale.Crop
        )
        Column {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cupcake),
                    contentDescription = "cupcake",
                    modifier = Modifier
                        .height(150.dp)
                        .width(150.dp)
                        .clickable { vm.dessertCalculate() }
                )
            }
            TransactionInfo(sold = uiState.value.dessertCount, revenue = uiState.value.revenue)
        }

    }
}

@Composable
fun TransactionInfo(
    modifier: Modifier = Modifier,
    sold: Int,
    revenue: Int
) {
    Card() {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row {
                Text(
                    text = "Dessert Sold",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.weight(0.1f))
                Text(
                    text = stringResource(id = R.string.sold, sold),
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Row {
                Text(
                    text = "Total Revenue",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.weight(0.1f))
                Text(
                    text = stringResource(id = R.string.revenue, revenue),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }

}


private fun shareSoldDessertInfo(intentContext: Context, dessertSold:Int, revenue: Int) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            intentContext.getString(R.string.share_text)
        )
        type = "text/plan"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)

    try {
        ContextCompat.startActivities(intentContext, arrayOf(shareIntent), null)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(
            intentContext,
            intentContext.getString(R.string.sharing_not_available),
            Toast.LENGTH_LONG
        ).show()
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewDessertScreen() {
    MaterialTheme {
        TransactionInfo(sold = 0, revenue = 0)
    }
}