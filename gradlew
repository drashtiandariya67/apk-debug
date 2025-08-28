#!/usr/bin/env sh

##############################################################################
##
##  Gradle start up script for UN*X
##
##############################################################################

# Attempt to locate JAVA
if [ -z "$JAVA_HOME" ]; then
  JAVA_CMD="$(command -v java)"
else
  JAVA_CMD="$JAVA_HOME/bin/java"
fi

if [ ! -x "$JAVA_CMD" ]; then
  echo "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH." 1>&2
  exit 1
fi

# Resolve APP_HOME (this script’s directory)
APP_HOME=$(cd "$(dirname "$0")"; pwd)

# Path to the wrapper JAR
WRAPPER_JAR="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"

# Check existence
if [ ! -f "$WRAPPER_JAR" ]; then
  echo "ERROR: Wrapper JAR not found at $WRAPPER_JAR" 1>&2
  exit 1
fi

# Execute Gradle via the wrapper
exec "$JAVA_CMD" -Xmx64m -Xms64m -cp "$WRAPPER_JAR" org.gradle.wrapper.GradleWrapperMain "$@"
