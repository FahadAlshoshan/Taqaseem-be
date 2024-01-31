#!/usr/bin/env bash
set -m
/usr/src/sql/run-initialization.sh & /opt/mssql/bin/sqlservr
fg