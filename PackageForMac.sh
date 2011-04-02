#!/bin/sh
AIRSDKBIN="/Users/dev/Downloads/AirSDK/bin"

echo "Packaging for Mac..."
$AIRSDKBIN/adt -package -target native natives/1upServerMonitor.dmg air/1upServerMonitor.air
echo "Done..."
