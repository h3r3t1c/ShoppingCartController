package com.github.h3r3t1c.shoppingCartController.presentation

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.ShoppingCartCheckout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import com.github.h3r3t1c.shoppingCartController.R
import com.github.h3r3t1c.shoppingCartController.presentation.PlaySoundActivity.Companion.ACTION_ARM
import com.github.h3r3t1c.shoppingCartController.presentation.PlaySoundActivity.Companion.ACTION_LOCK
import com.github.h3r3t1c.shoppingCartController.presentation.PlaySoundActivity.Companion.ACTION_PURCHASE_CHECK
import com.github.h3r3t1c.shoppingCartController.presentation.PlaySoundActivity.Companion.ACTION_UNLOCK
import com.github.h3r3t1c.shoppingCartController.presentation.theme.ShoppingCartControllerTheme
import kotlinx.coroutines.delay


class PlaySoundActivity : ComponentActivity() {
    companion object {
        const val ACTION_UNLOCK = 0
        const val ACTION_LOCK = 1
        const val ACTION_ARM = 2;
        const val ACTION_PURCHASE_CHECK = 3

        const val TYPE_GATE_KEEPER = 0;
        const val TYPE_ROCATEQ = 1;

    }

    class Builder(private var context: Context) {
        private var intent: Intent = Intent(context, PlaySoundActivity::class.java)

        fun setType(type:Int):Builder{
            intent.putExtra("type", type)
            return this
        }
        fun setAction(action:Int):Builder{
            intent.putExtra("action",action);
            return this
        }
        fun showActivity(){
            context.startActivity(intent)
        }
    }

    private var mediaPlayer: MediaPlayer? = null
    private var initialVolume = -1
    private lateinit var audioManager:AudioManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        createMediaPlayer()
        setContent {
            PlaySoundView(mediaPlayer, intent.getIntExtra("action", -1));
        }
    }
    override fun onStop() {
        super.onStop()
        mediaPlayer?.release()
        mediaPlayer = null;
    }

    override fun onPause() {
        super.onPause()
        if(initialVolume != -1)
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, initialVolume, AudioManager.FLAG_VIBRATE)
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun onResume() {
        super.onResume()
        if(initialVolume == -1)
            initialVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), AudioManager.FLAG_VIBRATE)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    private fun createMediaPlayer(){
        val type = intent.getIntExtra("type", -1);
        mediaPlayer = when (intent.getIntExtra("action", -1)) {
            ACTION_UNLOCK ->{
                if(type == TYPE_GATE_KEEPER) MediaPlayer.create(this, com.github.h3r3t1c.sharedlib.R.raw.gatekeeper_unlock)
                else MediaPlayer.create(this, com.github.h3r3t1c.sharedlib.R.raw.roca_unlock)
            }
            ACTION_LOCK ->{
                if(type == TYPE_GATE_KEEPER) MediaPlayer.create(this, com.github.h3r3t1c.sharedlib.R.raw.gatekeeper_lock)
                else MediaPlayer.create(this, com.github.h3r3t1c.sharedlib.R.raw.roca_lock)
            }
            ACTION_ARM -> MediaPlayer.create(this, com.github.h3r3t1c.sharedlib.R.raw.roca_arm)
            ACTION_PURCHASE_CHECK -> MediaPlayer.create(this, com.github.h3r3t1c.sharedlib.R.raw.roca_arm)
            else -> MediaPlayer.create(this, com.github.h3r3t1c.sharedlib.R.raw.roca_purchase_check)
        }
        mediaPlayer?.setOnCompletionListener {
            window.decorView.postDelayed({
                finish();
            },500)
        }
    }
}
@Composable
fun PlaySoundView(mediaPlayer: MediaPlayer?, action:Int){
    ShoppingCartControllerTheme {
        var prg by remember {
            mutableStateOf(0f)
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xff000000)),
            contentAlignment = Alignment.Center
        ){
            val imageModifier = Modifier
                .width(48.dp)
                .height(48.dp)
            Column (
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                when(action){
                    ACTION_UNLOCK -> IconText(stringResource(R.string.unlock), Icons.Default.LockOpen)
                    ACTION_LOCK -> IconText(stringResource(R.string.lock), Icons.Default.Lock)
                    ACTION_ARM -> IconText(stringResource(R.string.arm), Icons.Default.NotificationsActive)
                    ACTION_PURCHASE_CHECK -> IconText(stringResource(R.string.purchase_check), Icons.Default.ShoppingCartCheckout)
                    else -> {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "icon",
                            modifier = imageModifier,
                            tint = Color(0xffff0000)
                        )
                    }
                }
            }
            CircularProgressIndicator(
                progress = prg,
                modifier = Modifier.fillMaxSize(),
                indicatorColor = Color(0xffFDD835),
                trackColor = Color(0xff424242)
            )
        }
        LaunchedEffect(Unit) {
            if (mediaPlayer != null) {
                mediaPlayer.start()
                while (mediaPlayer.isPlaying) {
                    prg = (mediaPlayer.currentPosition + 0.0f) / mediaPlayer.duration
                    delay(200)
                }
                prg = 1f
            }
        }
    }
}
@Composable
fun IconText(title:String, icon:ImageVector){
    Icon(
        imageVector = icon,
        contentDescription = title,
        modifier = Modifier
            .width(48.dp)
            .height(48.dp),
        tint = Color(0xffFDD835)
    )
    Text(
        text = title,
        textAlign = TextAlign.Center,
        //modifier = Modifier.fillMaxWidth(),
        color = Color.White
    )
}