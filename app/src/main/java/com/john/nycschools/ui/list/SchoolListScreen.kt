package com.john.nycschools.ui.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.gson.Gson
import com.john.nycschools.R
import com.john.nycschools.models.School
import com.john.nycschools.models.SchoolCollection
import com.john.nycschools.ui.AppTopAppBar
import com.john.nycschools.ui.ErrorScreen
import com.john.nycschools.ui.LoadingScreen
import com.john.nycschools.ui.navigation.NavigationDestination


object SchoolListScreenDestination : NavigationDestination {
    override val route: String = "school_list"
    override val titleRes = R.string.school_list
    const val boroObj = "boroObj"
    val routeWithArgs = "$route?boroObj={$boroObj}"
}

@Composable
fun SchoolListScreen(
    navigateBack: () -> Unit,
    navigateToDetails: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SchoolListViewModel = hiltViewModel()
) {
    when (val schoolListUiState = viewModel.schoolListUiState) {
        is SchoolListUiState.Loading -> LoadingScreen(
            modifier
                .fillMaxSize()
                .size(200.dp)
        )

        is SchoolListUiState.Success ->
            SchoolListScreenMainBody(
                schoolListUiState.collection,
                navigateBack,
                navigateToDetails
            )

        is SchoolListUiState.Error ->
            ErrorScreen(
                modifier = modifier,
                schoolListUiState.message
            )
    }
}

@Composable
private fun SchoolListScreenMainBody(
    schoolCollection: SchoolCollection,
    navigateBack: () -> Unit,
    navigateToDetails: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            AppTopAppBar(
                title = stringResource(
                    id = R.string.school_list_title,
                    schoolCollection.type.title,
                    schoolCollection.schools.size
                ),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        ListScreenItems(
            schools = schoolCollection.schools,
            navigateToDetails = navigateToDetails,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun ListScreenItems(
    schools: List<School>,
    navigateToDetails: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_medium))
    ) {
        items(
            items = schools,
            key = { school ->
                school.dbn
            }
        ) { school ->
            ListScreenSchoolItem(
                school = school,
                navigateToDetails = navigateToDetails,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListScreenSchoolItem(
    school: School,
    navigateToDetails: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        onClick = {
            navigateToDetails(Gson().toJson(school))
        }
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            Text(
                text = school.school_name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = dimensionResource(id = R.dimen.padding_medium),
                        start = dimensionResource(id = R.dimen.padding_medium),
                        end = dimensionResource(id = R.dimen.padding_medium)
                    ),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left
            )
            Text(
                text = school.city.plus(" , ")
                    .plus(school.state_code)
                    .plus(" ")
                    .plus(school.zip),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(
                    top = dimensionResource(id = R.dimen.padding_medium),
                    start = dimensionResource(id = R.dimen.padding_medium)
                )
            )
            Text(
                text = school.phone_number,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.padding_medium),
                    bottom = dimensionResource(id = R.dimen.padding_medium)
                )
            )
        }
    }
}