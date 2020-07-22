package com.example.week6

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitService{
    //레트로핏은 인터페이스로 만들어야 함.
    //어노테이션 사용. @적어주기.
    //리퀘스트 타입을 적어줘야한다.
        @GET("json/students/")
        fun getStudentsList(): Call<ArrayList<PersonFromServer>>

        @POST("json/students/")
        fun createStudent(
            @Body params : HashMap<String, Any>
        ): Call<PersonFromServer>

        @POST("json/students/")
        fun createStudentEasy(
            @Body person : PersonFromServer
        ): Call<PersonFromServer>
}