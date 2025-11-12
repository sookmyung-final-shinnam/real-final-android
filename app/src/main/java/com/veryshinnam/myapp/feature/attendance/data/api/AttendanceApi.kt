package com.veryshinnam.myapp.feature.attendance.data.api;

import com.veryshinnam.myapp.core.network.BaseResponse;
import com.veryshinnam.myapp.feature.attendance.data.dto.AttendanceResult;

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AttendanceApi {

    // 출석 체크 조회
    @GET("api/attendance")
    suspend fun getAttendance(
            @Query("year") year: Int? = null,
            @Query("month") month: Int? = null
    ): BaseResponse<AttendanceResult>

    // 오늘의 출석 체크 등록
    @POST("api/attendance")
    suspend fun addAttendance(
    ): BaseResponse<AttendanceResult>

    // 출석 체크 보상 교환
    @POST("api/attendance/exchange")
    suspend fun exchangeAttendance(
    ): BaseResponse<AttendanceResult>
}
