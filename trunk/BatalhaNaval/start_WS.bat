cd %homepath%
cd workspace\Android\BatalhaNaval\src\
set path=%path%;"C:\Arquivos de programas\Java\jdk1.7.0_25\bin"
set classpath=.
wsimport -keep -p server.cliente http://192.168.43.32/batalha?wsdl
pause
exit