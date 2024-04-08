package com.john.nycschools.ui.list

import com.john.nycschools.models.SchoolCollection

sealed interface SchoolListUiState {
    data class Success(val collection: SchoolCollection) : SchoolListUiState
    data class Error(val message: String): SchoolListUiState
    data object Loading : SchoolListUiState
}