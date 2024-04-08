package com.john.nycschools.models

import androidx.compose.runtime.Immutable

/**
 *  A collection used to display the high schools by nyc boroughs
 *
 */
@Immutable
data class SchoolCollection(
    val type: SchoolBorough = SchoolBorough.UNKNOWN,
    var schools: List<School> = emptyList()
)