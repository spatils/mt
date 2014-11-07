set BASE_DIR=C:\Users\patils1\git\mt\Library\ManageTransfer
set JAVA_HOME=C:\Program Files\Java\jdk1.7.0_71
set ANT_HOME=C:\Users\patils1\git\mt\Library\ManageTransfer\CompilationLibraries\apache-ant-1.9.2-bin
set PATH=%PATH%;%ANT_HOME%\bin
set CLASSPATH=%CLASSPATH%;%BASE_DIR%\Record;%BASE_DIR%\Record\lib\*.jar
ant.bat InitializeDatabase run

pause