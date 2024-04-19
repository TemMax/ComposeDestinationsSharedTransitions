package com.duglasher.composedestinationsstl.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@RootNavGraph
@Composable
fun DetailsScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    navigator: DestinationsNavigator,
    item: Int
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFEFEFE))
            .padding(all = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.fillMaxHeight(0.5f)) {
            with(sharedTransitionScope) {
                Header(
                    animatedVisibilityScope = animatedVisibilityScope,
                    item = item
                )
            }
        }
    }
}

@Composable
private fun SharedTransitionScope.Header(
    animatedVisibilityScope: AnimatedVisibilityScope,
    item: Int
) {
    Box(
        modifier = Modifier
            .sharedElement(
                state = rememberSharedContentState(key = "item_$item"),
                animatedVisibilityScope = animatedVisibilityScope
            )
            .fillMaxSize()
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFFE0E0E0))
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Item ${
                if (item % 2 == 0) {
                    "Screen"
                } else {
                    "Sheet"
                }
            }"
        )
    }
}