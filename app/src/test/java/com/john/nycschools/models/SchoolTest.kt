package com.john.nycschools.models

import org.junit.Assert
import org.junit.Test

internal class SchoolTest {

    private companion object {
        const val CUSTOM_TEXT_VALUE = "CUSTOM_TEXT_VALUE"
        const val DEFAULT_TEXT_VALUE = "DEFAULT_TEXT_VALUE"
        val CUSTOM_SCHOOL_DETAILS = SchoolDetails()
        val DEFAULT_SCHOOL_DETAILS = SchoolDetails()
    }

    @Test
    fun testCreateSchoolObjectWithDefaultConstructor() {
        val model = School()
        Assert.assertEquals("", model.dbn)
        Assert.assertEquals("", model.school_name)
        Assert.assertEquals("", model.boro)
        Assert.assertEquals("", model.overview_paragraph)
        Assert.assertEquals("", model.phone_number)
        Assert.assertEquals("", model.website)
        Assert.assertEquals("", model.total_students)
        Assert.assertEquals("", model.extracurricular_activities)
        Assert.assertEquals("", model.primary_address_line_1)
        Assert.assertEquals("", model.city)
        Assert.assertEquals("", model.state_code)
        Assert.assertEquals("", model.zip)
        Assert.assertEquals(null, model.details)
    }

    @Test
    fun testCreateSchoolObjectWithDefaultValues() {
        val model = School(
            dbn = DEFAULT_TEXT_VALUE,
            school_name = DEFAULT_TEXT_VALUE,
            boro = DEFAULT_TEXT_VALUE,
            overview_paragraph = DEFAULT_TEXT_VALUE,
            phone_number = DEFAULT_TEXT_VALUE,
            website = DEFAULT_TEXT_VALUE,
            total_students = DEFAULT_TEXT_VALUE,
            extracurricular_activities = DEFAULT_TEXT_VALUE,
            primary_address_line_1 = DEFAULT_TEXT_VALUE,
            city = DEFAULT_TEXT_VALUE,
            state_code = DEFAULT_TEXT_VALUE,
            zip = DEFAULT_TEXT_VALUE,
            details = DEFAULT_SCHOOL_DETAILS
        )

        Assert.assertEquals(DEFAULT_TEXT_VALUE, model.dbn)
        Assert.assertEquals(DEFAULT_TEXT_VALUE, model.school_name)
        Assert.assertEquals(DEFAULT_TEXT_VALUE, model.boro)
        Assert.assertEquals(DEFAULT_TEXT_VALUE, model.overview_paragraph)
        Assert.assertEquals(DEFAULT_TEXT_VALUE, model.phone_number)
        Assert.assertEquals(DEFAULT_TEXT_VALUE, model.website)
        Assert.assertEquals(DEFAULT_TEXT_VALUE, model.total_students)
        Assert.assertEquals(DEFAULT_TEXT_VALUE, model.extracurricular_activities)
        Assert.assertEquals(DEFAULT_TEXT_VALUE, model.primary_address_line_1)
        Assert.assertEquals(DEFAULT_TEXT_VALUE, model.city)
        Assert.assertEquals(DEFAULT_TEXT_VALUE, model.state_code)
        Assert.assertEquals(DEFAULT_TEXT_VALUE, model.zip)
        Assert.assertEquals(DEFAULT_SCHOOL_DETAILS, model.details)
    }

    @Test
    fun testCreateSchoolObjectWithSetterAndGetter() {
        val model = School(
            dbn = DEFAULT_TEXT_VALUE,
            school_name = DEFAULT_TEXT_VALUE,
            boro = DEFAULT_TEXT_VALUE,
            overview_paragraph = DEFAULT_TEXT_VALUE,
            phone_number = DEFAULT_TEXT_VALUE,
            website = DEFAULT_TEXT_VALUE,
            total_students = DEFAULT_TEXT_VALUE,
            extracurricular_activities = DEFAULT_TEXT_VALUE,
            primary_address_line_1 = DEFAULT_TEXT_VALUE,
            city = DEFAULT_TEXT_VALUE,
            state_code = DEFAULT_TEXT_VALUE,
            zip = DEFAULT_TEXT_VALUE,
            details = DEFAULT_SCHOOL_DETAILS
        ).also {
            it.dbn = CUSTOM_TEXT_VALUE
            it.school_name = CUSTOM_TEXT_VALUE
            it.boro = CUSTOM_TEXT_VALUE
            it.overview_paragraph = CUSTOM_TEXT_VALUE
            it.phone_number = CUSTOM_TEXT_VALUE
            it.website = CUSTOM_TEXT_VALUE
            it.total_students = CUSTOM_TEXT_VALUE
            it.extracurricular_activities = CUSTOM_TEXT_VALUE
            it.primary_address_line_1 = CUSTOM_TEXT_VALUE
            it.city = CUSTOM_TEXT_VALUE
            it.state_code = CUSTOM_TEXT_VALUE
            it.zip = CUSTOM_TEXT_VALUE
            it.details = CUSTOM_SCHOOL_DETAILS
        }

        Assert.assertEquals(CUSTOM_TEXT_VALUE, model.dbn)
        Assert.assertEquals(CUSTOM_TEXT_VALUE, model.school_name)
        Assert.assertEquals(CUSTOM_TEXT_VALUE, model.boro)
        Assert.assertEquals(CUSTOM_TEXT_VALUE, model.overview_paragraph)
        Assert.assertEquals(CUSTOM_TEXT_VALUE, model.phone_number)
        Assert.assertEquals(CUSTOM_TEXT_VALUE, model.website)
        Assert.assertEquals(CUSTOM_TEXT_VALUE, model.total_students)
        Assert.assertEquals(CUSTOM_TEXT_VALUE, model.extracurricular_activities)
        Assert.assertEquals(CUSTOM_TEXT_VALUE, model.primary_address_line_1)
        Assert.assertEquals(CUSTOM_TEXT_VALUE, model.city)
        Assert.assertEquals(CUSTOM_TEXT_VALUE, model.state_code)
        Assert.assertEquals(CUSTOM_TEXT_VALUE, model.zip)
        Assert.assertEquals(CUSTOM_SCHOOL_DETAILS, model.details)
    }
}