UART GPS sample for Android Things
==================================

This Android Things sample demonstrates how to connect to a GPS unit that
emits data via UART in the NMEA format, and integrates it into the Android
location framework.


Pre-requisites
--------------

- Android Things compatible board
- Android Studio 2.2+
- 1 GPS NMEA-compatible module with UART interface, like the
  [Ultimate GPS hat](https://www.adafruit.com/product/2324)
- jumper wires
- 1 breadboard


Schematics
----------

If using the [Raspberry Pi Ultimate GPS Hat](https://www.adafruit.com/product/2324), just plug it onto your
Raspberry Pi 3.

NOTE: on Raspberry Pi 3 you need to disable the serial debugging console using
the following [instructions][pi3-instructions] in order for the driver to be
able to access the UART port.


Build and install
=================

On Android Studio, click on the "Run" button.

If you prefer to run on the command line, from this repository's root directory, type

```bash
./gradlew gps:installDebug
adb shell am start com.example.androidthings.driversamples/.GpsActivity
```

If you have everything set up correctly, a log will be generated on logcat
whenever there is a location update.

Notice that the GPS driver integrates with the Android location framework
using a GpsDriver user driver and fuses with other location sources, so
the part of the app that handles the location updates works exactly the same
no matter which, or how many location sources are available.


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

[pi3-instructions]: https://developer.android.com/things/hardware/raspberrypi.html#disabling_the_console
