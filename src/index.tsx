import { DeviceEventEmitter, NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-imin-scanner' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const IminScanner = NativeModules.IminScanner
  ? NativeModules.IminScanner
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export function registerScannerReceiver() {
  return IminScanner.registerScannerReceiver();
}
export function unregisterScannerReceiver() {
  return IminScanner.unregisterScannerReceiver();
}

interface BarcodeScannedCallback {
  (barcode: string): void;
}

export function onBarcodeScanned(callback: BarcodeScannedCallback) {
  return DeviceEventEmitter.addListener(
    'IMIN_SCANNER_barcode_scanned',
    callback
  );
}
