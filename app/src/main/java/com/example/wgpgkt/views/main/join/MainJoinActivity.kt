package com.example.wgpgkt.views.main.join

import android.content.Intent
import android.os.Bundle
import com.example.wgpgkt.R
import com.example.wgpgkt.base.BaseActivity
import com.example.wgpgkt.databinding.ActivityMainJoinBinding
import com.example.wgpgkt.views.main.join.general.MainJoinGeneralActivity

class MainJoinActivity: BaseActivity<ActivityMainJoinBinding>() {
    override val layoutResID: Int = R.layout.activity_main_join

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btnClick()
    }

    override fun onRestart() {
        super.onRestart()
        //회원가입이 하나이기때문에 이페이지 안보여줌
        finish()
    }

    override fun btnClick() {
        //버튼 클릭 조건 정해줘야됨
        val intent = Intent(this,MainJoinGeneralActivity::class.java)
        startActivity(intent)
    }
}