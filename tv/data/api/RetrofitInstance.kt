package lynx.internship.tv.data.api


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASEURL = "https://api.themoviedb.org/3/"



    val retrofit = Retrofit.Builder()
        .baseUrl(BASEURL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(createClient())
        .build()


    fun createClient(): OkHttpClient {
       var interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        var client =  OkHttpClient.Builder().addInterceptor(interceptor).build()
        return client
    }
}


