package com.john.nycschools.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.john.nycschools.data.SchoolRepository
import com.john.nycschools.models.SchoolBorough
import com.john.nycschools.models.SchoolCollection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: SchoolRepository
) : ViewModel() {

    var homeUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set
    var networkAvailable: Boolean by mutableStateOf(false)

    init {
        getAllSchools()
    }

    private fun getAllSchools() {
        viewModelScope.launch {
            val schoolCollections = mutableListOf<SchoolCollection>()
            homeUiState = HomeUiState.Loading
            homeUiState = try {
                repository.loadAllSchools(networkAvailable)
                    .flowOn(Dispatchers.Default)
                    .collect { schools ->
                        schools.groupBy {
                            it.boro
                        }.forEach { entry ->
                            schoolCollections.add(
                                SchoolCollection(
                                    type = SchoolBorough.fromString(entry.key),
                                    schools = entry.value
                                )
                            )
                        }
                    }
                HomeUiState.Success(schoolCollections)
            } catch (e: IOException) {
                HomeUiState.Error(e.localizedMessage)
            } catch (e: HttpException) {
                HomeUiState.Error(e.localizedMessage)
            }
        }
    }
}