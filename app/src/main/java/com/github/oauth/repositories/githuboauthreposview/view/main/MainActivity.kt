package com.github.oauth.repositories.githuboauthreposview.view.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.github.oauth.repositories.githuboauthreposview.R

class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Получение разрешение на запись и считывание информации с телефона
        isStoragePermissionGranted()
        setContentView(R.layout.activity_main)
    }

    /** Получение разрешений на запись и считывание информации с телефона */
    fun isStoragePermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
                (this.getString(R.string.get_permission_write_read_text))
                true
            } else {
                Toast.makeText(this, this.getString(
                    R.string.not_get_permission_write_read_text), Toast.LENGTH_SHORT).show()
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1
                )
                false
            }
        } else {
            Toast.makeText(this, this.getString(
                R.string.get_permission_write_read_text), Toast.LENGTH_SHORT).show()
            true
        }
    }
}