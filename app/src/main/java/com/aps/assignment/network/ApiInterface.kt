package com.aps.assignment.network

import com.aps.assignment.model.ResponseModel
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by ashish on 2020-30-10.
 */

interface ApiInterface {

    //https://plobalapps.s3-ap-southeast-1.amazonaws.com/dummy-app-data.json
    @GET("dummy-app-data.json")
    fun getFacts(): Call<JsonObject>
}
