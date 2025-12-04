#!/bin/sh
set -e

# Maven wrapper script

MAVEN_VERSION=3.8.6
MVN_CMD="mvn"

if [ -n "$MAVEN_OPTS" ]; then
    MVN_CMD="$MVN_CMD -DargLine=\"$MAVEN_OPTS\""
fi

if [ -n "$MAVEN_HOME" ]; then
    MVN_CMD="$MAVEN_HOME/bin/mvn"
else
    MVN_CMD="$MVN_CMD -Dmaven.home=$HOME/.m2/repository/apache-maven-$MAVEN_VERSION"
fi

exec "$MVN_CMD" "$@"