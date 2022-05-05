package tn.request.preferences

import android.content.Context
import android.content.SharedPreferences
import tn.request.R

class SharedPreferencesDao(context: Context) {
    companion object {
        private const val USER_FIRSTNAME = "user_firstname";
        private const val USER_LASTNAME = "user_firstname";
        private const val USER_EMAIL = "user_firstname";

        private const val USER_JWT = "user_jwt";
    }

    val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        context.getString(R.string.shared_preferences), Context.MODE_PRIVATE
    )

    fun setCurrentUser(currentUser: CurrentUser) {
        with(sharedPreferences.edit()) {
            putString(USER_FIRSTNAME, currentUser.firstname)
            putString(USER_LASTNAME, currentUser.lastname)
            putString(USER_EMAIL, currentUser.email)

            putString(USER_JWT, currentUser.jwt)
            commit()
        }
    }

    fun getCurrentUser(): CurrentUser? {
        with(sharedPreferences) {
            if (contains(USER_JWT)) {
                return CurrentUser(
                    getString(USER_JWT, "")!!,
                    getString(USER_FIRSTNAME, "")!!,
                    getString(USER_LASTNAME, "")!!,
                    getString(USER_EMAIL, "")!!,
                )
            } else {
                return null;
            }
        }
    }

    fun clearCurrentUser() {
        with(sharedPreferences.edit()) {
            remove(USER_LASTNAME)
            remove(USER_LASTNAME)
            remove(USER_EMAIL)
            remove(USER_JWT)
            commit()
        }
    }

    fun updateCurrentUserJwt(newJwt: String) {
        with(sharedPreferences.edit()) {
            putString(USER_JWT, newJwt);
            commit()
        }
    }
}