package com.duglasher.composedestinationsstl.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.duglasher.composedestinationsstl.screens.destinations.DetailsScreenDestination
import com.duglasher.composedestinationsstl.screens.destinations.SheetScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@RootNavGraph(start = true)
@Composable
fun AnimatedVisibilityScope.ListScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    navigator: DestinationsNavigator
) {
    with(sharedTransitionScope) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFEFEFE)),
            contentPadding = PaddingValues(all = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                count = 100,
                key = { it }
            ) {
                ListItem(
                    animatedVisibilityScope = animatedVisibilityScope,
                    i = it,
                    onClick = {
                        if (it % 2 == 0) {
                            navigator.navigate(DetailsScreenDestination(it))
                        } else {
                            navigator.navigate(SheetScreenDestination(it))
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun SharedTransitionScope.ListItem(
    animatedVisibilityScope: AnimatedVisibilityScope,
    i: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .sharedElement(
                state = rememberSharedContentState(key = "item_$i"),
                animatedVisibilityScope = animatedVisibilityScope,
            )
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFE0E0E0))
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Item ${
                if (i % 2 == 0) {
                    "Screen"
                } else {
                    "Sheet"
                }
            }"
        )
    }
}