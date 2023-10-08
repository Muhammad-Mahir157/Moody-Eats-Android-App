package com.example.moodyeats

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.moodyeats.Constants
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

class ResultActivity : AppCompatActivity() {
lateinit var btn_finish:Button
lateinit var iv_trophy:ImageView
lateinit var finalmessage:TextView
lateinit var result:TextView
lateinit var ingridients:TextView
private var mInterstitialAd: InterstitialAd? = null
private final var TAG = "ResultActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        ingridients=findViewById(R.id.resultingridients)
        // TODO (STEP 6: Hide the status bar and get the details from intent and set it to the UI. And also add a click event to the finish button.)
        // START
        // Hide the status bar.
        ingridients.isVisible=false
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        ingridients.findViewById<TextView>(R.id.resultingridients)
        iv_trophy=findViewById(R.id.iv_trophy)
        finalmessage=findViewById(R.id.tv_final_msg)
        MobileAds.initialize(this@ResultActivity)
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(this,"ca-app-pub-9331202938399552/6230657773", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, adError?.message)
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })
        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                Log.d(TAG, "Ad was dismissed.")
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                Log.d(TAG, "Ad failed to show.")
            }

            override fun onAdShowedFullScreenContent() {
                Log.d(TAG, "Ad showed fullscreen content.")
                mInterstitialAd = null
            }
        }
        result=findViewById(R.id.tv_result)
        btn_finish=findViewById(R.id.btn_finish)
        val score = intent.getIntExtra(Constants.CORRECT_ANSWERS_SCORE, 0)

     //   tv_score.text = "Your Score is $correctAnswers out of $totalQuestions."
        if(score%4==0)
            iv_trophy.setImageResource(R.drawable.dessert)
        else if (score%4==1)
            iv_trophy.setImageResource(R.drawable.sushi)
        else if (score%4==2)
            iv_trophy.setImageResource(R.drawable.sea_food)
        else
            iv_trophy.setImageResource(R.drawable.red_meat)

        // Get back To Home Page
        btn_finish.setOnClickListener {
            if (mInterstitialAd != null) {
                mInterstitialAd?.show(this)
            } else {
                Toast.makeText(this,"The add is empty",Toast.LENGTH_LONG).show()
                Log.d("TAG", "The interstitial ad wasn't ready yet.")
            }
           finish()
        }
        ingridients.setOnClickListener {
            //go to ingridients page
        }
        // END
    }
}