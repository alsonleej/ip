@echo off
echo Compiling Kip.java with Java 17...
javac -d out src/main/java/kip/Kip.java
if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b 1
)

echo Running Kip program...
java -cp out kip.Kip
pause
