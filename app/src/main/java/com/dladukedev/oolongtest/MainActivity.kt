package com.dladukedev.oolongtest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.core.setContent
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.MaterialTheme
import androidx.ui.tooling.preview.Preview
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
                            Column(crossAxisAlignment = CrossAxisAlignment.Stretch) {
                                Row(mainAxisAlignment = MainAxisAlignment.Center) {
                                    DisplayResults(props)
                                }
                                HeightSpacer(10.dp)
                                Row(mainAxisAlignment = MainAxisAlignment.Center) {
                                    Button(
                                        text = "Get Data",
                                        onClick = { dispatch(props.getData()) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}
