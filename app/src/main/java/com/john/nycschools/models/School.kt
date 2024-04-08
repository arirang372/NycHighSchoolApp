package com.john.nycschools.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 *  Object that holds local or remote School data
 *
 */

@Parcelize
@Entity(tableName = "schools")
data class School(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "dbn")
    var dbn: String = "",
    @ColumnInfo(name = "school_name")
    var school_name: String = "",
    @ColumnInfo(name = "boro")
    var boro: String = "",
    @ColumnInfo(name = "overview_paragraph")
    var overview_paragraph: String = "",
    @ColumnInfo(name = "phone_number")
    var phone_number: String = "",
    @ColumnInfo(name = "website")
    var website: String = "",
    @ColumnInfo(name = "total_students")
    var total_students: String = "",
    @ColumnInfo(name = "extracurricular_activities")
    var extracurricular_activities: String = "",
    @ColumnInfo(name = "primary_address_line_1")
    var primary_address_line_1: String = "",
    @ColumnInfo(name = "city")
    var city: String = "",
    @ColumnInfo(name = "state_code")
    var state_code: String = "",
    @ColumnInfo(name = "zip")
    var zip: String = "",
    @Ignore
    var details: SchoolDetails? = null
) : Parcelable

enum class SchoolBorough(val type: String, val title: String) {
    BRONX("X", "The Bronx"),
    BROOKLYN("K", "Brooklyn"),
    MANHATTAN("M", "Manhattan"),
    QUEENS("Q", "Queens"),
    STATEN_ISLAND("R", "Staten Island"),
    UNKNOWN("unknown", "unknown");

    companion object {
        fun fromString(value: String): SchoolBorough {
            return values().firstOrNull {
                it.type.equals(value, ignoreCase = true)
            } ?: UNKNOWN
        }
    }
}