package com.dladukedev.oolongtest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.ui.core.setContent
import com.dladukedev.oolongtest.store.Store.init
import com.dladukedev.oolongtest.store.Store.update
import com.dladukedev.oolongtest.store.Store.view
import com.dladukedev.oolongtest.ui.MainLayout
import oolong.Oolong


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Oolong.runtime(
            init,
            update,
            view,
            { props, dispatch ->
                setContent {
                    MainLayout(props, dispatch)
                }
            }
        )
    }
}
