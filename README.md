# Spoofax-Lua: a Lua Eclipse plugin based on Spoofax

## What is it?

Spoofax-Lua is an Eclipse plugin for editing Lua files, based on [Spoofax](http://strategoxt.org/Spoofax).
It's been made for educational/testing purposes, so don't expect too much.

## How to use?

Install Spoofax nightly by following the instructions [here](http://strategoxt.org/Spoofax/Download),
but use `http://www.lclnet.nl/update/nightly` as the update site URL.

When finished, import this project into your workspace and press ctrl+alt+B to build it.
After this `.lua` files should open in this plugin.

## Features

* Completion and reference resolving of:
  * global variables that are assigned anywhere in the file
  * local variables in the current scope
* Completion of table fields
* Warns about:
  * incorrect placement of `break` statements
  * use of unassigned globals
  * unused globals
  * unused locals
* Folding of functions
* Rudimentary outline view (only when deployed)
* Syntax tested on more than 3650 Lua files

## Missing features / To do

* Fix analysis when varargs are present
* Completion of function arguments
* Checks for obvious (type) errors
* Multi-file analysis (e.g., [dofile](http://www.lua.org/pil/8.html), [require](http://www.lua.org/pil/8.1.html))
* Debugging (not planned)
* Much more (not planned)
