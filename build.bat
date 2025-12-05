@echo off
:: ============================================================
:: Core Banking Transaction System - Build Script
:: ============================================================
echo.
echo ========================================
echo   BUILDING CORE BANKING SYSTEM
echo ========================================
echo.

:: Set paths
set SRC_DIR=src\main\java
set OUT_DIR=target\classes
set LIB_DIR=lib

:: Create output directory
if not exist %OUT_DIR% mkdir %OUT_DIR%

:: Compile all Java files
echo [1/2] Compiling Java sources...
javac -d %OUT_DIR% -cp "%LIB_DIR%\ojdbc6.jar" -sourcepath %SRC_DIR% %SRC_DIR%\com\corebanking\*.java %SRC_DIR%\com\corebanking\config\*.java %SRC_DIR%\com\corebanking\model\*.java %SRC_DIR%\com\corebanking\dto\*.java %SRC_DIR%\com\corebanking\exception\*.java %SRC_DIR%\com\corebanking\repository\*.java %SRC_DIR%\com\corebanking\repository\impl\*.java %SRC_DIR%\com\corebanking\service\*.java %SRC_DIR%\com\corebanking\service\impl\*.java %SRC_DIR%\com\corebanking\ui\*.java

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo [ERROR] Compilation failed!
    pause
    exit /b 1
)

echo [2/2] Build successful!
echo.
echo ========================================
echo   BUILD COMPLETE
echo   Run 'run.bat' to start the app
echo ========================================
pause
