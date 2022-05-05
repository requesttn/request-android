package tn.request.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tn.request.model.Resource
import tn.request.model.User
import tn.request.network.BackendService
import tn.request.network.dto.LoginRequest
import tn.request.network.dto.LoginResponse
import tn.request.preferences.CurrentUser
import tn.request.preferences.SharedPreferencesDao

class LoginViewModel(
    private val backendService: BackendService,
    private val preferencesDao: SharedPreferencesDao,
) : ViewModel() {

    private val internalUserLoginEvent = MutableLiveData<Resource<Unit>>(Resource.idle())
    val userLoginEvent = internalUserLoginEvent


    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            internalUserLoginEvent.postValue(Resource.loading(Unit))
            try {
                val response = backendService.login(LoginRequest(email, password)).execute()
                internalUserLoginEvent.postValue(
                    if (response.isSuccessful) {
                        saveCurrentUser(response.body()!!)
                        Resource.success(Unit)
                    } else {
                        when (response.code()) {
                            403 -> Resource.error("Email or password is invalid", Unit)
                            500 -> Resource.error(
                                "Internal server error, please try again later",
                                Unit
                            )
                            else -> Resource.error("Unknown error", Unit)
                        }
                    }

                )
            } catch (e: Exception) {
                internalUserLoginEvent.postValue(
                    Resource.error(
                        """Unable to reach the server
                    |${e}
                """.trimMargin(), Unit
                    )
                )
            }

        }
    }

    /**
     * Save logged-in user's information and jwt token in a persistent storage location.
     * */
    private fun saveCurrentUser(response: LoginResponse) {
        val user = response.user;
        preferencesDao.setCurrentUser(
            CurrentUser(
                response.jwt,
                user.firstname,
                user.lastname,
                user.email
            )
        )
    }
}