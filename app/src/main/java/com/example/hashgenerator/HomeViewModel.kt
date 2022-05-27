package com.example.hashgenerator

import android.util.Log
import androidx.lifecycle.ViewModel
import java.security.MessageDigest

class HomeViewModel:ViewModel() {

    fun getHash(plainText:String,algorithm:String):String{
        val byte=MessageDigest.getInstance(algorithm).digest(plainText.toByteArray())
        return toHex(byte)
    }
    private fun toHex(byteArray: ByteArray):String{
        val s = byteArray.joinToString (""){ "%02x".format(it) }
        Log.d("ViewModel",s)
        return s
    }
}