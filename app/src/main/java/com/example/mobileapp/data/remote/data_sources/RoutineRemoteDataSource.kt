package com.example.mobileapp.data.remote.data_sources

import com.example.mobileapp.data.remote.api.services.RoutineService
import com.example.mobileapp.data.remote.model.RemoteRoutine
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RoutineRemoteDataSource(
    private val routineService: RoutineService
) : RemoteDataSource() {

    val routines: Flow<List<RemoteRoutine<*>>> = flow {
        while (true) {
            val routines = handleApiResponse {
                routineService.getRoutines()
            }
            emit(routines)
            delay(DELAY)
        }
    }

    suspend fun getRoutine(routineId: String): RemoteRoutine<*> {
        return handleApiResponse {
            routineService.getRoutine(routineId)
        }
    }

    suspend fun addRoutine(routine: RemoteRoutine<*>): RemoteRoutine<*> {
        return handleApiResponse {
            routineService.addRoutine(routine)
        }
    }

    suspend fun executeRoutine(routineId: String): Array<*> {
        return handleApiResponse {
            routineService.executeRoutine(routineId)
        }
    }

    suspend fun deleteRoutine(routineId: String): Boolean {
        return handleApiResponse {
            routineService.deleteRoutine(routineId)
        }
    }

    companion object {
        const val DELAY: Long = 10000
    }
}