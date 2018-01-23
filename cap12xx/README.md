Capacitive touch sample for Android Things
============================================

This Android Things sample demonstrates how to connect to a Capacitive Touch
sensor based on a CAP12xx microchip and how to integrate it to the
Android framework using an InputDriver.


Screenshots
-----------

![Capacitive touch sample demo][demo-gif]

[(Watch the demo on YouTube)][demo-yt]

Pre-requisites
--------------

- Android Things compatible board
- Android Studio 2.2+
- 1 capacitive touch sensor based on a CAP12xx microchip like the
  [Pimoroni Explorer Hat](https://www.adafruit.com/product/2427)
- jumper wires
- 1 breadboard


Schematics
----------

If using the [Raspberry Pi Explorer Hat]((https://shop.pimoroni.com/products/explorer-hat), just plug it onto your Raspberry Pi 3.


Build and install
=================

On Android Studio, click on the "Run" button.

If you prefer to run on the command line, from this repository's root directory, type

```bash
./gradlew cap12xx:installDebug
adb shell am start com.example.androidthings.driversamples/.CaptouchActivity
```

If you have everything set up correctly, pressing any of the 8 capacitive
buttons labeled 1 to 8 on the Explorer Hat will log the corresponding button
label to logcat.

Notice that the Cap12xx driver integrates with the Android framework using an
InputDriver user driver and simulates regular key presses, so the part of the
app that handles the button presses works exactly the same as if it was a
regular keyboard generating the key presses.


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

[demo-yt]: https://www.youtube.com/watch?v=4vbNUmD7vM0&index=17&list=PLWz5rJ2EKKc-GjpNkFe9q3DhE2voJscDT
[demo-gif]: https://storage.googleapis.com/android-things/samples-gifs/cap12xx.gif