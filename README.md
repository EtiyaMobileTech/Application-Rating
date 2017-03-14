# Etiya Application Rating

This is a library for Android applications, which asks users to rate the given application in Play Store.

The way it works:

Application rating dialog is shown after given times (frequency) of trigger. The lib can be called from onCreate, onStart, onResume, click event, or any type of event. Each time the lib is called, the counter increments towards the given frequency. After reaching the frequency, it will ask user whether to rate the application or not. If user says yes, it will open your application's Play Store page, and the rating dialog will never be shown again. If user says no, the rating dialog will re-appear after another cycle of the given frequency.

Texts of the rating dialog are customizable. Default frequency is set to 30, but can be customized, too.

Example usage:
```java
ApplicationRating.initRatingPopupManager(context, 10,<br /> 
				"Would you support the application with 5 stars, please?",<br />
                "YES", "NO");
```
				
**Gradle**
```
dependencies {<br />
    compile 'com.ferid.lib.applicationrating:application-rating:1.0.0'<br />
}

repositories {<br />
    maven {<br />
        url  "http://feridsourcebin.bintray.com/EtiyaApplicationRating"<br />
    }<br />
}
```

# Licence

Copyright (C) 2017 Ferid Cafer

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.