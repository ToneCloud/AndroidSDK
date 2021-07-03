package com.tone.cloud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.tone.cloud.api.ToneResponse

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        ToneCloud.initialize("","")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        zz()
    }

    fun zz() {
        ToneCloud.getMysql().update("zz","name='李四'","1=1",object : ToneCloudListener {
            override fun onDone(response: ToneResponse?) {
                Log.e("done",response.toString())
            }

            override fun onError(response: ToneResponse?) {
                Log.e("error",response.toString())
            }
        })
        /*
        ToneCloud.getMysql().insert("zz","id,name,age","233,'张三',16",object : ToneCloudListener {
            override fun onDone(response: ToneResponse?) {
                Log.e("done",response.toString())
            }

            override fun onError(response: ToneResponse?) {
                Log.e("error",response.toString())
            }
        })*/
        /*
        ToneCloud.getMysql().select("zz","name","1=1", object: ToneCloudListener {
            override fun onDone(response: ToneResponse?) {
                Log.e("done",response.toString())
                title = response?.message
            }

            override fun onError(response: ToneResponse?) {
                Log.e("error",response.toString())
            }
        })*/
    }
}