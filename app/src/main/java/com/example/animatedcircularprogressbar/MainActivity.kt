package com.example.animatedcircularprogressbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                AnimatedProgressIndicator(percentage = 0.9f, number = 100)
            }
        }
    }
}

@Composable
fun AnimatedProgressIndicator(
    //How many amount of total circumference needs to be covered
    percentage: Float,
    fontSize: TextUnit = 34.sp,
    radius: Dp = 50.dp,
    color: Color = Color.Red,
    strokeWidth: Dp = 4.dp,
    number: Int,
    animatedDuration: Int = 2000,
) {
    var isAnimationPlayed by remember {
        mutableStateOf(false)
    }

    val currentPercentage =
        animateFloatAsState(
            targetValue = if (isAnimationPlayed) percentage else 0f,
            label = "",
            animationSpec = tween(
                durationMillis = animatedDuration,
                delayMillis = 0,
                easing = LinearEasing
            )
        )

    /*since only once it needs to be displayed*/
    LaunchedEffect(key1 = true) {
        isAnimationPlayed = true
    }

    Box(
        modifier = Modifier.size(radius * 2f),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.size(radius * 2f),
        ) {
            drawArc(
                color = color,
                -90f, //from here the animation starts
                360 * currentPercentage.value,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
        Text(
            text = (currentPercentage.value * number).toInt().toString() + " %",
            fontSize = fontSize,
            color = Color.Black
        )
    }
}
