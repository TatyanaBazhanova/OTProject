package com.otp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony

class OTPReceiver : BroadcastReceiver() {

    interface ResultAware {
        fun getSmsCode(smsCode: String)
    }

    companion object{
        private lateinit var resultAware: ResultAware

        fun setListener(resultAware: ResultAware){
            this.resultAware = resultAware
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        val message = messages[0].messageBody
        val removingRegex = """[^0-9]""".toRegex()
        val otp = message.replace(removingRegex, "")
        resultAware.getSmsCode(otp)
    }
}
