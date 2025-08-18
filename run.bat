@echo off
echo Compiling Duke.java with Java 17...
javac -d out src/main/java/Duke.java
if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b 1
)

echo Running Duke program...
java -cp out Duke
pause
