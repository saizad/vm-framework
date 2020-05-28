package com.saizad.mvvm.components.form

import com.sa.easyandroidform.ObjectUtils
import com.sa.easyandroidform.StringUtils
import com.sa.easyandroidform.StringUtils.stripTrailingLeadingNewLines
import io.reactivex.exceptions.CompositeException

private const val MAX_REQUIRED_STRING_LENGTH = 50

@Throws(CompositeException::class)
fun validateName(name: String?) {
    if (!isNameValid(name))
        throw Exception("Invalid Name format. Use first name followed by last name and space in between.")

    val split = stripTrailingLeadingNewLines(name).split(" ".toRegex()).toTypedArray()

    split.forEach {
        stringLengthValidation(it, MAX_REQUIRED_STRING_LENGTH, fieldName = "Name", min = 1)
    }
}

fun isNameValid(name: String?): Boolean {
    if (name == null) {
        return false
    }
    val split: Array<String> = stripTrailingLeadingNewLines(name).split(" ").toTypedArray()
    if (split.size != 2) {
        return false
    }
    for (i in split.indices) {
        val splitName = split[i]
        if (i == 0 && !isSingleNameValid(splitName)) {
            return false
        } else if (i == 1 && !isLastNameValid(splitName)) {
            return false
        }
    }
    return true
}

fun isLastNameValid(name: String?): Boolean {
    return ObjectUtils.isNull(name) || isSingleNameValid(name)
}

fun isSingleNameValid(name: String?): Boolean {
    return ObjectUtils.isNotNull(name) && name!!.matches("^[A-Za-z-]+$".toRegex())
}

fun isUserNameValid(name: String?): Boolean {
    return ObjectUtils.isNull(name) || name!!.matches("^[0-9A-Za-z_-]+$".toRegex())
}

@Throws(CompositeException::class)
@JvmOverloads
fun stringLengthValidation(string: String?, max: Int = -1, min: Int = 0, allowNone: Boolean = false,
                           fieldName: String? = null, minErrorMessage: String? = null, maxErrorMessage: String? = null,
                           nullStringErrorMessage: String? = null): String? {
    var fieldName = fieldName
    var minErrorMessage = minErrorMessage
    var maxErrorMessage = maxErrorMessage
    var nullStringErrorMessage = nullStringErrorMessage
    fieldName = fieldName ?: "value"

    if (ObjectUtils.isNull(nullStringErrorMessage)) {
        nullStringErrorMessage = "$fieldName can't be empty"
    }

    if (string == null && !allowNone) {
        throw Exception(nullStringErrorMessage)
    }

    if (ObjectUtils.isNull(maxErrorMessage)) {
        maxErrorMessage = "Max length for $fieldName is $max."
    }

    if (ObjectUtils.isNull(minErrorMessage)) {
        minErrorMessage = "Min length for $fieldName is $min."
    }

    if (ObjectUtils.isNotNull(string) && StringUtils.stripTrailingLeadingNewLines(string).length < min) {
        throw Exception(minErrorMessage)
    }

    if (ObjectUtils.isNotNull(string) && max > 0 && StringUtils.stripTrailingLeadingNewLines(string).length > max) {
        throw Exception(maxErrorMessage)
    }

    return string
}

//not-tested
@Throws(CompositeException::class)
@JvmOverloads
fun valueValidation(value: Int?, max: Int, min: Int = 0, allowNone: Boolean = true,
                    fieldName: String? = null, minErrorMessage: String? = null, maxErrorMessage: String? = null,
                    nullStringErrorMessage: String? = null): Int? {

    var fieldName = fieldName
    var minErrorMessage = minErrorMessage
    var maxErrorMessage = maxErrorMessage
    var nullStringErrorMessage = nullStringErrorMessage
    fieldName = if (fieldName != null) fieldName else "value"

    if (ObjectUtils.isNull(nullStringErrorMessage)) {
        nullStringErrorMessage = "$fieldName can't be empty"
    }

    if (ObjectUtils.isNull(value) && !allowNone) {
        throw Exception(nullStringErrorMessage)
    }

    if (ObjectUtils.isNull(maxErrorMessage)) {
        maxErrorMessage = "Max value for $fieldName is $max."
    }

    if (ObjectUtils.isNull(minErrorMessage)) {
        minErrorMessage = "Min value for $fieldName is $min."
    }

    if (value != null) {
        if (ObjectUtils.isNotNull(value) && value < min) {
            throw Exception(minErrorMessage)
        }
    }

    if (value != null) {
        if (ObjectUtils.isNotNull(value) && value > max) {
            throw Exception(maxErrorMessage)
        }
    }

    return value
}