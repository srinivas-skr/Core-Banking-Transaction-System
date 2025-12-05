@echo off
:: ============================================================
:: Core Banking Transaction System - Run Script
:: ============================================================
echo.
echo ========================================
echo   CORE BANKING TRANSACTION SYSTEM
echo ========================================
echo.

:: Set paths
set OUT_DIR=target\classes
set LIB_DIR=lib

:: Check if compiled
if not exist %OUT_DIR%\com\corebanking\CoreBankingApplication.class (
    echo [ERROR] Application not built. Running build.bat first...
    call build.bat
)

:: Run application
echo Starting application...
echo.
java -cp "%OUT_DIR%;%LIB_DIR%\ojdbc6.jar" com.corebanking.CoreBankingApplication

pause
