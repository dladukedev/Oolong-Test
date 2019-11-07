package com.dladukedev.oolongtest

import kotlinx.coroutines.delay
import oolong.Init
import oolong.Update
import oolong.View
import oolong.effect
import oolong.effect.none
import java.lang.Exception

sealed class Result {
    object Empty: Result()
    object Loading: Result()
    data class Error(val message: String): Result()
    data class Content(val data: String): Result()
}

object Store {

    data class Model(
        val data: Result = Result.Empty
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
        val data: Result
    )

    val init: Init<Model, Msg> = {
        Model() to none()
    }

    val update: Update<Model, Msg> = { msg, model ->
        when (msg) {
            is Msg.LoadDataRequest -> {
                model.copy(data = Result.Loading) to loadData
            }
            is Msg.LoadDataError -> {
                model.copy(data = Result.Error(msg.message)) to none()
            }
            is Msg.LoadDataSuccess -> {
                model.copy(data = Result.Content(msg.data)) to none()
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