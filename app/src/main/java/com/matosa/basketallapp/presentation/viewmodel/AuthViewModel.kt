package com.matosa.basketallapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import com.matosa.basketallapp.data.AppDatabase
import com.matosa.basketallapp.data.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao = AppDatabase.getInstance(application)

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    sealed class AuthState {
        object Loading : AuthState()
        object Success : AuthState()
        data class Error(val message: String) : AuthState()
        object Idle : AuthState()
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _authState.postValue(AuthState.Loading)

                val user = userDao.loginUser(email, password)
                if (user != null) {
                    userDao.logoutAllUsers()
                    val updatedUser = user.copy(isLoggedIn = true)
                    userDao.updateUser(updatedUser)
                    _authState.postValue(AuthState.Success)
                } else {
                    _authState.postValue(AuthState.Error("Credenciales incorrectas"))
                }
            } catch (e: Exception) {
                _authState.postValue(AuthState.Error("Error en el login: ${e.message}"))
            }
        }
    }

    fun registerUser(email: String, password: String, name: String = "") {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _authState.postValue(AuthState.Loading)

                // Verifica si el usuario ya existe en la BD
                val existingUser = userDao.userExists(email)
                if (existingUser > 0) {
                    _authState.postValue(AuthState.Error("El email ya está registrado"))
                    return@launch
                }

                // Crear nuevo usuario
                val newUser = UserEntity(
                    email = email,
                    password = password,
                    name = name.ifEmpty { email.split("@")[0] },
                    isLoggedIn = true
                )

                userDao.insertUser(newUser)
                _authState.postValue(AuthState.Success)

            } catch (e: Exception) {
                _authState.postValue(AuthState.Error("Error en el registro: ${e.message}"))
            }
        }
    }

    fun checkLoggedInUser(onResult: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val user = userDao.getLoggedInUser()
                onResult(user != null)
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }

    fun resetAuthState() {
        _authState.value = AuthState.Idle
    }
}