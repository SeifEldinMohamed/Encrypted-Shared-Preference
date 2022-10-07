package com.seif.encryptedsharedpreference

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = getSharedPreferences("simple_shared_preference", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putString("name", "Seif Eldin Mohamed")
            putString("age", "21")
            putString("Title", "Junior Android Software Engineer")
            apply()
        }

        getEncryptedSharedPreference().edit().apply {
            putString("name", "Seif Eldin Mohamed")
            putString("age", "21")
            putString("Title", "Junior Android Software Engineer")
            apply()
        }

    }
    private fun getEncryptedSharedPreference() : SharedPreferences {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        return EncryptedSharedPreferences.create(
            "encrypted_shared_preference",
            masterKeyAlias,
            this,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, // The encryption scheme to encrypt keys. Pref keys are encrypted deterministically with AES256-SIV-CMAC (RFC 5297)
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM //The encryption scheme to encrypt values. Pref values are encrypted with AES256-GCM. The associated data is the encrypted pref key
        )
    }
}

// when we create encrypted shared prefernce we can set the name of shared preference file dynamically inside the code