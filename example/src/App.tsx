import {
  registerScannerReceiver,
  unregisterScannerReceiver,
  onBarcodeScanned,
} from 'react-native-imin-scanner';
import { Text, View, StyleSheet } from 'react-native';
import { useState, useEffect } from 'react';

export default function App() {
  const [result, setResult] = useState<string>();

  useEffect(() => {
    registerScannerReceiver();
    const barcodeSubscription = onBarcodeScanned((barcode) => {
      console.log('Scanned barcode:', barcode);
      setResult(barcode);
    });

    return () => {
      barcodeSubscription.remove();
      unregisterScannerReceiver();
    };
  }, []);

  return (
    <View style={styles.container}>
      <Text>Result: {result}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
});
