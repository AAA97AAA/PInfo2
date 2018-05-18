FROM jboss/wildfly:latest

ADD ./config_wildfly.sh ./academi-co-realm-roles.properties ./academi-co-realm-users.properties /opt/jboss/wildfly/
ADD ./log4j-core-2.11.0.jar ./log4j-api-2.11.0.jar ./log4j-web-2.11.0.jar /opt/jboss/wildfly/standalone/lib/ext/
RUN  sed -i_back 's/\r//g' /opt/jboss/wildfly/config_wildfly.sh

RUN ["/opt/jboss/wildfly/config_wildfly.sh"]
RUN rm -rf  /opt/jboss/wildfly/standalone/configuration/standalone_xml_history
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "--debug", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]

EXPOSE 8080
EXPOSE 8787
EXPOSE 9990
