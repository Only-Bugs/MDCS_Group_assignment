package com.example.ndis_client.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MultiRingChart(
    modifier: Modifier = Modifier,
    segments: List<Pair<Float, Color>>,
    backgroundColor: Color = Color(0xFFE0E0E0)
) {
    Canvas(modifier = modifier) {
        val center = size.center
        val thickness = 16.dp.toPx()
        val gap = 6.dp.toPx()
        val radiusStep = thickness + gap
        val totalRadius = (segments.size) * radiusStep

        segments.forEachIndexed { index, (percent, color) ->
            val sweepAngle = (percent / 100f) * 360f
            val radius = totalRadius - (index * radiusStep)
            val arcSize = Size(radius * 2, radius * 2)
            val topLeft = Offset(center.x - radius, center.y - radius)
            val strokeStyle = Stroke(width = thickness, cap = StrokeCap.Round)

            // Background Ring
            drawArc(
                color = backgroundColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = strokeStyle
            )

            // Foreground Arc
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = strokeStyle
            )
        }
    }
}

@Composable
fun BudgetSection(
    segments: List<Pair<Float, Color>>,
    creditLeft: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 12.dp)
//            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        // Title and Subtitle
        Column(
            modifier = Modifier.padding(bottom = 20.dp)
        ) {
            Text(
                text = "Budget Analysis",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )

            Text(
                text = "$creditLeft left",
                color = Color(0xFF1A661A),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left: Legend
            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.weight(1f)
            ) {
                segments.forEachIndexed { index, (percent, color) ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(color = color, shape = CircleShape)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Category ${index + 1} - ${percent.toInt()}%",
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            MultiRingChart(
                modifier = Modifier
                    .size(120.dp)
                    .padding(start = 12.dp),
                segments = segments
            )
        }
    }
}
