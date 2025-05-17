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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    segments: List<Pair<Float, Color>>
) {
    Canvas(modifier = modifier) {
        var startAngle = -90f
        segments.forEach { (percent, color) ->
            val sweep = percent / 100f * 360f
            drawArc(
                color = color,
                startAngle = startAngle,
                sweepAngle = sweep,
                useCenter = true
            )
            startAngle += sweep
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
            .padding(16.dp)
            .border(1.dp, Color.LightGray, shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Budget",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            PieChart(
                modifier = Modifier
                    .size(120.dp)
                    .padding(end = 16.dp),
                segments = segments
            )

            Column {
                segments.forEachIndexed { index, (percent, color) ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(color = color, shape = CircleShape)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Metric ${index + 1} - ${percent.toInt()}%")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "$creditLeft credits left",
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    color = Color(0xFF1A661A)
                )
            }
        }
    }
}
