package com.gotz.views.main.prepare

import android.content.Intent
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.lifecycle.Observer
import com.gotz.base.BaseApplication
import com.gotz.databinding.ActivityMainPrepareHelloBinding
import com.gotz.views.frame.FrameActivity

class MainPrepareHelloActivity:AppCompatActivity() {
    private lateinit var _binding: ActivityMainPrepareHelloBinding
    private val binding get() = _binding

    private val RESULT_FINISH = 3

    private val viewModel: MainPrepareHelloViewModel by viewModels{
        MainPrepareHelloViewModelFactory((application as BaseApplication).roomCalendarMemoRepositoryImpl)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainPrepareHelloBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewmodel = viewModel
        setAnimaition()
        val pref = getSharedPreferences("PREF",0)
        binding.tvMainPrepareHello.text = getHelloString(pref.getString("NAME","")!!)

        viewModel.saveButtonEvent.observe(this, Observer {
            val intent = Intent(this, FrameActivity::class.java)
            startActivity(intent)
            setResult(RESULT_FINISH)
            finish()
        })
    }

    fun setAnimaition(){
        val fadeIn = AlphaAnimation(0f,1f)
        fadeIn.interpolator = FastOutLinearInInterpolator()
        fadeIn.duration = 1500

        val animation = AnimationSet(false)
        animation.addAnimation(fadeIn)
        binding.tvMainPrepareHello.animation = animation
        binding.btnMainPrepareHelloNext.animation = animation
    }

    fun getHelloString(str:String):String{
        return str + "님\n환영합니다!"
    }
}