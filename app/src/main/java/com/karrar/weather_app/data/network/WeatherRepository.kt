package com.karrar.weather_app.data.network

import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import com.google.gson.Gson
import com.karrar.weather_app.data.domain.WeatherModel
import com.karrar.weather_app.util.State
import io.reactivex.rxjava3.core.Observable
import okhttp3.OkHttpClient
import okhttp3.Response
import org.reactivestreams.Subscriber


class WeatherRepository {

    fun getData( state: State<Any>): Observable<WeatherModel> {
        val response =   Client.fetchData()
        val result = Gson().fromJson(response.body?.string(), WeatherModel::class.java)
        State.OnLoading
        return when(state){

            is State.OnError ->  {
                 Observable.create { error  ->
                     run {
                         error.onError(Throwable())
                     }
                }
            }

            is State.OnSuccess -> {
            return Observable.create { subscriber ->
                run {

                    subscriber.onNext(result)
                    subscriber.onComplete()
                }
            }
            }

            else -> {}
        }











//        object : OnSubscribe<Response?>() {
//            fun call(subscriber: Subscriber<in Response?>) {
//                try {
//                    Client.fetchData()
//                    val response = client.newCall(request).execute()
//                    subscriber.onNext(response)
//                    subscriber.onCompleted()
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                    subscriber.onError(e)
//                }
//            }
//        })



//        return Observable.create(object : OnSubscribe<Response?>() {
//            fun call(subscriber: Subscriber<in Response?>) {
//                try {
//                    Client.fetchData()
//                    val response = client.newCall(request).execute()
//                    subscriber.onNext(response)
//                    subscriber.onCompleted()
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                    subscriber.onError(e)
//                }
//            }
//        })

//        val policy = ThreadPolicy.Builder().permitAll().build()
//        StrictMode.setThreadPolicy(policy)
//        val client = OkHttpClient()
//        val request: Request = Builder()
//            .url("https://github.com/ar-android/panfic/raw/master/Panfic/gen/com/ocit/data.json")
//            .get()
//            .addHeader("cache-control", "no-cache")
//            .addHeader("postman-token", "ac8311d5-3876-ea1e-53d3-85f9e397ea21")
//            .build()
    }
}