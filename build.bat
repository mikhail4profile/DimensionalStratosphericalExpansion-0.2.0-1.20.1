@echo off
setlocal
chcp 65001 >nul
cd /d "%~dp0"

echo ===== Java (нужна версия 17) =====
java -version 2>&1
echo JAVA_HOME=%JAVA_HOME%
echo.
echo ===== Сборка, подождите... (лог: build-log.txt) =====
call gradlew.bat build --console=plain --stacktrace > build-log.txt 2>&1
set RESULT=%ERRORLEVEL%
type build-log.txt
echo.
if "%RESULT%"=="0" (
    echo ===== ГОТОВО. Jar лежит в папке build\libs =====
    dir /b build\libs
) else (
    echo ===== ОШИБКА, код %RESULT%. Полный текст сохранён в build-log.txt =====
)
echo.
pause
