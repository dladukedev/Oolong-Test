package com.dladukedev.oolongtest.store

import kotlinx.coroutines.delay
import oolong.Init
import oolong.Update
import oolong.View
import oolong.effect
import oolong.effect.none

sealed class DataFetchResult {
    object Empty: DataFetchResult()
    object Loading: DataFetchResult()
    data class Error(val message: String): DataFetchResult()
    data class Content(val data: String): DataFetchResult()
}

object Store {

    data class Model(
        val data: DataFetchResult = DataFetchResult.Empty
    )

    sealed class Msg {
        object LoadDataRequest : Msg()
        data class LoadDataSuccess(val data: String) : Msg()
        data class LoadDataError(val message: String) : Msg()
    }

    private val loadData = effect<Msg>{ dispatch ->
        delay(500)
        try {
            dispatch(Msg.LoadDataSuccess("Hello World!"))
        } catch (e: Exception) {
            dispatch(Msg.LoadDataError(e.message ?: "UNKNOWN EXCEPTION"))
        }
    }

    class Props(
        val getData: () -> Msg,
        val data: DataFetchResult
    )

    val init: Init<Model, Msg> = {
        Model() to none()
    }

    val update: Update<Model, Msg> = { msg, model ->
        when (msg) {
            is Msg.LoadDataRequest -> {
                model.copy(data = DataFetchResult.Loading) to loadData
            }
            is Msg.LoadDataError -> {
                model.copy(data = DataFetchResult.Error(
                    msg.message
                )
                ) to none()
            }
            is Msg.LoadDataSuccess -> {
                model.copy(data = DataFetchResult.Content(
                    msg.data
                )
                ) to none()
            }
        }
    }

    val view: View<Model, Props> = { model ->
        Props(
            { Msg.LoadDataRequest },
            model.data
        )
    }
}