package com.odroid.epifi_project.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.odroid.epifi_project.entity.AccountDetails
import com.odroid.epifi_project.entity.BirthDetails
import com.odroid.epifi_project.entity.Constants
import com.odroid.epifi_project.util.CommonUtils.isDateOfBirthValid
import com.odroid.epifi_project.util.CommonUtils.isPanCardNumberValid
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class MainActivityViewModel : ViewModel() {

    var validationLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun validateFields(accountDetails: AccountDetails) {
        viewModelScope.launch {
            val isFieldsValidationSuccessful = when {
                checkIfPanCardNumberIsValid(accountDetails.panCardNo)
                        && checkIfDateOfBirthIsValid(accountDetails.birthDetails) -> true
                else -> false
            }
            validationLiveData.postValue(isFieldsValidationSuccessful)
        }
    }

    private fun checkIfPanCardNumberIsValid(panCardNo: String?): Boolean {
        panCardNo?.let {
            if (it.length < Constants.PAN_CARD_LENGTH) {
                return false
            }
        } ?: return false
        return isPanCardNumberValid(panCardNo)
    }

    private fun checkIfDateOfBirthIsValid(birthDetails: BirthDetails): Boolean {
        val date = birthDetails.birthDate
        val month = birthDetails.birthMonth
        val year = birthDetails.birthYear
        if (year.length < Constants.YEAR_LENGTH)
            return false
        val dateFormat = SimpleDateFormat(Constants.DATE_FORMAT)
        val birthDate = date.plus("/").plus(month).plus("/")
            .plus(year)
        dateFormat.isLenient = false
        return isDateOfBirthValid(dateFormat, birthDate)
    }
}