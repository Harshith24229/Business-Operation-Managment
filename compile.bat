@echo off
echo Compiling BOMS Application...
mvn clean compile
if %ERRORLEVEL% EQU 0 (
    echo.
    echo ✅ Compilation successful!
    echo.
    echo To run the application:
    echo 1. Make sure MySQL is running
    echo 2. Run: mvn spring-boot:run
    echo 3. Access: http://localhost:8081
    echo.
    echo Default users created:
    echo - Admin: admin/admin123
    echo - Sales: sales1/sales123
    echo - Executive: executive1/exec123
) else (
    echo.
    echo ❌ Compilation failed!
)
pause