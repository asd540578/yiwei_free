@ECHO OFF
COLOR b
REM MODE CON COLS=65 LINES=20

REM �᭱���Ʀr�O����ɶ�(����)
java jdelay 1
cls
goto begin

:noupdate
echo �L�s���֤ߥi��s
goto endud

:ifupdate
IF EXIST update\yiwei.jar goto movezip
IF NOT EXIST update\yiwei.jar goto noupdate

:movezip
del yiwei.jar
move /Y update\yiwei.jar yiwei.jar

echo ��s�֤ߦ��\
goto endud

:begin
Title Yiwei - Game

REM ===================================�ѼƳ]�w=====================================
REM �O��������t�mREM -XX:+UseConcMarkSweepGC ��h�֤ߦ���, JVM ���o���̤j�� -Xms1024m -Xmx1024m �����n�W�L����O���� 80%�AWindows �����ܼƥi�W�[ �ܼƦW��:JAVA_OPTS �ܼƭ�:-Xms1024m -Xmx1024m
REM (�����γ~ -XX:+ParallelGCThreads -XX:+UseParallelGC -XX:+UseAdaptiveSizePolicy)-Xmn480m -XX:NewSize=128m -XX:MaxNewSize=128m -XX:SurvivorRatio=128m -XX:MaxPermSize=128m
SET L1J_RAM=-Xms4096m -Xms4096m -Xmn512m -XX:PermSize=32M -XX:MaxPermSize=64M -Xss128k
REM Time�����t�m
SET L1J_TIME=-Duser.timezone=Asia/Taipei


REM ===================================�T�w�Ѽ�=====================================
REM �{�����|(�Ű�)
SET L1J_PATH=-cp yiwei.jar;lib\c3p0-0.9.1.2.jar;lib\commons-loggint-1.1.1.jar;lib\log4j-1.2.16.jar;lib\mysql-connector-java-5.1.20-bin.jar com.lineage.Server
:sta
REM ===================================�֤ߧ�s=====================================
goto ifupdate

:endud

REM ===================================�֤߱Ұ�=====================================
echo �֤߱Ұ�
java -Xincgc %L1J_RAM% %L1J_TIME% %L1J_PATH%


cls
rem ===================================���s�Ұ�=====================================
goto sta
