call setenv.bat
set CLASSPATH=;%BASE_DIR%/CompilationLibraries/Common/conf/;%BASE_DIR%/RunTimeLibraries/D7/lib/*;%BASE_DIR%/RunTimeLibraries/D7/conf/;%BASE_DIR%/RunTimeLibraries/Hibernate/conf/;%BASE_DIR%/RunTimeLibraries/Hibernate/lib/*;%BASE_DIR%/RunTimeLibraries/Phases/lib/*;%BASE_DIR%/RunTimeLibraries/Project/lib/*;%BASE_DIR%/RunTimeLibraries/SharePoint2013/lib/*;
java  com.managetransfer.sharepoint.SharePointImport   tlc 3 0
pause