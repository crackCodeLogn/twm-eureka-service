VERSION="1.0-SNAPSHOT"
BASE_PATH=$TWM_HOME_PARENT/TWM/twm-eureka-service/bin     #TWM-HOME-PARENT :: exported in .bashrc
JAR_PATH=$BASE_PATH/../target/twm-eureka-service-$VERSION.jar

java -Xmx251m -jar $JAR_PATH
