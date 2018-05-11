#!/bin/bash

# Usage: execute.sh [WildFly mode] [configuration file]
#
# The default mode is 'standalone' and default configuration is based on the
# mode. It can be 'standalone.xml' or 'domain.xml'.

JBOSS_HOME=/opt/jboss/wildfly
JBOSS_CUSTOMIZATION=$JBOSS_HOME/customization
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
export ACADEMI_CO_TESTS="java:/academi-co-tests"
export H2_URI="jdbc:mysql://172.18.0.3:3306/ACADEMI_CO_DB"
export H2_USER="root"
export H2_PWD="admin"

$JBOSS_CLI -c << EOF
batch

echo "Connection URL: " $CONNECTION_URL

# Add the datasource
data-source add --name=academi-co-tests --driver-name=h2 --jndi-name=$ACADEMI_CO_TESTS --connection-url=$H2_URI --user-name=$H2_USER --password=$H2_PWD --use-ccm=false --max-pool-size=25 --blocking-timeout-wait-millis=5000

# Add a realm based on a database
/subsystem=security/security-domain=academi-co-realm:add(cache-type=default)
/subsystem=security/security-domain=academi-co-realm/authentication=classic:add()
/subsystem=security/security-domain=academi-co-realm/authentication=classic/login-module=UsersRoles       \
    :add(code=UsersRoles, flag=required,                                                        \
         module-options={"usersProperties"=>"${JBOSS_CUSTOMIZATION}/academi-co-realm-users.properties",   \
                         "rolesProperties"=>"${JBOSS_CUSTOMIZATION}/academi-co-realm-roles.properties"})


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
