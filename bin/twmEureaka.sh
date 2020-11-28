APP_NAME="TWM-EUREKA-SERVICE"
APP_VERSION="1.0-SNAPSHOT"
JAVA_PARAM="-Xmx251m"

BASE_PATH=$TWM_HOME_PARENT/TWM/twm-eureka-service/bin     #TWM-HOME-PARENT :: exported in .bashrc
JAR_PATH=$BASE_PATH/../target/twm-eureka-service-$APP_VERSION.jar

echo "Starting '$APP_NAME' with java param: '$JAVA_PARAM', at '$JAR_PATH'"
java $JAVA_PARAM -jar $JAR_PATH
