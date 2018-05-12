#!/bin/bash

# Usage: execute.sh [WildFly mode] [configuration file]
#
# The default mode is 'standalone' and default configuration is based on the
# mode. It can be 'standalone.xml' or 'domain.xml'.

JBOSS_HOME=/opt/jboss/wildfly
JBOSS_STANDALONE_CONFIG=$JBOSS_HOME/standalone/configuration/
JBOSS_CLI=$JBOSS_HOME/bin/jboss-cli.sh
JBOSS_MODE=${1:-"standalone"}
JBOSS_CONFIG=${2:-"$JBOSS_MODE.xml"}

function wait_for_server() {
  until `$JBOSS_CLI -c ":read-attribute(name=server-state)" 2> /dev/null | grep -q running`; do
    sleep 1
  done
}

echo "=> Starting WildFly server"
$JBOSS_HOME/bin/$JBOSS_MODE.sh -b 0.0.0.0 -c $JBOSS_CONFIG &

echo "=> Waiting for the server to boot"
wait_for_server

echo "=> Executing the commands"
export ACADEMI_CO="java:/academi-co"
export MYSQL_URI="jdbc:mysql://172.18.0.4:3306/ACADEMI_CO_DB"
export ACADEMI_CO_TESTS="java:/academi-co-tests"
export MYSQL_URI_TESTS="jdbc:mysql://172.18.0.3:3306/ACADEMI_CO_DB"
export MYSQL_USER="root"
export MYSQL_PWD="admin"

$JBOSS_CLI -c << EOF
batch

echo "Connection URL: " $CONNECTION_URL

# Add MySQL module
module add --name=com.mysql --resources=/opt/jboss/wildfly/mysql-connector-java-5.1.45-bin.jar --dependencies=javax.api,javax.transaction.api

# Add MySQL driver
/subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql,driver-module-name=com.mysql,driver-xa-datasource-class-name=com.mysql.jdbc.jdbc2.optional.MysqlXADataSource)

# Add the datasource
data-source add --name=academi-co --driver-name=mysql --jndi-name=$ACADEMI_CO --connection-url=$MYSQL_URI?useUnicode=true&amp;characterEncoding=UTF-8 --user-name=$MYSQL_USER --password=$MYSQL_PWD --use-ccm=false --max-pool-size=25 --blocking-timeout-wait-millis=5000

# Add a realm based on a database
/subsystem=security/security-domain=academi-co-realm:add(cache-type=default)
/subsystem=security/security-domain=academi-co-realm/authentication=classic                                         \
   :add(login-modules=[                                                                                      \
           {"code"=>"Database", "flag"=>"required",                                                          \
            "module-options"=>{"dsJndiName" => "$ACADEMI_CO",                                   \
                               "principalsQuery" => "select passwd from ACADEMI_CO_DB.USERS username where username=?",    \
                               "rolesQuery" => "select role, 'Roles' from ACADEMI_CO_DB.USER_ROLES where username=?", \
			       "hashAlgorithm" => "SHA-256", \
			       "hashEncoding" => "base64"}}])

# Add the datasource for integration tests
data-source add --name=academi-co-tests --driver-name=mysql --jndi-name=$ACADEMI_CO_TESTS --connection-url=$MYSQL_URI_TESTS?useUnicode=true&amp;characterEncoding=UTF-8 --user-name=$MYSQL_USER --password=$MYSQL_PWD --use-ccm=false --max-pool-size=25 --blocking-timeout-wait-millis=5000

# Add a realm based on a database for integration tests
/subsystem=security/security-domain=academi-co-tests-realm:add(cache-type=default)
/subsystem=security/security-domain=academi-co-tests-realm/authentication=classic                                         \
   :add(login-modules=[                                                                                      \
           {"code"=>"Database", "flag"=>"required",                                                          \
            "module-options"=>{"dsJndiName" => "$ACADEMI_CO_TESTS",                                   \
                               "principalsQuery" => "select passwd from ACADEMI_CO_DB.USERS username where username=?",    \
                               "rolesQuery" => "select role, 'Roles' from ACADEMI_CO_DB.USER_ROLES where username=?", \
			       "hashAlgorithm" => "SHA-256", \
			       "hashEncoding" => "base64"}}])

#/subsystem=logging/console-handler=CONSOLE:write-attribute(name=level,value=TRACE)

#/subsystem=logging/logger=org.jboss.security:write-attribute(name=level,value=TRACE)
#Successfully saved resource [ ("subsystem" => "logging"), ("logger" => "org.jboss.security") ]

# Execute the batch
run-batch
EOF

/opt/jboss/wildfly/bin/add-user.sh admin admin

echo "=> Shutting down WildFly"
if [ "$JBOSS_MODE" = "standalone" ]; then
  $JBOSS_CLI -c ":shutdown"
else
  $JBOSS_CLI -c "/host=*:shutdown"
fi
