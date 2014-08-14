## Fuse -Camel-IBM MQ Demo

Verified and tested against JBoss Fuse 6.1.

This is a short introduction on how to quickly get stared with Fuse and MQ Series. This will show you how to configure Fuse to work with IBM MQ Series, and how to perform simple operations from Camel route such as read a message from an MQ Series queue, put it on a MQ queue, replyTo pattern etc. This could be useful if you need to demonstrate Fuse and MQ Series integration, or if you have a project that requires integration between the two



## Requirements

In order to work with MQ Series you have two options

a)download the evaluation version of MQ Series and run it locally. Evaluation version runs for about 90 days and this may be suitable for your needs. Install can be quite challenging if you haven't done it before. Aim for a half a day to a day, there is plenty of google and youtube help.

b)use an existing instance This is the preferred set up, since most of the heavy lifting is already done for you.

One thing that you will find handy is using IBM Websphere MQ Explorer, from here you can do most of admin tasks on MQ Series if you need to.




## Set up
In order to get this example working you need two things

a)install OSIG libraries

b)deploy camel routes onto Fuse

##Installing OSGI libraries
It is assumed that you have an instance of MQ Series available which is 7.x version. This was written for version of 7.1 MQ Series.
First thing you need to do is make MQ Series OSGI libraries available to Fuse.

These libraries are typically located under $MQ_INSTALL/java/lib/OSGi

 
Libraries that are required:

com.ibm.mq.osgi.java_7.1.0.3.jar

com.ibm.msg.client.osgi.jms_7.1.0.3.jar

com.ibm.msg.client.osgi.jms.prereq_7.1.0.3.jar

com.ibm.msg.client.osgi.wmq_7.1.0.3.jar

com.ibm.msg.client.osgi.wmq.prereq_7.1.0.3.jar

com.ibm.msg.client.osgi.wmq.nls_7.1.0.3.jar

com.ibm.msg.client.osgi.commonservices.j2se_7.1.0.3.jar

com.ibm.mq.osgi.xa_7.1.0.3.jar

com.ibm.msg.client.osgi.nls_7.1.0.3.jar

com.ibm.mq.osgi.directip_7.1.0.3.jar


You have a two ways of doing that

a)Manually adding these dependencies into Fuse via osgi:install command i.e

osgi:install -s file:/opt/mqm/java/lib/OSGi/com.ibm.mq.osgi.java_7.1.0.3.jar

osgi:install -s file:/opt/mqm/java/lib/OSGi/com.ibm.msg.client.osgi.jms_7.1.0.3.jar

osgi:install -s file:/opt/mqm/java/lib/OSGi/com.ibm.msg.client.osgi.jms.prereq_7.1.0.3.jar

osgi:install -s file:/opt/mqm/java/lib/OSGi/com.ibm.msg.client.osgi.wmq_7.1.0.3.jar

osgi:install -s file:/opt/mqm/java/lib/OSGi/com.ibm.msg.client.osgi.wmq.prereq_7.1.0.3.jar

osgi:install -s file:/opt/mqm/java/lib/OSGi/com.ibm.msg.client.osgi.wmq.nls_7.1.0.3.jar

osgi:install -s file:/opt/mqm/java/lib/OSGi/com.ibm.msg.client.osgi.commonservices.j2se_7.1.0.3.jar

osgi:install -s file:/opt/mqm/java/lib/OSGi/com.ibm.mq.osgi.xa_7.1.0.3.jar

osgi:install -s file:/opt/mqm/java/lib/OSGi/com.ibm.msg.client.osgi.nls_7.1.0.3.jar

osgi:install -s file:/opt/mqm/java/lib/OSGi/com.ibm.mq.osgi.directip_7.1.0.3.jar

 

b)creating a maven features project and setting these dependencies in the features.xml

Please refer to features project for an example
 

Ensure that all of the MQ bundles are started or resolved i.e

JBossFuse:karaf@root> list

''' 
[ 269] [Active	] [      	] [ 	] [   60] WebSphere MQ classes for Java Plug-in (7.1.0.3)

[ 270] [Active	] [      	] [ 	] [   60] JMS client Plug-in (7.1.0.3)

[ 271] [Active	] [      	] [ 	] [   60] JMS prereq Plug-in (7.1.0.3)

[ 272] [Active	] [      	] [ 	] [   60] WMQ provider Plug-in (7.1.0.3), Fragments: 276

[ 273] [Active	] [      	] [ 	] [   60] WMQ prereq Plug-in (7.1.0.3)

[ 274] [Resolved   ] [      	] [ 	] [   60] WMQ NLS Plug-in (7.1.0.3), Hosts: 277

[ 275] [Active	] [      	] [ 	] [   60] Common Services J2SE Plug-in (7.1.0.3)

[ 276] [Resolved   ] [      	] [ 	] [   60] WMQ Transactional Client Plug-in (7.1.0.3), Hosts: 272

[ 277] [Active	] [      	] [ 	] [   60] NLS Plug-in (7.1.0.3), Fragments: 274

[ 278] [Active	] [      	] [ 	] [   60] DirectIP Plug-in (7.1.0.3)
'''
 

##Deploy camel routes onto Fuse  
mvn clean install

In Fuse shell run
'''
JBossFuse:karaf@root> features:addurl mvn:org.apache.camel.demo.camel-wmq/features/1.0.0/xml/features
JBossFuse:karaf@root> features:install camel-wmq-demo
'''
##Invoke route 
by putting sample xml payload to jetty endpoint http://localhost:3000/order and http://localhost:4000/order
with something like SOAP UI or Firefox plugin HttpRequester
'''
<?xml version="1.0" encoding="UTF-8"><Customer><id>123</id><name>A</name></Customer>
'''
--END
