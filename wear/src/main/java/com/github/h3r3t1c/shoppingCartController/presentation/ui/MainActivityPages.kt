package com.github.h3r3t1c.shoppingCartController.presentation.ui

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.ShoppingCartCheckout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.ExperimentalWearFoundationApi
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Card
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.rememberActiveFocusRequester
import androidx.wear.compose.material.Text
import com.github.h3r3t1c.shoppingCartController.BuildConfig

import kotlinx.coroutines.launch
import com.github.h3r3t1c.shoppingCartController.R
import com.github.h3r3t1c.shoppingCartController.presentation.PlaySoundActivity
import com.github.h3r3t1c.shoppingCartController.presentation.util.OpenLinkHelper

@OptIn(ExperimentalWearFoundationApi::class)
@Composable
fun AboutPage(activity: Activity){
    val listState = rememberScalingLazyListState()
    val focusRequester = rememberActiveFocusRequester()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    Scaffold(
        positionIndicator = { PositionIndicator(scalingLazyListState = listState) },
        modifier = Modifier.fillMaxSize()
    ){
        ScalingLazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .onRotaryScrollEvent {
                    coroutineScope.launch {
                        listState.scrollBy(it.verticalScrollPixels)
                    }
                    true
                }
                .focusRequester(focusRequester)
                .focusable(),
            state = listState
        ) {
            item{
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_shopping_cart),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .width(48.dp)
                        .height(48.dp)
                )
            }
            item{
                InfoTextOption(
                    stringResource(R.string.how_to_use),
                    stringResource(R.string.how_to_use_explain)
                )
            }
            item{
                InfoTextOption(
                    stringResource(R.string.how_it_works),
                    stringResource(R.string.how_it_works_msg)
                )
            }
            item {
                ClickableOption("DEFCON Talk", ImageVector.vectorResource(id = R.drawable.ic_youtube)) {
                    OpenLinkHelper.openRemoteLink(activity, "https://www.youtube.com/watch?v=fBICDODmCPI")
                }
            }
            item {
                ClickableOption("Original Work", Icons.Default.Language) {
                    OpenLinkHelper.openRemoteLink(activity, "https://www.begaydocrime.com/")
                }
            }
            item {
                ClickableOption("App Github", ImageVector.vectorResource(id = R.drawable.ic_github)) {
                    OpenLinkHelper.openRemoteLink(activity, "https://github.com/h3r3t1c/ShoppingCartController")
                }
            }
            item{
                ClickableOption("My linkedin", ImageVector.vectorResource(id = R.drawable.ic_linkedin)) {
                    OpenLinkHelper.openRemoteLink(activity, "https://www.linkedin.com/in/thomas-otero-5b8aa429/")
                }
            }
            item{
                ClickableOption("v"+BuildConfig.VERSION_NAME, Icons.Default.Info) {

                }
            }
            /*item {
                OpenLinkOption("App Support") {
                    OpenLinkHelper.openRemoteLink(activity, "https://github.com/h3r3t1c/ShoppingCartController")
                }
            }*/
        }
    }
}
@Composable
fun ClickableOption(title:String, icon:ImageVector, onClick:()->Unit){
    Card(
        onClick = { onClick() },
        modifier = Modifier
    ) {
        Column {
            Row {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 4.dp),
                    tint = Color.White
                )
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .height(24.dp)
                        .wrapContentHeight(align = Alignment.CenterVertically),
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }
        }
    }
}
@Composable
fun InfoTextOption(title:String, message:String){
    var showDialog by remember {
        mutableStateOf(false)
    }
    if(showDialog) {
        InfoDialog(title, message) {
            showDialog = false
        }
    }
    Card(
        onClick = { showDialog = true },
        modifier = Modifier
    ) {
        Column {
            Row {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 4.dp),
                    tint = Color.White
                )
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .height(24.dp)
                        .wrapContentHeight(align = Alignment.CenterVertically),
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }
        }
    }
}
@OptIn(ExperimentalWearFoundationApi::class)
@Composable
fun CartTwoPage(){
    val listState = rememberScalingLazyListState()
    val focusRequester = rememberActiveFocusRequester()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var imageSize by remember {
        mutableStateOf(48)
    }
    Scaffold(
        positionIndicator = { PositionIndicator(scalingLazyListState = listState) },
        modifier = Modifier.fillMaxSize()
    ){
        ScalingLazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .onRotaryScrollEvent {
                    coroutineScope.launch {
                        listState.scrollBy(it.verticalScrollPixels)
                        listState.animateScrollBy(0f)
                    }
                    true
                }
                .focusRequester(focusRequester)
                .focusable(),
            state = listState
        ) {
            item{
                Image(
                    painter =  painterResource(id = R.drawable.gatekeeper),
                    contentDescription = "Gatekeeper Cart",
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .height(imageSize.dp)
                        .width(imageSize.dp)
                        .clip(RoundedCornerShape(imageSize.dp))
                        .background(color = Color.White)
                        .clickable {
                            imageSize = if (imageSize != 48) 48 else 128
                        }
                )
            }
            item{
                CartActionOption(stringResource(R.string.unlock), Icons.Default.LockOpen){
                    PlaySoundActivity.Builder(context)
                        .setAction(PlaySoundActivity.ACTION_UNLOCK)
                        .setType(PlaySoundActivity.TYPE_GATE_KEEPER)
                        .showActivity()
                }
            }
            item{
                CartActionOption(stringResource(R.string.lock), Icons.Default.Lock){
                    PlaySoundActivity.Builder(context)
                        .setAction(PlaySoundActivity.ACTION_LOCK)
                        .setType(PlaySoundActivity.TYPE_GATE_KEEPER)
                        .showActivity()
                }
            }
        }
    }

}
@OptIn(ExperimentalWearFoundationApi::class)
@Composable
fun CartOnePage(){
    val listState = rememberScalingLazyListState()
    val focusRequester = rememberActiveFocusRequester()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var imageSize by remember {
        mutableStateOf(48)
    }
    Scaffold(
        positionIndicator = { PositionIndicator(scalingLazyListState = listState) },
        modifier = Modifier.fillMaxSize()
    ){
        ScalingLazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .onRotaryScrollEvent {
                    coroutineScope.launch {
                        listState.scrollBy(it.verticalScrollPixels)
                        listState.animateScrollBy(0f)
                    }
                    true
                }
                .focusRequester(focusRequester)
                .focusable(),
            state = listState
        ) {
            item{
                Image(
                    painter =  painterResource(id = R.drawable.rocateq),
                    contentDescription = "Rocateq Cart",
                    modifier =
                    Modifier
                        .padding(bottom = 8.dp)
                        .height(imageSize.dp)
                        .width(imageSize.dp)
                        .clip(RoundedCornerShape(imageSize.dp))
                        .background(color = Color.White)
                        .clickable {
                            imageSize = if (imageSize != 48) 48 else 128
                        }
                )
            }
            item{
                CartActionOption(stringResource(R.string.unlock), Icons.Default.LockOpen){
                    PlaySoundActivity.Builder(context)
                        .setAction(PlaySoundActivity.ACTION_UNLOCK)
                        .setType(PlaySoundActivity.TYPE_ROCATEQ)
                        .showActivity()
                }
            }
            item{
                CartActionOption(stringResource(R.string.lock), Icons.Default.Lock){
                    PlaySoundActivity.Builder(context)
                        .setAction(PlaySoundActivity.ACTION_LOCK)
                        .setType(PlaySoundActivity.TYPE_ROCATEQ)
                        .showActivity()
                }
            }
            item{
                CartActionOption(stringResource(R.string.arm), Icons.Default.NotificationsActive){
                    PlaySoundActivity.Builder(context)
                        .setAction(PlaySoundActivity.ACTION_ARM)
                        .setType(PlaySoundActivity.TYPE_ROCATEQ)
                        .showActivity()
                }
            }
            item{
                CartActionOption(stringResource(R.string.purchase_check), Icons.Default.ShoppingCartCheckout){
                    PlaySoundActivity.Builder(context)
                        .setAction(PlaySoundActivity.ACTION_PURCHASE_CHECK)
                        .setType(PlaySoundActivity.TYPE_ROCATEQ)
                        .showActivity()
                }
            }
        }
    }
}
@Composable
fun CartActionOption(title:String, icon:ImageVector, onClick: () -> Unit){
    Card(
        onClick = { onClick() },
        modifier = Modifier
    ) {
        Row {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.padding(end = 4.dp),
                tint = Color.White
            )
            Text(
                text = title,
                modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentHeight(align = Alignment.CenterVertically),
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }
}