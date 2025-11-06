@echo off
echo Starting BOMS Application...
echo.
echo Make sure MySQL is running on localhost:3306
echo Database 'boms_db' will be created automatically
echo.
echo Default Users:
echo Admin: admin/admin123
echo Sales: sales1/sales123  
echo Executive: executive1/exec123
echo.
pause

cd /d "%~dp0"
mvn spring-boot:run

pause