set BASE_DIR=c:\ManageTransfer\ManageTransfer
set JAVA_HOME=C:\Program Files\Java\jdk1.7.0_15
set ANT_HOME=%BASE_DIR%\apache-ant-1.9.2-bin
set PATH=%PATH%;%ANT_HOME%\bin
set CLASSPATH=%CLASSPATH%;%BASE_DIR%\Record;%BASE_DIR%\Record\lib\*.jar
ant.bat gensrc compile jar  run

pause