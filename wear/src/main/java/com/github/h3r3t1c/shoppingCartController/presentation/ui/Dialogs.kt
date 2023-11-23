package com.github.h3r3t1c.shoppingCartController.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.ExperimentalWearFoundationApi
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.foundation.rememberActiveFocusRequester
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.ListHeader
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Shapes

import androidx.wear.compose.material.Text
import androidx.wear.compose.material.dialog.Dialog
import com.github.h3r3t1c.shoppingCartController.R
import com.github.h3r3t1c.shoppingCartController.presentation.theme.Blue500
import com.github.h3r3t1c.shoppingCartController.presentation.theme.Green500

@Composable
fun InitialDialog(onClose:()->Unit){
    var showDialog by remember{
        mutableStateOf(true)
    }
    val listState = rememberScalingLazyListState()

    Dialog(showDialog = showDialog,
        onDismissRequest = {
            showDialog = false
            onClose()
        },
        content ={
            Scaffold(
                positionIndicator = { PositionIndicator(scalingLazyListState = listState) }
            ) {
                ScalingLazyColumn(modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background),
                    state = listState

                ){
                    item{
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_shopping_cart),
                            contentDescription = stringResource(R.string.app_name),
                            tint = Color.White,
                            modifier = Modifier
                                .width(48.dp)
                                .height(48.dp)
                        )
                    }
                    item {
                        Text(
                            text = stringResource(R.string.app_name),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            color = Blue500,

                        )
                    }
                    item{
                        Text(
                            text = "This app is provided as is. It was created for educational purposes only. I take no responsibility for what you do with this app.",
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                    item{
                        Spacer(modifier = Modifier
                            .height(4.dp)
                            .fillMaxWidth())
                    }
                    item{
                        Button(
                            onClick = {
                                showDialog = false
                                onClose()
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Green500)
                        ) {
                            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Next")
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun InfoDialog(title:String, message:String, onClose: () -> Unit){
    var showDialog by remember{
        mutableStateOf(true)
    }
    val listState = rememberScalingLazyListState()

    Dialog(showDialog = showDialog,
        onDismissRequest = {
            showDialog = false
            onClose()
        },
        content ={
            Scaffold(
                positionIndicator = { PositionIndicator(scalingLazyListState = listState) }
            ) {
                ScalingLazyColumn(modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background),
                    state = listState

                ){
                    item{
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .width(48.dp)
                                .height(48.dp)
                        )
                    }
                    item {
                        Text(
                            text = title,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            color = Blue500
                        )
                    }
                    item{
                        Text(
                            text = message,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                    item{
                        Spacer(modifier = Modifier
                            .height(4.dp)
                            .fillMaxWidth())
                    }
                    item{
                        Button(
                            onClick = {
                                showDialog = false
                                onClose()
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Green500)
                        ) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = "Close")
                        }
                    }
                }
            }
        }
    )
}