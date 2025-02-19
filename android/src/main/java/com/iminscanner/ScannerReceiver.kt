package com.iminscanner

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.facebook.react.bridge.ReactApplicationContext

class ScannerReceiver(private val reactContext: ReactApplicationContext) : BroadcastReceiver() {
  override fun onReceive(context: Context?, intent: Intent?) {
    if (intent != null && intent.action == "com.imin.scanner.api.RESULT_ACTION") {
      val barcodeData = intent.getStringExtra("com.imin.scanner.api.label_type") ?: "Unknown Data"
      Log.d("IminScannerModule", "Scanned barcode: $barcodeData")
      val strData = intent.getStringExtra("decode_data_str") ?: "Unknown Data"
      Log.d("IminScannerModule", "Scanned barcode: $strData")
      sendEventToReactNative("IMIN_SCANNER_barcode_scanned", strData)
    }
  }

  private fun sendEventToReactNative(eventName: String, data: String) {
    Log.d("IminScannerModule", "Sending event to React Native: $eventName")
    Log.d("IminScannerModule", "Data: $data")
    reactContext
      .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
      .emit(eventName, data)
  }
}
