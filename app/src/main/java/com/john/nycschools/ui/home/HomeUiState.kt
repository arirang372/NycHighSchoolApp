package com.john.nycschools.ui.home

import com.john.nycschools.models.SchoolCollection

sealed interface HomeUiState {
    data class Success(val schools: List<SchoolCollection>): HomeUiState
    data class Error(val message: String): HomeUiState
    data object Loading: HomeUiState
}