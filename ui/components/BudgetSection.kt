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
import androidx.compose.ui.text.font.FontWeight

@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    segments: List<Pair<Float, Color>>
) {
    Canvas(modifier = modifier) {
        var startAngle = -90f
        segments.forEach { (percent, color) ->
            val sweepAngle = percent / 100f * 360f
            drawArc(
                color = color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true
            )
            startAngle += sweepAngle
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
            .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Budget",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PieChart(
                modifier = Modifier
                    .size(100.dp)
                    .padding(end = 16.dp),
                segments = segments
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                segments.forEachIndexed { index, (percent, color) ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(color = color, shape = CircleShape)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Metric ${index + 1} - ${percent.toInt()}%",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "$creditLeft credits left",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A661A),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
