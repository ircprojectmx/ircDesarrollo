xcopy "C:\Projects\WS\resp\images\*.*" "C:\Projects\WS\IRC\war\irc\images" /S /I /Y
del "C:\Program Files (x86)\Apache Software Foundation\Tomcat 6.0\webapps\IRC" /F /S /Q
rd "C:\Program Files (x86)\Apache Software Foundation\Tomcat 6.0\webapps\IRC" /S /Q
xcopy "C:\Projects\WS\IRC\war\*.*" "C:\Program Files (x86)\Apache Software Foundation\Tomcat 6.0\webapps\IRC" /S /I /Y
