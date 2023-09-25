package com.example.dessertclicker.model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DessertViewModel : ViewModel(){
    private val _uiState = MutableStateFlow(DessertUiState())
    val uiState: StateFlow<DessertUiState> = _uiState.asStateFlow()

    fun dessertCalculate(){
        var sold = uiState.value.dessertCount
        val revenue = uiState.value.revenue
        if(sold<10){
            val updateState = uiState.value.copy(sold + 1, revenue + 5)
            _uiState.value = updateState
        }
    }
}