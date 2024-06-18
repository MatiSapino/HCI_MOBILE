package com.example.mobileapp.data.repository

import com.example.mobileapp.data.model.Routine
import com.example.mobileapp.data.remote.data_sources.RoutineRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoutineRepository(
    private val remoteDataSource: RoutineRemoteDataSource
) {
    val routines: Flow<List<Routine>> =
        remoteDataSource.routines
            .map { it.map { jt -> jt.asModel() } }

    suspend fun getRoutine(routineId: String): Routine {
        return remoteDataSource.getRoutine(routineId).asModel()
    }

    suspend fun addRoutine(routine: Routine): Routine {
        return remoteDataSource.addRoutine(routine.asRemoteModel()).asModel()
    }

    suspend fun executeRoutine(routineId: String): Array<*> {
        return remoteDataSource.executeRoutine(routineId)
    }

    suspend fun deleteRoutine(routineId: String): Boolean {
        return remoteDataSource.deleteRoutine(routineId)
    }
}