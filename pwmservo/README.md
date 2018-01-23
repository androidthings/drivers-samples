Servo sample for Android Things
===============================

This sample demonstrates how to control a servo using PWM with Android Things.


Screenshots
-----------

![Servo sample demo][demo-gif]

[(Watch the demo on YouTube)][demo-yt]

Pre-requisites
--------------

- Android Things compatible board
- Android Studio 2.2+
- 1 [servo](https://www.adafruit.com/products/169)
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
./gradlew pwmservo:installDebug
adb shell am start com.example.androidthings.driversamples/.ServoActivity
```

If you have everything set up correctly, you will see the servo periodically update its position.


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

[demo-yt]: https://www.youtube.com/watch?v=2IC6zcF29Ww&index=20&list=PLWz5rJ2EKKc-GjpNkFe9q3DhE2voJscDT
[demo-gif]: https://storage.googleapis.com/android-things/samples-gifs/pwmservo.gif