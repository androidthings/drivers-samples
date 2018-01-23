Temperature sensor sample for Android Things
============================================

This Android Things sample demonstrates how to use a BMP280 temperature sensor
and integrate it to the Android `SensorManager`.


Screenshots
-----------

![Temperature sensor sample demo][demo-gif]

[(Watch the demo on YouTube)][demo-yt]

Pre-requisites
--------------

- Android Things compatible board
- Android Studio 2.2+
- [Rainbow Hat for Android Things](https://shop.pimoroni.com/products/rainbow-hat-for-android-things) or the following individual components:
    - 1 [BMP280 temperature sensor](https://www.adafruit.com/product/2651)
    - jumper wires
    - 1 breadboard

Schematics
----------

If you have the Raspberry Pi [Rainbow Hat for Android Things](https://shop.pimoroni.com/products/rainbow-hat-for-android-things), just plug it onto your Raspberry Pi 3.

![Schematics for Raspberry Pi 3](rpi3_schematics.png)

Build and install
=================

On Android Studio, click on the "Run" button.

If you prefer to run on the command line, from this repository's root directory, type

```bash
./gradlew bmx280:installDebug
adb shell am start com.example.androidthings.driversamples/.TemperatureActivity
```

If you have everything set up correctly, the current temperature will be
logged to logcat.

License
-------

Copyright 2016 The Android Open Source Project, Inc.

Licensed to the Apache Software Foundation (ASF) under one or more contributor
license agreements.  See the NOTICE file distributed with this work for
additional information regarding copyright ownership.  The ASF licenses this
file to you under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License.  You may obtain a copy of
the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
License for the specific language governing permissions and limitations under
the License.

[demo-yt]: https://www.youtube.com/watch?v=K4WuwHODXvY&list=PLWz5rJ2EKKc-GjpNkFe9q3DhE2voJscDT&index=16
[demo-gif]: https://storage.googleapis.com/android-things/samples-gifs/bmx280.gif