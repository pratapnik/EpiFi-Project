package com.odroid.epifi_project.util

import android.content.Context
import android.widget.Toast
import com.odroid.epifi_project.entity.Constants
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

object CommonUtils {

    fun showToast(context: Context, toastMessage: String) {
        Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show()
    }

    fun isPanCardNumberValid(panCardNo: String?): Boolean {
        val panCardRegex = Constants.PAN_CARD_REGEX
        val panCardPattern: Pattern = Pattern.compile(panCardRegex)
        val panCardMatcher: Matcher = panCardPattern.matcher(panCardNo)
        return panCardMatcher.matches()
    }

    fun isDateOfBirthValid(
        dateFormat: SimpleDateFormat,
        birthDate: String
    ): Boolean {
        return try {
            val dateOfBirth: Date = dateFormat.parse(birthDate)
            val today = Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24)
            if (dateOfBirth.before(today)) {
                return true
            }
            false
        } catch (e: Exception) {
            false
        }
    }
}