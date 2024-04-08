package com.john.nycschools.ui.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.john.nycschools.data.SchoolRepository
import com.john.nycschools.models.School
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: SchoolRepository
): ViewModel(){

    var detailsUiState: DetailsUiState by mutableStateOf(DetailsUiState.Loading)
        private set

    init {
        val schoolString: String = checkNotNull(savedStateHandle[DetailsScreenDestination.schoolObj])
        val school = Gson().fromJson(schoolString, School::class.java)
        getSchoolDetails(school)
    }

    private fun getSchoolDetails(school: School) {
        viewModelScope.launch {
            var newSchool = School()
            detailsUiState = DetailsUiState.Loading
            detailsUiState = try {
                repository.loadSchoolDetails(school)
                    .collect {
                        newSchool = it
                    }
                DetailsUiState.Success(newSchool)
            } catch (e: IOException) {
                DetailsUiState.Error(e.localizedMessage)
            } catch (e: HttpException) {
                DetailsUiState.Error(e.localizedMessage)
            }
        }
    }
}