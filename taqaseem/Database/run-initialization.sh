#!/usr/bin/env bash
sleep 20
/opt/mssql-tools/bin/sqlcmd -S 127.0.0.1 -U SA -P ${SA_PASSWORD} -i /usr/src/sql/init.sql