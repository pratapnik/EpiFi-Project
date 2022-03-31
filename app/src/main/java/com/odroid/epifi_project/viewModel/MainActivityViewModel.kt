package com.odroid.epifi_project.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.odroid.epifi_project.entity.AccountDetails
import com.odroid.epifi_project.entity.BirthDetails
import com.odroid.epifi_project.entity.Constants
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class MainActivityViewModel : ViewModel() {

    var validationLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun validateFields(accountDetails: AccountDetails) {
        viewModelScope.launch {
            val isFieldsValidationSuccessful = when {
                isValidPanCardNo(accountDetails.panCardNo)
                        && isBirthDateValid(accountDetails.birthDetails) -> true
                else -> false
            }
            validationLiveData.postValue(isFieldsValidationSuccessful)
        }
    }

    private fun isValidPanCardNo(panCardNo: String?): Boolean {
        panCardNo?.let {
            if (it.length < Constants.PAN_CARD_LENGTH) {
                return false
            }
        } ?: return false
        val panCardRegex = Constants.PAN_CARD_REGEX
        val panCardPattern: Pattern = Pattern.compile(panCardRegex)
        val panCardMatcher: Matcher = panCardPattern.matcher(panCardNo)
        return panCardMatcher.matches()
    }

    private fun isBirthDateValid(birthDetails: BirthDetails): Boolean {
        val date = birthDetails.birthDate
        val month = birthDetails.birthMonth
        val year = birthDetails.birthYear
        if (year.length < Constants.YEAR_LENGTH)
            return false
        val sdf = SimpleDateFormat(Constants.DATE_FORMAT)
        val birthDate = date.plus("/").plus(month).plus("/")
            .plus(year)
        sdf.isLenient = false
        return try {
            val dateOfBirth: Date = sdf.parse(birthDate)
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