package com.example.mealdealapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mealdealapp.ui.DetailsScreen
import com.example.mealdealapp.ui.HomeScreen
import com.example.mealdealapp.ui.theme.MealDealAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel by viewModels<MainViewModel>()
            val state = viewModel.uiState
            val scope = rememberCoroutineScope()
            val snackbarHostState = remember {
                SnackbarHostState()
            }
            val navController = rememberNavController()

            MealDealAppTheme {

                if (state.hasError) {
                    LaunchedEffect(snackbarHostState) {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = state.errorMessage,
                                actionLabel = "Retry"
                            ).run {
                                when (this) {
                                    SnackbarResult.ActionPerformed -> {
                                        viewModel.loadItems()
                                        viewModel.dismissSnackBar()
                                    }

                                    else -> {}
                                }
                            }
                        }
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            CenterAlignedTopAppBar(
                                modifier = Modifier.shadow(
                                    elevation = 5.dp,
                                    spotColor = Color.DarkGray,),
                                title = {
                                    Text(
                                        text = "Meal Deal",
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 22.sp
                                    )
                                },
                            )
                        },

                        snackbarHost = {
                            SnackbarHost (hostState = snackbarHostState)
                        }

                        ) { paddingValues ->

                        Loading(isLoading = state.isLoading)

                        NavHost(
                            modifier = Modifier.navigationBarsPadding(),
                            navController = navController,
                            startDestination = "home"
                        ) {
                            composable(route = "home",
                                enterTransition = { fadeIn(animationSpec = tween(500)) },
                                exitTransition = { fadeOut(animationSpec = tween(500)) }
                            ) {
                                HomeScreen(
                                    paddingValues = paddingValues,
                                    items = viewModel.mealDealList(),
                                    onItemClicked = { event ->
                                        (event as Event.ViewItemDetails).let {
                                            navController.navigate("detail?id=${event.id}&categoryId=${event.categoryId}")
                                        }

                                    })
                            }
                            composable(route = "detail?id={id}&categoryId={categoryId}",
                                enterTransition = {
                                    slideIntoContainer(
                                        towards = AnimatedContentTransitionScope.SlideDirection.Start,
                                        animationSpec = tween(500)
                                    )
                                },
                                exitTransition = {
                                    slideOutOfContainer(
                                        towards = AnimatedContentTransitionScope.SlideDirection.End,
                                        animationSpec = tween(500)
                                    )
                                }
                            ) { backStackEntry ->
                                backStackEntry.arguments?.let {
                                    val item = viewModel.fetchItem(
                                        it.getString("id"),
                                        it.getString("categoryId")
                                    )

                                    DetailsScreen(
                                        paddingValues = paddingValues,
                                        title = item.title,
                                        imageUrl = item.imageUrl,
                                        price = item.price,
                                        )
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Loading(isLoading: Boolean){
    if(isLoading){
        Box(modifier = Modifier
            .fillMaxSize(),
            contentAlignment = Alignment.Center){

            CircularProgressIndicator()
        }
    }
}
