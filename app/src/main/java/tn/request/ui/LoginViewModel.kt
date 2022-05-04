package tn.request.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tn.request.model.Resource
import tn.request.network.BackendService
import tn.request.network.dto.LoginRequest

class LoginViewModel(
    private val backendService: BackendService
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
                        Resource.success(Unit)
                    } else {
                        when (response.code()) {
                            400 -> Resource.error("Email or password is invalid", Unit)
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
}