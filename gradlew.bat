@ECHO OFF
SET DIR=%~dp0
SET JAVA_EXE=java.exe
IF NOT "%JAVA_HOME%"=="" SET JAVA_EXE=%JAVA_HOME%\bin\java.exe

IF EXIST "%JAVA_EXE%" (
  SET _JAVA_CMD="%JAVA_EXE%"
) ELSE (
  ECHO.
  ECHO ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
  EXIT /B 1
)

SET WRAPPER_JAR=%DIR%\gradle\wrapper\gradle-wrapper.jar

%_JAVA_CMD% -Xmx64m -Xms64m -cp "%WRAPPER_JAR%" org.gradle.wrapper.GradleWrapperMain %*
