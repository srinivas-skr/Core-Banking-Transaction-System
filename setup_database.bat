@echo off
echo ==========================================
echo      DATABASE SETUP AUTOMATION
echo ==========================================
echo.
echo This script will configure your Oracle Database automatically.
echo.
set /p syspass="Enter your Oracle SYS password (the one you set during install): "

echo.
echo [INFO] Connecting to database and running setup script...
sqlplus sys/%syspass%@XE as sysdba @init_db.sql

echo.
echo [INFO] Process Finished.
pause
