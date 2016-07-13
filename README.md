# app-license-mask
An Android GUI solution to show license the App is using.

* Shows list of licenses in different app orientations.
* For horizontal shows app a pop-up list.
* For vertical shows app a normal activity.

# Struct

* Define different license under ```assets/licences-box``` with meaningful and unique name.

Example for MIT license:

```json
The MIT License (MIT)
Copyright (c) <year> <copyright holders>
.......
```

| Placeholder        | Comment |
| ------------- | -----|
| year     |  For copyright guilty years |
| copyright holders      |    author |


* Connect different library to different license in ```assets/license-list.json```.

Example for Apache-LICENSE-2.0 license libraries:

```json
{
  "licences": [
      {
        "name": "Apache-LICENSE-2.0",
        "description" : "Apache License, Version 2.0",
        "libraries": [
          {
            "name": "com.squareup.leakcanary:leakcanary-android",
            "owner": "Square, Inc.",
            "copyright": "2015"
          },
          {
            "name": "com.fasterxml.jackson.core:jackson-core",
            "owner": "FasterXML/jackson"
          },
          //...more libraries...
        ]
    }
    //...other licenses...
  ]
}
```
#### Library class:

| Placeholder        | Comment |
| ------------- | -----|
| name     |  name of library|
| owner (optional)     |    author |
| copyright(optional)      |    For copyright guilty years |

#### License class:

| Placeholder        | Comment |
| ------------- |-----|
| name     |  name of license|
| description     |  description of  license |
| array of library objects    |  collections of libraries |



### Third Party Dependencies

#### Event-bus for handling list

* Copyright (C) 2012-2016 Markus Junginger, greenrobot (http://greenrobot.org)

* EventBus binaries and source code can be used according to the Apache License, Version 2.0.

#### Gson for parsing json

*  Gson is released under the Apache 2.0 license.
*  Copyright 2008 Google Inc.

### Data-bining for ease programming

* https://developer.android.com/topic/libraries/data-binding/index.html

# License

```java
The MIT License (MIT)

Copyright (c) 2016 Chris Xinyue Zhao

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
