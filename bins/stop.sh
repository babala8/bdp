#!/usr/bin/env bash
ps -ef|grep name=treasury-brain |awk '{print "kill -9 ",$2}' |sh >/dev/null 2>&1