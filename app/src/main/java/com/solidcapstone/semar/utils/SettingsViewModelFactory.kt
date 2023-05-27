package com.solidcapstone.semar.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.solidcapstone.semar.ui.profile.ProfileViewModel
import com.solidcapstone.semar.ui.profile.SettingPreferences

class SettingsViewModelFactory(private val preferences: SettingPreferences) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(preferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}