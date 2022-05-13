package com.wedid.views.main.prepare

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import com.wedid.R
import com.wedid.base.BaseActivity
import com.wedid.databinding.ActivityMainPrepareIntroBinding

class MainPrepareIntroActivity : BaseActivity<ActivityMainPrepareIntroBinding>() {

    override val layoutResID: Int = R.layout.activity_main_prepare_intro

    private lateinit var str: String
    private val RESULT_FINISH = 3

    //private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //supportActionBar?.hide()

        setAnimaition()
        //binding.viewmodel = viewModel

        btnClick()
    }
    /*
    override fun onRestart() {
        super.onRestart()
        val pref = getSharedPreferences("PREF", 0)
        str = pref.getString("NAME","")!!
        if(str.length > 0) finish()
    }*/

    fun setAnimaition(){
        val fadeIn = AlphaAnimation(0f,1f)
        fadeIn.interpolator = FastOutLinearInInterpolator()
        fadeIn.duration = 1500

        val animation = AnimationSet(false)
        animation.addAnimation(fadeIn)
        binding.ivMainPrepareIntro.animation = animation
        binding.tvMainPrepareIntro.animation = animation
        binding.btnMainPrepareIntroNext.animation = animation
    }

    override fun btnClick(){
        binding.btnMainPrepareIntroNext.setOnClickListener {
            //val intent = Intent(this, MainPrepareNameActivity::class.java)
            //startActivity(intent)
            openActivityForResult(MainPrepareNameActivity())
        }
        /*
        binding.btnMainLogin.setOnClickListener {

            makeToast("MainActivity :: LOGIN")
        }

        binding.btnMainJoin.setOnClickListener {
            val intent = Intent(this, MainJoinActivity::class.java)
            startActivity(intent)
        }

        binding.tvMainFind.setOnClickListener {
            try{
                val intent = Intent(this, MainFindActivity::class.java)
                startActivity(intent)
            }catch (e:Exception){
                Log.e("MainActivity : ", e.message.toString() )
            }
        }*/
    }

    fun openActivityForResult(activity: Activity){
        val intent = Intent(this, activity::class.java)
        startForResult.launch(intent)
    }

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == RESULT_FINISH){
            finish()
        }

    }
}