
import android.content.ContentValues
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aps.assignment.model.ResponseModel
import com.aps.assignment.network.ApiClient
import com.aps.assignment.network.ApiInterface
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
* Author:Ashish Savvashe
* */

class ResultDataRepository: CoroutineScope by MainScope() {

    private var responseModelData: MutableLiveData<ResponseModel> = MutableLiveData()

    val retrofit = ApiClient.client
    val requestInterface = retrofit.create(ApiInterface::class.java)

    init {
        responseModelData = MutableLiveData()
    }

    fun getFacts(): MutableLiveData<ResponseModel>{

        val accessTokenCall: Call<JsonObject> = requestInterface.getFacts()
        launch(Dispatchers.IO) {
            async { callApi(accessTokenCall) }
        }

        return responseModelData
    }

     fun callApi(accessTokenCall: Call<JsonObject>) {

         try {
             accessTokenCall.enqueue(object : Callback<JsonObject> {
                 override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {

                     if (response.body()!=null) {
                         val response1 = Gson().fromJson(response.body() , ResponseModel::class.java)

                         responseModelData.value = response1

                     } else {
                         // Toast. makeText(context!!, " No Record Data Found", Toast. LENGTH_LONG).show()
                     }
                 }

                 override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                     try {
                         Log.e(ContentValues.TAG, "callApiWithAccessToken:")
                     } catch (e: Exception) {
                         Log.e(ContentValues.TAG, "onFailure: $e")
                     }
                 }
             })
         } catch (e: Exception) {
             Log.e(ContentValues.TAG, "callApiWithAccessToken: $e")
         }
    }
}


