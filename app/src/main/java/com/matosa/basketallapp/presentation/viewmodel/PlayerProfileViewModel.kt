package com.matosa.basketallapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import com.matosa.basketallapp.data.PlayerProfileEntity
import com.matosa.basketallapp.data.PlayerProfileRepository
import kotlinx.coroutines.launch
import java.util.UUID

class PlayerProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PlayerProfileRepository(application)

    private val _profile = MutableLiveData<PlayerProfileEntity>()
    val profile: LiveData<PlayerProfileEntity> = _profile

    private val _saveState = MutableLiveData<SaveState>()
    val saveState: LiveData<SaveState> = _saveState

    sealed class SaveState {
        object Loading : SaveState()
        object Success : SaveState()
        data class Error(val message: String) : SaveState()
        object Idle : SaveState()
    }

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            val existingProfile = repository.getPlayerProfile()
            if (existingProfile != null) {
                _profile.value = existingProfile
            } else {
                // Crear perfil vacío si no existe
                _profile.value = PlayerProfileEntity()
            }
        }
    }

    fun saveProfile(profile: PlayerProfileEntity) {
        viewModelScope.launch {
            _saveState.value = SaveState.Loading

            val profileToSave = if (profile.id.isEmpty()) {
                profile.copy(
                    id = UUID.randomUUID().toString(),
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis()
                )
            } else {
                profile.copy(updatedAt = System.currentTimeMillis())
            }

            val success = repository.savePlayerProfile(profileToSave)
            if (success) {
                _profile.value = profileToSave
                _saveState.value = SaveState.Success
            } else {
                _saveState.value = SaveState.Error("Error al guardar el perfil")
            }
        }
    }

    fun resetSaveState() {
        _saveState.value = SaveState.Idle
    }

    fun deleteProfile() {
        viewModelScope.launch {
            val success = repository.deletePlayerProfile()
            if (success) {
                _profile.value = PlayerProfileEntity()
            }
        }
    }
}