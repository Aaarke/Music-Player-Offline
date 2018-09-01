package com.example.asus.gopimusicplayer.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.content.Intent
import com.example.asus.gopimusicplayer.R


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        openHomeActivity()
    }
    /**
     * ==================================================Function to open Home Activity=====================================================
     * */
    private fun openHomeActivity(){

        Handler().postDelayed({
            val mainIntent = Intent(this@SplashActivity, HomeActivity::class.java)
            this@SplashActivity.startActivity(mainIntent)
            this@SplashActivity.finish()
        }, 3000)
    }

}
