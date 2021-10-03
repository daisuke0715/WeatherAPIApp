package com.nemo.sampleweatherapp.extensions

class StateData<T> {
    var status : Status = Status.LOADING

    var error: ErrorState? = null

    var data: T? = null

    fun loading(): StateData<T> {
        status = Status.LOADING
        error = null
        data = null
        return this
    }

    fun success(_data: T? = null): StateData<T> {
        status = Status.SUCCESSFUL
        error = null
        data = _data
        return this
    }

    fun failure(errorState: ErrorState): StateData<T> {
        status = Status.ERROR
        error = errorState
        data = null
        return this
    }

    companion object {
        enum class Status {
            LOADING,
            SUCCESSFUL,
            ERROR
        }
    }
}

data class ErrorState(
    var message: String,
    var displayType: DisplayType = DisplayType.ALERT
)

enum class DisplayType {
    ALERT
}

object Errors {
    fun failedGetData(): ErrorState {
        return ErrorState("データの取得に失敗しました。", DisplayType.ALERT)
    }
}