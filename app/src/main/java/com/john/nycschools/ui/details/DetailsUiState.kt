package com.john.nycschools.ui.details

import com.john.nycschools.models.School

sealed interface DetailsUiState {
    data class Success(val school: School): DetailsUiState
    data class Error(val message: String): DetailsUiState
    data object Loading: DetailsUiState
}