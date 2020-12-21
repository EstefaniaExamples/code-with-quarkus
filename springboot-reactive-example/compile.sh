#!/usr/bin/env bash

ARTIFACT=$(mvn -q -Dexec.executable=echo -Dexec.args='${project.artifactId}' --non-recursive exec:exec);
MAINCLASS=$(mvn -q -Dexec.executable=echo -Dexec.args='${start-class}' --non-recursive exec:exec);
VERSION=$(mvn -q -Dexec.executable=echo -Dexec.args='${project.version}' --non-recursive exec:exec);

GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m'

rm -rf target
mkdir -p target/native-image

echo "Packaging $ARTIFACT with Maven"
mvn -ntp package > target/native-image/output.txt

JAR="$ARTIFACT-$VERSION.jar"
rm -f $ARTIFACT
echo "Unpacking $JAR"
cd target/native-image
jar -xvf ../$JAR >/dev/null 2>&1
cp -R META-INF BOOT-INF/classes

LIBPATH=`find BOOT-INF/lib | tr '\n' ':'`
CP=BOOT-INF/classes:$LIBPATH

GRAALVM_VERSION=`native-image --version`
echo "Compiling $ARTIFACT with $GRAALVM_VERSION"
{ time native-image \
  -H:EnableURLProtocols=http \
  -H:Name=$ARTIFACT \
  -H:+ReportExceptionStackTraces \
  -Dspring.spel.ignore=true \
  -Dspring.graal.mode=initialization-only \
  -Dspring.native.remove-yaml-support=true \
  -Dspring.native.remove-spel-support=true \
  -Dspring.native.remove-xml-support=true \
  --initialize-at-run-time=org.springframework.data.r2dbc.connectionfactory.ConnectionFactoryUtils \
  --trace-class-initialization=sun.instrument.InstrumentationImpl,javax.el.ListELResolver,javax.el.MapELResolver \
  --initialize-at-build-time=sun.instrument.InstrumentationImpl \
  --initialize-at-build-time=io.r2dbc.spi.IsolationLevel,io.r2dbc.spi \
  --initialize-at-build-time=io.r2dbc.spi.ConstantPool,io.r2dbc.spi.Assert,io.r2dbc.spi.ValidationDepth \
  --initialize-at-build-time=org.springframework.data.r2dbc.connectionfactory \
  --initialize-at-build-time=org.springboot.training.BookRepository \
  --no-fallback \
  --allow-incomplete-classpath \
  -cp $CP $MAINCLASS >> output.txt ; } 2>> output.txt

if [[ -f $ARTIFACT ]]
then
  printf "${GREEN}SUCCESS${NC}\n"
  mv ./$ARTIFACT ..
  exit 0
else
  cat output.txt
  printf "${RED}FAILURE${NC}: an error occurred when compiling the native-image.\n"
  exit 1
fi
