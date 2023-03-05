package com.gotz.presentation.view.permission

import android.Manifest
import android.content.Intent
import com.gotz.presentation.base.BaseActivity
import com.gotz.presentation.R
import com.gotz.presentation.databinding.ActivityPermissionBinding
import com.gotz.presentation.view.onboarding.OnboardingActivity
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission

class PermissionActivity: BaseActivity<ActivityPermissionBinding>(R.layout.activity_permission) {

    companion object{
        val permissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    override fun initActivity() {
        requestPermission()
    }

    private fun requestPermission() {
        TedPermission.create()
            .setPermissionListener(object : PermissionListener {
                override fun onPermissionGranted() {
                    val intent = Intent(this@PermissionActivity, OnboardingActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    val intent = Intent(this@PermissionActivity, OnboardingActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            })
            .setDeniedMessage("위치 권한을 허용하지 않으면 날씨 정보를 얻을 수 없습니다.")
            .setDeniedCloseButtonText("그냥 사용하기")
            .setGotoSettingButtonText("설정으로 이동")
            .setPermissions(*permissions)
            .check()
    }
}