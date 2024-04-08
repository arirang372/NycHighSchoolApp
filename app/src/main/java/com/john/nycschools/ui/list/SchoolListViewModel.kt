package com.john.nycschools.ui.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.john.nycschools.data.SchoolRepository
import com.john.nycschools.models.SchoolBorough
import com.john.nycschools.models.SchoolCollection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SchoolListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: SchoolRepository
) : ViewModel() {

    var schoolListUiState: SchoolListUiState by mutableStateOf(SchoolListUiState.Loading)
        private set

    init {
        val boroString: String =
            checkNotNull(savedStateHandle[SchoolListScreenDestination.boroObj])
        getSchoolList(boroString)
    }

    private fun getSchoolList(boro: String) {
        viewModelScope.launch {
            var schoolCollection = SchoolCollection()
            schoolListUiState = SchoolListUiState.Loading
            schoolListUiState = try {
                repository.loadSchoolsByBoro(boro)
                    .collect { schools ->
                        schoolCollection =
                            SchoolCollection(
                                type = SchoolBorough.fromString(boro),
                                schools = schools.sortedBy {
                                    it.school_name
                                }
                            )
                    }
                SchoolListUiState.Success(schoolCollection)
            }catch (e: IOException) {
                SchoolListUiState.Error(e.localizedMessage)
            } catch (e: HttpException) {
                SchoolListUiState.Error(e.localizedMessage)
            }
        }
    }
}