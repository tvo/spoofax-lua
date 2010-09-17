#!/bin/sh

#- set LUA_PATH to "?;./?.lua" (or, better yet, set LUA_PATH to "./?.lua;;"
export LUA_PATH="./?.lua;;"

#  and LUA_INIT to "package.path = '?;'..package.path")
export LUA_INIT="package.path = '?;'..package.path"

#- run "lua all.lua"
exec lua all.lua
