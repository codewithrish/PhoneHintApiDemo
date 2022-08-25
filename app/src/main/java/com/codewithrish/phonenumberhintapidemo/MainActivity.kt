package com.codewithrish.phonenumberhintapidemo

import android.app.PendingIntent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.identity.GetPhoneNumberHintIntentRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.tasks.OnSuccessListener

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val resultLauncher: ActivityResultLauncher<IntentSenderRequest> = registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            try {
                val phoneNumber =
                    Identity.getSignInClient(this).getPhoneNumberFromIntent(result.data)
                // Do something with the number
            } catch (e: Exception) {
                /// Log.e(TAG, "Phone Number Hint failed")
            }
        }

        val request: GetPhoneNumberHintIntentRequest =
            GetPhoneNumberHintIntentRequest.builder().build()


        Identity.getSignInClient(this)
            .getPhoneNumberHintIntent(request)
            .addOnSuccessListener { request: PendingIntent ->
                try {

                    resultLauncher.launch(IntentSenderRequest.Builder(request).build())
                } catch (e: Exception) {
                    Log.e("TAG", "Launching the PendingIntent failed")
                }
            }
    }


}