package com.john.nycschools.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 *  Object that holds local or remote School details data
 *
 */

@Parcelize
@Entity(
    tableName = "school_details",
    foreignKeys = [
        ForeignKey(entity = School::class, parentColumns = ["dbn"], childColumns = ["dbn_id"])
    ],
    indices = [Index("dbn_id")]
)
data class SchoolDetails(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,
    @ColumnInfo(name = "school_name")
    var school_name: String = "",
    @ColumnInfo(name = "dbn_id")
    var dbn: String = "",
    @ColumnInfo(name = "num_of_sat_test_takers")
    var num_of_sat_test_takers: String = "",
    @ColumnInfo(name = "sat_critical_reading_avg_score")
    var sat_critical_reading_avg_score: String = "",
    @ColumnInfo(name = "sat_math_avg_score")
    var sat_math_avg_score: String = "",
    @ColumnInfo(name = "sat_writing_avg_score")
    var sat_writing_avg_score: String = ""
) : Parcelable