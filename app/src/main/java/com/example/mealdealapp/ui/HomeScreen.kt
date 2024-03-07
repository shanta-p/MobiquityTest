package com.example.mealdealapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mealdealapp.Event

/**
 * Created by shanta on 4/3/24
 */

@Composable
fun HomeScreen(
    paddingValues: PaddingValues ,
    items: List<MealDealItem> ,
    onItemClicked: (Event)-> Unit
) {
    Box(modifier = Modifier.padding(paddingValues)

    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            sectionedList(
                items = items,
                onItemClick = onItemClicked
            )
        }
    }
}




