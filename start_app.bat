@echo off
echo ==========================================
echo      ORACLE BANKING SYSTEM LAUNCHER
echo ==========================================

:: 1. Check and Copy Driver
if not exist "lib" mkdir lib

if exist "lib\ojdbc6.jar" (
    echo [OK] Driver found in lib folder.
) else (
    echo [INFO] Driver not found. Searching default Oracle path...
    if exist "C:\oraclexe\app\oracle\product\11.2.0\server\jdbc\lib\ojdbc6.jar" (
        copy "C:\oraclexe\app\oracle\product\11.2.0\server\jdbc\lib\ojdbc6.jar" "lib\ojdbc6.jar"
        echo [SUCCESS] Driver copied successfully!
    ) else (
        echo [ERROR] Could not find ojdbc6.jar automatically.
        echo Please manually copy 'ojdbc6.jar' to the 'lib' folder.
        pause
        exit /b
    )
)

:: 2. Compile
echo.
echo [INFO] Compiling Java files...
javac -cp ".;lib/ojdbc6.jar" *.java
if %errorlevel% neq 0 (
    echo [ERROR] Compilation failed!
    pause
    exit /b
)

:: 3. Run
echo.
echo [INFO] Starting Application...
echo ------------------------------------------
java -cp ".;lib/ojdbc6.jar" OracleBankingApp
pause
