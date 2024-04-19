package com.duglasher.composedestinationsstl

import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.navigation.BottomSheetNavigator
import androidx.compose.material.navigation.ModalBottomSheetLayout
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.duglasher.composedestinationsstl.screens.DetailsScreen
import com.duglasher.composedestinationsstl.screens.ListScreen
import com.duglasher.composedestinationsstl.screens.NavGraphs
import com.duglasher.composedestinationsstl.screens.SheetScreen
import com.duglasher.composedestinationsstl.screens.destinations.DetailsScreenDestination
import com.duglasher.composedestinationsstl.screens.destinations.ListScreenDestination
import com.duglasher.composedestinationsstl.screens.destinations.SheetScreenDestination
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.manualcomposablecalls.bottomSheetComposable
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import kotlin.math.roundToInt

@Composable
fun STLApp() {
    val navHostEngine = rememberAnimatedNavHostEngine(
        rootDefaultAnimations = destinationsAnimations
    )

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    val bottomSheetNavigator = remember(sheetState) { BottomSheetNavigator(sheetState) }
    val navController = navHostEngine.rememberNavController(bottomSheetNavigator)

    val startRoute = remember { NavGraphs.root.startRoute }

    SharedTransitionLayout {
        ModalBottomSheetLayout(
            modifier = Modifier.fillMaxSize(),
            bottomSheetNavigator = bottomSheetNavigator
        ) {
            DestinationsNavHost(
                navGraph = NavGraphs.root,
                engine = navHostEngine,
                navController = navController,
                startRoute = startRoute,
            ) {
                /**
                 * Pay attention here.
                 * We are using the manual composable calls to define the composable functions
                 * instead of ComposeDestination's way of defining them.
                 *
                 * The reason for it is that we want to use the sharedTransitionScope and animatedVisibilityScope
                 * that are provided by the SharedTransitionLayout and navhost respectively.
                 *
                 * One more possible way is to declare screen as:
                 *
                 * context(SharedTransitionScope)
                 * @Destination
                 * @RootNavGraph(start = true)
                 * @Composable
                 * fun AnimatedVisibilityScope.ListScreen(...)
                 *
                 * but this way won't compile.
                 *
                 *
                 * One more problem — BottomSheets.
                 * We can't get AnimatedVisibilityScope inside bottomSheetComposable.
                 */

                composable(ListScreenDestination) {
                    ListScreen(
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this,
                        navigator = destinationsNavigator
                    )
                }
                composable(DetailsScreenDestination) {
                    DetailsScreen(
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this,
                        navigator = destinationsNavigator,
                        item = navArgs.item
                    )
                }
                /*bottomSheetComposable(SheetScreenDestination) {
                    SheetScreen(
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this,
                        navigator = destinationsNavigator,
                        item = navArgs.item
                    )
                }*/
            }
        }
    }
}

val destinationsAnimations = RootNavGraphDefaultAnimations(
    enterTransition = {
        fadeIn(
            animationSpec = ScreenAnimationSpec
        ) + slideIn(
            animationSpec = ScreenAnimationSlideSpec,
            initialOffset = ScreenOffsetEnterPopExitCalculation
        )
    },
    exitTransition = {
        slideOut(
            animationSpec = ScreenAnimationSlideSpec,
            targetOffset = ScreenOffsetExitPopEnterCalculation
        )
    },
    popEnterTransition = {
        slideIn(
            animationSpec = ScreenAnimationSlideSpec,
            initialOffset = ScreenOffsetExitPopEnterCalculation
        )
    },
    popExitTransition = {
        fadeOut(
            animationSpec = ScreenAnimationSpec
        ) + slideOut(
            animationSpec = ScreenAnimationSlideSpec,
            targetOffset = ScreenOffsetEnterPopExitCalculation
        )
    }
)

private const val animationDuration = 200
private const val widthSlideMultiplier = 0.125F
val ScreenAnimationSpec =
    tween<Float>(durationMillis = animationDuration, easing = FastOutSlowInEasing)
val ScreenAnimationSlideSpec =
    tween<IntOffset>(durationMillis = animationDuration, easing = FastOutSlowInEasing)
val ScreenOffsetEnterPopExitCalculation: (fullSize: IntSize) -> IntOffset = { fullSize ->
    IntOffset(
        x = (fullSize.width * widthSlideMultiplier).roundToInt(),
        y = 0
    )
}
val ScreenOffsetExitPopEnterCalculation: (fullSize: IntSize) -> IntOffset = { fullSize ->
    IntOffset(
        x = -(fullSize.width * widthSlideMultiplier).roundToInt(),
        y = 0
    )
}