ADS1015 ADC sample for Android Things
=====================================

This Android Things sample demonstrates how to use an ADS1015 ADC module
with the ADS1xxx contrib driver.


Pre-requisites
--------------

- Android Things compatible board
- Android Studio 2.2+
- 1 [ADS1015 ADC module](https://www.adafruit.com/product/1083)
- jumper wires
- 1 breadboard

Schematics
----------

![Schematics for Raspberry Pi 3](rpi3_schematics.png)

Build and install
=================

On Android Studio, click on the "Run" button.

If you prefer to run on the command line, from this repository's root directory, type

```bash
./gradlew ads1015:installDebug
adb shell am start com.example.androidthings.driversamples/.MainActivity
```

If you have everything set up correctly, the latest ADC reading will be
written to logcat.

License
-------

Copyright 2018 The Android Open Source Project, Inc.

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
