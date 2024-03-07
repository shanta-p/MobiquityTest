package com.example.mealdealapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mealdealapp.R
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage

/**
 * Created by shanta on 5/3/24
 */

@Composable
fun DetailsScreen(
    paddingValues: PaddingValues,
    title: String ,
    price: String ,
    imageUrl: String? ,
){
        Column(
            modifier = Modifier
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(8.dp))

            CoilImage(
                modifier = Modifier.size(width = 350.dp, height = 550.dp),
                imageModel = { imageUrl },
                previewPlaceholder = painterResource(id = R.drawable.placeholder),
                failure = {
                    Image(painter = painterResource(id = R.drawable.placeholder),
                        contentDescription = null)
                },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Fit
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = title,
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp)

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = price,
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp)

            Spacer(modifier = Modifier.height(8.dp))

        }
    }

