package com.example.nyc_schools_test.model

import com.example.nyc_schools_test.model.remote.NycApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RepositoryImpl : Repository {
    private val service = NycApi.initRetrofit()

    override fun useCaseSchoolList(): Flow<UIState> {
        return flow {
            emit(UIState.Loading(true))
            delay(500)
            val response = service.getSchoolList()
            emit(UIState.Loading())

            if (response.isSuccessful) {
                response.body()?.let {
                    emit(UIState.ResponseListSchool(it))
                } ?: emit(
                    UIState.Error(response.message())
                )
            } else {
                emit(
                    UIState.Error(response.message())
                )
            }
            /*val list = mutableListOf<SchoolListResponse>()
            for (i in 0..10) {
                list.add(
                    SchoolListResponse(
                        "",
                        "School $i",
                        "",
                        "",
                        ""
                    )
                )
            }
            emit(list)*/
        }
    }

    override fun useCaseSchoolSatByDBN(dbn: String): Flow<UIState> {
        TODO("Not yet implemented")
    }
}