@file:OptIn(ExperimentalMaterial3Api::class)

package com.john.nycschools.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.gson.Gson
import com.john.nycschools.R
import com.john.nycschools.models.School
import com.john.nycschools.models.SchoolCollection
import com.john.nycschools.ui.AppTopAppBar
import com.john.nycschools.ui.ErrorScreen
import com.john.nycschools.ui.LoadingScreen
import com.john.nycschools.ui.components.AppDivider
import com.john.nycschools.ui.navigation.NavigationDestination
import com.john.nycschools.ui.theme.md_theme_light_onPrimary
import com.john.nycschools.ui.theme.md_theme_light_onPrimaryContainer
import com.john.nycschools.ui.theme.md_theme_light_onSecondary
import com.john.nycschools.ui.theme.md_theme_light_onSecondaryContainer
import com.john.nycschools.ui.theme.md_theme_light_primaryContainer
import com.john.nycschools.ui.theme.md_theme_light_secondary
import com.john.nycschools.ui.theme.md_theme_light_secondaryContainer
import com.john.nycschools.utils.isNetworkAvailable
import com.john.nycschools.utils.mirroringIcon
import com.john.nycschools.ui.components.offsetGradientBackground

private val HighlightCardWidth = 220.dp
private val HighlightCardPadding = 16.dp

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

private val Density.cardWidthWithPaddingPx
    get() = (HighlightCardWidth + HighlightCardPadding).toPx()


@Composable
fun HomeScreen(
    navigateToDetails: (String) -> Unit,
    navigateToMoreSchools: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            AppTopAppBar(
                title = stringResource(id = HomeDestination.titleRes),
                canNavigateBack = false
            )
        }
    ) { innerPadding ->
        //check if the internet is available
        viewModel.networkAvailable = isNetworkAvailable(LocalContext.current)
        HomeSchoolFeed(
            uiState = viewModel.homeUiState,
            navigateToDetails = navigateToDetails,
            navigateToMoreSchools = navigateToMoreSchools,
            modifier = Modifier.padding(innerPadding)
        )
    }
}


@Composable
private fun HomeSchoolFeed(
    uiState: HomeUiState,
    navigateToDetails: (String) -> Unit,
    navigateToMoreSchools: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        is HomeUiState.Loading -> LoadingScreen(
            modifier
                .fillMaxSize()
                .size(200.dp)
        )

        is HomeUiState.Success ->
            HomeSchoolList(
                uiState.schools,
                navigateToDetails,
                navigateToMoreSchools,
                modifier
            )

        is HomeUiState.Error ->
            ErrorScreen(
                modifier = modifier,
                uiState.message
            )
    }
}

@Composable
private fun HomeSchoolList(
    schoolCollections: List<SchoolCollection>,
    navigateToDetails: (String) -> Unit,
    navigateToMoreSchools: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
    ) {
        Box {
            LazyColumn {
                itemsIndexed(schoolCollections) { index, schoolCollection ->
                    if (index > 0) {
                        AppDivider(thickness = 2.dp)
                    }

                    SchoolCollectionItem(
                        index = index,
                        schoolCollection = schoolCollection,
                        navigateToDetails = navigateToDetails,
                        navigateToMoreSchools = navigateToMoreSchools,
                        modifier = modifier
                    )
                }
            }
        }
    }
}


@Composable
private fun SchoolCollectionItem(
    index: Int,
    schoolCollection: SchoolCollection,
    navigateToDetails: (String) -> Unit,
    navigateToMoreSchools: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .heightIn(min = 56.dp)
                .padding(start = 24.dp)
        ) {
            Text(
                text = schoolCollection.type.title,
                style = MaterialTheme.typography.h6,
                color = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.Start)
            )
            IconButton(
                onClick = {
                    navigateToMoreSchools(schoolCollection.type.type)
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = mirroringIcon(
                        ltrIcon = Icons.Outlined.ArrowForward,
                        rtlIcon = Icons.Outlined.ArrowBack
                    ),
                    tint = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                    contentDescription = null
                )
            }
        }
        SchoolRowItems(
            index = index,
            schoolCollection.schools,
            navigateToDetails = navigateToDetails
        )
    }
}

@Composable
private fun SchoolRowItems(
    index: Int,
    schools: List<School>,
    navigateToDetails: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val rowState = rememberLazyListState()
    val cardWidthWithPaddingPx = with(LocalDensity.current) { cardWidthWithPaddingPx }

    val scrollProvider = {
        // Simple calculation of scroll distance for homogenous item types with the same width.
        val offsetFromStart = cardWidthWithPaddingPx * rowState.firstVisibleItemIndex
        offsetFromStart + rowState.firstVisibleItemScrollOffset
    }

    val gradient = when ((index / 2) % 2) {
        0 -> listOf(
            md_theme_light_onPrimary,
            md_theme_light_primaryContainer,
            md_theme_light_onPrimaryContainer
        )

        else -> listOf(
            md_theme_light_secondary,
            md_theme_light_onSecondary,
            md_theme_light_secondaryContainer,
            md_theme_light_onSecondaryContainer
        )
    }

    LazyRow(
        state = rowState,
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(start = 24.dp, end = 24.dp)
    ) {
        itemsIndexed(schools) { index, school ->
            SchoolRowItem(
                index = index,
                school = school,
                gradient = gradient,
                scrollProvider = scrollProvider,
                onCardClick = {
                    navigateToDetails(Gson().toJson(school))
                }
            )
        }
    }
}


@Composable
private fun SchoolRowItem(
    index: Int = 0,
    school: School,
    gradient: List<Color>,
    scrollProvider: () -> Float,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .size(
                width = HighlightCardWidth,
                height = 350.dp
            )
            .padding(bottom = 16.dp),
        onClick = onCardClick,
    ) {
        Column(
            modifier = modifier
                .clickable(onClick = onCardClick)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                        .offsetGradientBackground(
                            colors = gradient,
                            width = {
                                // The Cards show a gradient which spans 6 cards and scrolls with parallax.
                                6 * cardWidthWithPaddingPx
                            },
                            offset = {
                                val left = index * cardWidthWithPaddingPx
                                val gradientOffset = left - (scrollProvider() / 3f)
                                gradientOffset
                            }
                        )
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = school.school_name,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                color = androidx.compose.material3.MaterialTheme.colorScheme.outline,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = school.city.plus(" , ")
                    .plus(school.state_code)
                    .plus(" ")
                    .plus(school.zip),
                style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                color = androidx.compose.material3.MaterialTheme.colorScheme.outline,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Text(
                text = school.phone_number,
                style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                color = androidx.compose.material3.MaterialTheme.colorScheme.outline,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}
