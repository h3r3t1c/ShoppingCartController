package com.github.h3r3t1c.shoppingCartController.presentation

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.HorizontalPageIndicator
import androidx.wear.compose.material.PageIndicatorState
import androidx.wear.compose.material.Scaffold
import com.github.h3r3t1c.shoppingCartController.presentation.theme.ShoppingCartControllerTheme
import com.github.h3r3t1c.shoppingCartController.presentation.ui.AboutPage
import com.github.h3r3t1c.shoppingCartController.presentation.ui.CartOnePage
import com.github.h3r3t1c.shoppingCartController.presentation.ui.CartTwoPage
import com.github.h3r3t1c.shoppingCartController.presentation.ui.InitialDialog
import com.github.h3r3t1c.shoppingCartController.presentation.util.Prefs


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainWearApp(this)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainWearApp(activity: Activity) {
    ShoppingCartControllerTheme {
        var showAcknowledgeDialog by remember {
            mutableStateOf(!Prefs.isInitialAckSet(activity))
        }
        val pagerState = rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f,

        ){3 /* is the number of pages... */}

        val pageIndicatorState: PageIndicatorState = remember {
            object : PageIndicatorState {
                override val pageOffset: Float
                    get() = pagerState.currentPageOffsetFraction
                override val selectedPage: Int
                    get() = pagerState.currentPage
                override val pageCount: Int
                    get() = pagerState.pageCount
            }
        }

        if(showAcknowledgeDialog){
            InitialDialog {
                showAcknowledgeDialog = false
                //Prefs.setInitialAckSet(activity)
            }
        }

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black),
            pageIndicator = { HorizontalPageIndicator(
                pageIndicatorState = pageIndicatorState,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxSize()
            )}
        ) {
            HorizontalPager(state = pagerState) { page ->
                when(page){
                    0-> CartOnePage()
                    1-> CartTwoPage()
                    2-> AboutPage(activity)
                }
            }
        }

    }
}