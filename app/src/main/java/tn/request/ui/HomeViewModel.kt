package tn.request.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tn.request.preferences.SharedPreferencesDao

class HomeViewModel(
    private val preferencesDao: SharedPreferencesDao
) : ViewModel() {

    val currentUser = MutableLiveData(preferencesDao.getCurrentUser())

}