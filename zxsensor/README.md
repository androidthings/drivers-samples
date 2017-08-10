ZXSensor Sample for Android Things
==================================

This sample demonstrates how to control a ZXSensor using I2C or UART with
Android Things.

Pre-requisites
--------------

- Android Things compatible board
- Android Studio 2.2+
- the following individual components:
    - 1 [ZX Distance & Gesture Sensor](https://shop.pimoroni.com/products/zx-distance-and-gesture-sensor)
    - jumper wires
    - [Female headers](https://shop.pimoroni.com/products/female-headers)
    - 1 breadboard

Schematics
----------

![Schematics for Raspberry Pi 3](zxsensor_rpi3_schematics.png)

Build and install
=================

On Android Studio, click on the "Run" button.

If you prefer to run on the command line, from this repository's root directory, type

```bash
./gradlew zxsensor:installDebug
adb shell am start com.example.androidthings.driversamples/.ZxSensorActivity
```

If you have everything set up correctly, when you swipe over the sensor a log will appear in Logcat.

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
