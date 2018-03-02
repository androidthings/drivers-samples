4-Digit Segment Display sample for Android Things
=================================================

This Android Things sample demonstrates how to use a 4-Digit segment display
based on the TM1637 IC.


Screenshots
-----------

![4-Digit Segment Display sample demo][demo-gif]

[(Watch the demo on YouTube)][demo-yt]

Pre-requisites
--------------

- Android Things compatible board
- Android Studio 2.2+
- 1 4-Digit Segment display like [this](http://wiki.seeed.cc/Grove-4-Digit_Display/)
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
./gradlew tm1637:installDebug
adb shell am start com.example.androidthings.driversamples/.SegmentDisplayActivity
```

If you have everything set up correctly, the segment display will show "2342".

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

[demo-yt]: https://www.youtube.com/watch?v=fUwTR3X9BdE&index=23&list=PLWz5rJ2EKKc-GjpNkFe9q3DhE2voJscDT
[demo-gif]: https://storage.googleapis.com/android-things/samples-gifs/tm1637.png