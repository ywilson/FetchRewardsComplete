package com.example.fetchrewards

import kotlinx.coroutines.delay
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


class Repository {

    suspend fun loadData(): MutableList<DataClass> {
        var jsonArray: JSONArray
        val dataList = mutableListOf<DataClass>()// used to display in list view


        val url = "https://fetch-hiring.s3.amazonaws.com/hiring.json"
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()

        client.run {

            newCall(request).enqueue(object : Callback {

                override fun onFailure(call: okhttp3.Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: okhttp3.Call, response: Response) {
                    if (!response.isSuccessful) {
                        System.err.println("Response unsuccessful")
                        return
                    }
                    val dataResponse = response.body?.string()
                    jsonArray = JSONArray(dataResponse)

                    for (i in 0 until jsonArray.length() - 1) {
                        val obj: JSONObject = jsonArray.getJSONObject(i)
                        val id = obj.getInt("id")
                        val listId = obj.getInt("listId")
                        val name = obj.getString("name")
                        val map = mutableListOf<DataClass>()

                        map.add(DataClass(id = id, listId = listId, name = name))
                        dataList.addAll(map)

                    }
                }

            })
            delay(1000)
        }
        return dataList
    }
}

