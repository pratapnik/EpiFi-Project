package com.odroid.epifi_project.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.odroid.epifi_project.viewModel.MainActivityViewModel
import com.odroid.epifi_project.R
import com.odroid.epifi_project.databinding.ActivityMainBinding
import com.odroid.epifi_project.entity.AccountDetails
import com.odroid.epifi_project.entity.BirthDetails
import com.odroid.epifi_project.util.CommonUtils

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        setupFieldsValidationObserver()
        setupFieldsTextChangedListeners()
        setupButtonEventListeners()
    }

    private fun setupFieldsTextChangedListeners() {
        activityMainBinding.etPanInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                accountDetailsUpdated()
            }

        })

        activityMainBinding.etBirthDate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                accountDetailsUpdated()
            }
        })

        activityMainBinding.etMonthInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                accountDetailsUpdated()
            }

        })

        activityMainBinding.etYearInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                accountDetailsUpdated()
            }

        })
    }

    private fun setupFieldsValidationObserver() {
        viewModel.validationLiveData.observe(this, Observer {
            if(it)
                enableNextButton()
            else
                disableNextButton()
        })
    }

    private fun setupButtonEventListeners() {
        activityMainBinding.btnNext.setOnClickListener {
            CommonUtils.showToast(this, resources.getString(R.string.details_submitted_label))
            finish()
        }
        activityMainBinding.tvIDoNotHavePAN.setOnClickListener {
            finish()
        }
    }

    private fun accountDetailsUpdated() {
        val panCardNo = activityMainBinding.etPanInput.text.toString()
        val date = activityMainBinding.etBirthDate.text.toString()
        val month = activityMainBinding.etMonthInput.text.toString()
        val year = activityMainBinding.etYearInput.text.toString()
        val birthDetails = BirthDetails(date, month, year)
        val accountDetails = AccountDetails(panCardNo, birthDetails)
        viewModel.validateFields(accountDetails)
    }

    private fun disableNextButton() {
        activityMainBinding.btnNext.isEnabled = false
    }

    private fun enableNextButton() {
        activityMainBinding.btnNext.isEnabled = true
    }
}