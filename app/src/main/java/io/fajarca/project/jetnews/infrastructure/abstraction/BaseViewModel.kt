package io.fajarca.project.jetnews.infrastructure.abstraction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<Event : UiEvent, State : UiState, Effect : UiEffect> : ViewModel() {

    private val initialState: State by lazy { createInitialState() }

    /**
     * Use StateFlow for ui state.
     * StateFlow is just like LiveData but have initial value. So we have always a state.
     * It is also a kind of SharedFlow. We always want to receive last view state when UI become visible.
     */
    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    /**
     * Use SharedFlow for event. We want to drop event if there is not any subscriber.
     */
    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    /**
     * Use Channels for effect.
     * Because Channels are hot and we do not need to show side effect again when orientation
     * changed or UI become visible again.
     * Simply we want to replicate SingleLiveEvent behavior.
     */
    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()


    init {
        subscribeEvent()
    }

    private val currentState: State
        get() = _uiState.value


    /**
     * Set new Event
     */
    fun setEvent(event: Event) {
        val newEvent = event
        viewModelScope.launch { _event.emit(newEvent) }
    }

    /**
     * Set new Ui State
     */
    protected fun setState(reduce: State.() -> State) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }

    /**
     * Set new Effect
     */
    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }

    /**
     * Start listening to Event
     */
    private fun subscribeEvent() {
        viewModelScope.launch {
            event.collect { event ->
                handleEvent(event)
            }
        }
    }

    /**
     * Handle each event
     */
    abstract fun handleEvent(event: Event)

    abstract fun createInitialState(): State
}