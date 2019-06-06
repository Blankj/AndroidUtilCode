@IF EXIST "%~dp0\node.exe" (
  "%~dp0\node.exe"  "%~dp0\precompile" %*
) ELSE (
  node  "%~dp0\precompile" %*
)