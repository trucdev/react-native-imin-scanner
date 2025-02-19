package com.iminscanner

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.util.Log

class IminScannerModule(reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {
  private lateinit var scannerReceiver: ScannerReceiver

  init {
    scannerReceiver = ScannerReceiver(reactContext)
    registerScannerReceiver()
  }

  @ReactMethod
  fun registerScannerReceiver() {
    val filter = IntentFilter("com.imin.scanner.api.RESULT_ACTION")
    reactApplicationContext.registerReceiver(scannerReceiver, filter)
    Log.d("IminScannerModule", "ScannerReceiver registered")
  }

  @ReactMethod
  fun unregisterScannerReceiver() {
    reactApplicationContext.unregisterReceiver(scannerReceiver)
    Log.d("IminScannerModule", "ScannerReceiver unregistered")
  }

  override fun getName(): String {
    return NAME
  }

  companion object {
    const val NAME = "IminScanner"
  }
}
