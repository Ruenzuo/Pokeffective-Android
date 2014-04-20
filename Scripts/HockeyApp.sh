#!/bin/sh

# Executing Gradle wrapper
./gradlew clean build
# Creating build number
DIR='./'
count=`git rev-list HEAD | wc -l | sed -e 's/ *//g' | xargs -n1 printf %04d`
commit=`git show --abbrev-commit HEAD | grep '^commit' | sed -e 's/commit //'`
buildno=b"$count.$commit"
# Distributing APK over HockeyApp
puck -submit=auto -download=true -notes="Build by Travis CI with build number $buildno" -api_token=5f641487e3b0406cad450a9176be5eac -app_id=63f2eda0ed13b522abc43ff58f28d179 "Pokeffective/build/apk/Pokeffective-release.apk"
