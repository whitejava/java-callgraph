# check args
cd "`dirname "$0"`"
c="$1"
if [ "$c" == "" ] ; then
  echo try:
  ls ../src/main/java/gr/gousiosg/javacg/test | sed 's/\.java$//g' | xargs -n 1 echo 'bash' "$0"
  exit 1
fi

# run test
cd ..
mvn package -DskipTests
cd target
export CLASSPATH=javacg-0.1-SNAPSHOT-test.jar
java -javaagent:javacg-0.1-SNAPSHOT-dycg-agent.jar="incl=gr.gousiosg.javacg.test.*;" gr.gousiosg.javacg.test.$1
echo ------------------------
echo Call trace is:
cat calltrace.json
echo ------------------------
echo To xmind:
python3 ../process/to_xmind.py calltrace.json
