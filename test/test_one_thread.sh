cd "`dirname "$0"`"
cd ..
mvn package -DskipTests
cd target
export CLASSPATH=javacg-0.1-SNAPSHOT-test.jar
java -javaagent:javacg-0.1-SNAPSHOT-dycg-agent.jar="incl=gr.gousiosg.javacg.test.*;" gr.gousiosg.javacg.test.TestOneThread
echo ------------------------
echo Call trace is:
cat calltrace.json
echo ------------------------
echo To xmind:
python3 ../process/to_xmind.py calltrace.json
