package com.john.nycschools.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.john.nycschools.R
import com.john.nycschools.ui.details.DetailsScreen
import com.john.nycschools.ui.details.DetailsScreenDestination
import com.john.nycschools.ui.home.HomeDestination
import com.john.nycschools.ui.home.HomeScreen
import com.john.nycschools.ui.list.SchoolListScreen
import com.john.nycschools.ui.list.SchoolListScreenDestination
import com.john.nycschools.ui.theme.NycSchoolsTheme

/**
 * Handles the navigations throughout the screens Home, Details, and List screens
 *
 */

@Composable
fun NycSchoolApp() {
    NycSchoolsTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = HomeDestination.route,
            modifier = Modifier
        ) {
            composable(
                route = HomeDestination.route
            ) {
                HomeScreen(
                    navigateToDetails = { school: String ->
                        navController.navigate(
                            "${DetailsScreenDestination.route}?schoolObj=${school}"
                        )
                    },
                    navigateToMoreSchools = { boroObj: String ->
                        navController.navigate(
                            "${SchoolListScreenDestination.route}?boroObj=${boroObj}"
                        )
                    }
                )
            }

            composable(
                route = DetailsScreenDestination.routeWithArgs,
                arguments = listOf(
                    navArgument(DetailsScreenDestination.schoolObj) {
                        type = NavType.StringType
                        defaultValue = ""
                    }
                )
            ) {
                DetailsScreen(navigateBack = {
                    navController.navigateUp()
                })
            }

            composable(
                route = SchoolListScreenDestination.routeWithArgs,
                arguments = listOf(
                    navArgument(SchoolListScreenDestination.boroObj) {
                        type = NavType.StringType
                        defaultValue = ""
                    }
                )
            ) {
                SchoolListScreen(
                    navigateBack = {
                        navController.navigateUp()
                    },
                    navigateToDetails = { school: String ->
                        navController.navigate(
                            "${DetailsScreenDestination.route}?schoolObj=${school}"
                        )
                    }
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {}
) {
    if (canNavigateBack) {
        CenterAlignedTopAppBar(title = {
            Text(title)
        },
            modifier = modifier,
            navigationIcon = {
                IconButton(
                    onClick = navigateUp
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back)
                    )
                }
            }
        )
    } else {
        CenterAlignedTopAppBar(
            title = {
                Text(title)
            },
            modifier = modifier
        )
    }
}

@Composable
fun LoadingScreen(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.progress_bar),
        contentDescription = stringResource(id = R.string.loading),
        modifier = modifier
    )
}


@Composable
fun ErrorScreen(modifier: Modifier = Modifier, error: String) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = error)
    }
}