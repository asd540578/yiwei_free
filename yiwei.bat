@ECHO OFF
COLOR b
REM MODE CON COLS=65 LINES=20

REM 後面的數字是延遲時間(單位秒)
java jdelay 1
cls
goto begin

:noupdate
echo 無新的核心可更新
goto endud

:ifupdate
IF EXIST update\yiwei.jar goto movezip
IF NOT EXIST update\yiwei.jar goto noupdate

:movezip
del yiwei.jar
move /Y update\yiwei.jar yiwei.jar

echo 更新核心成功
goto endud

:begin
Title Yiwei - Game

REM ===================================參數設定=====================================
REM 記憶體相關配置REM -XX:+UseConcMarkSweepGC 對多核心有效, JVM 取得之最大值 -Xms1024m -Xmx1024m 但不要超過實體記憶體 80%，Windows 環境變數可增加 變數名稱:JAVA_OPTS 變數值:-Xms1024m -Xmx1024m
REM (不知用途 -XX:+ParallelGCThreads -XX:+UseParallelGC -XX:+UseAdaptiveSizePolicy)-Xmn480m -XX:NewSize=128m -XX:MaxNewSize=128m -XX:SurvivorRatio=128m -XX:MaxPermSize=128m
SET L1J_RAM=-Xms4096m -Xms4096m -Xmn512m -XX:PermSize=32M -XX:MaxPermSize=64M -Xss128k
REM Time相關配置
SET L1J_TIME=-Duser.timezone=Asia/Taipei


REM ===================================固定參數=====================================
REM 程式路徑(勿動)
SET L1J_PATH=-cp yiwei.jar;lib\c3p0-0.9.1.2.jar;lib\commons-loggint-1.1.1.jar;lib\log4j-1.2.16.jar;lib\mysql-connector-java-5.1.20-bin.jar com.lineage.Server
:sta
REM ===================================核心更新=====================================
goto ifupdate

:endud

REM ===================================核心啟動=====================================
echo 核心啟動
java -Xincgc %L1J_RAM% %L1J_TIME% %L1J_PATH%


cls
rem ===================================重新啟動=====================================
goto sta
