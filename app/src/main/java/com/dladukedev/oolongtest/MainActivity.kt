package com.dladukedev.oolongtest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.core.Text
import androidx.ui.core.setContent
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.MaterialTheme
import androidx.ui.unit.dp
import com.dladukedev.oolongtest.Store.init
import com.dladukedev.oolongtest.Store.update
import com.dladukedev.oolongtest.Store.view
import oolong.Oolong

@Composable
fun DisplayResults(props: Store.Props) {
    val resultText = when (val result = props.data) {
        is Result.Empty -> "Click to get Data!"
        is Result.Loading -> "Loading..."
        is Result.Error -> result.message
        is Result.Content -> result.data
    }

    Text(text = resultText)
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Oolong.runtime(
            init,
            update,
            view,
            { props, dispatch ->
                setContent {
                    MaterialTheme {
                        Center {
                            Column {
                                DisplayResults(props)
                                Spacer(modifier = LayoutHeight(10.dp))
                                Button(
                                    modifier = LayoutGravity.Center,
                                    onClick = { dispatch(props.getData()) }
                                ) {
                                    Text("Get Data")
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}
