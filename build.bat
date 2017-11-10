@echo on

cd Commons
cmd /c ant

cd ../GameServer
cmd /c ant
xcopy .\build\dist\GameServer ..\..\build\GameServer\ /e/y


cd ../../
PAUSE