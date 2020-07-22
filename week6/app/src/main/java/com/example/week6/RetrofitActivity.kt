package com.example.week6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit_library)

        //http://mellowcode.org/json/students/
        //http://mellowcode.org/test/code/
        //베이스 url은 변하지 않는 부분을 말한다.
        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        //뒷부분. 변한은 url에 대비하는 서버 코드가 있는데,
        //이제부터는 뒷부분 url을 관리 해주는 파일.
        val service = retrofit.create(RetrofitService::class.java)

        //GET요청청
       service.getStudentsList().enqueue(object: Callback<ArrayList<PersonFromServer>> {
            override fun onFailure(call<ArrayList<PersonFromServer>>, t:Throwable){
                Log.d("retrofitt","ERROR")

            }

            override fun onResponse(
                call: Call<ArrayList<PersonFromServer>>,
                response: Response<ArrayList<PersonFromServer>>
            ) {
                if(response.isSuccessful){
                    val personList = response.body()
                    Log.d("retrofitt", "res: "+personList?.get(0)?.age)

                    val code=response.code()
                    Log.d("retrofitt", "code: " + code)

                    val header = response.headers()
                    Log.d("retrofitt", "code : "+ header)

                }
            }
        })
        //POST 요청 (1)
//        val params = HashMap<String, Any>
//        params.put("name", "김개똥")
//        params.put("age", 20)
//        params.put("intro", "안녕")
//        service.createStudent(params).enqueue(object: Callback<PersonFromServer>{
//            override fun onFailure(call: Call<PersonFromServer>, t:Throwable){
//
//            }
//
//            override fun onResponse(
//                call: Call<PersonFromServer>,
//                response: Response<PersonFromServer>
//            ){
//                if (response.inSuccessful) {
//                    val person = response.body()
//                    if (person != null) {
//                        Log.d("retrofit","name: "+ person?.name)
//                    }
//                }
//            }
//        })
        //POST 요청(2)
        val person = PersonFromServer(name="김철수", age = 12, intro = "안녕하세요 철수입니다. ")
        service.createStudentEasy(person).enqueue(object :Callback<PersonFromServer>{
            override fun onFailure(call: Call<PersonFromServer>, t:Throwable){
            }

            override fun onResponse(
                call: Call<PersonFromServer>,
                response: Response<PersonFromServer>
            ){
                if (response.inSuccessful) {
                    val person = response.body()
                    if (person != null) {
                        Log.d("retrofit","name: "+ person?.name)
                    }
                }
            }
        })
    }
}


