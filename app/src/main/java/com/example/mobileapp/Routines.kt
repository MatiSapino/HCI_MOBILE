package com.example.mobileapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RoutineSection(
    routines: List<Routine>,
    onRoutineSelected: (Routine) -> Unit,
    onAddRoutine: () -> Unit,
    onDeleteRoutine: (Routine) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Routines", fontSize = 20.sp)
        IconButton(onClick = onAddRoutine) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Routine")
        }
    }
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(routines) { routine ->
            RoutineCard(routine = routine, onClick = { onRoutineSelected(routine) }, onDeleteRoutine = onDeleteRoutine)
        }
    }
}

@Composable
fun RoutineCard(
    routine: Routine,
    onClick: () -> Unit,
    onDeleteRoutine: (Routine) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var isExecuting by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .width(150.dp)
            .clickable(onClick = onClick)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.Home, contentDescription = null) // Replace with actual icon
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = routine.name)
            }
            HorizontalDivider()
            Button(
                onClick = { isExecuting = !isExecuting },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isExecuting) Color.Gray else MaterialTheme.colorScheme.primary
                )
            ) {
                Text(text = "Execute")
            }
            HorizontalDivider()
            Button(onClick = { expanded = !expanded }) {
                Text(text = if (expanded) "Less" else "More")
            }
            if (expanded) {
                Column {
                    routine.automations.forEach { automation ->
                        Text(text = automation)
                    }
                    Button(onClick = { onDeleteRoutine(routine) }) {
                        Text(text = "Delete")
                    }
                }
            }
        }
    }
}