# KotlinGolfer

[![codebeat badge](https://codebeat.co/badges/f9095c92-9bdb-4938-aa2d-5a93cdc88ab8)](https://codebeat.co/projects/github-com-jrtapsell-kotlingolfer-master)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/7c2d4dd6aeba4d5093343b1fc2eba857)](https://www.codacy.com/app/jrtapsell/kotlinGolfer?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=jrtapsell/kotlinGolfer&amp;utm_campaign=Badge_Grade)

A simple tool that takes in human readable Kotlin code, and returns markdown that can be pasted straight into [Programming Puzzles & Code Golf](https://codegolf.stackexchange.com).

## Features

* Minification of code
* Creation of Try It Online links
* Automatic edit notes and strikeout
* Supports headers and footers
* Detection of some optimisations to improve score

## Example files

Example files can be found in the docs directory

## Running

You want to launch the class `uk.co.jrtapsell.kotlinMin.CommandLine` with 1 argument, which should be the path of the input files. The output files will be in an output folder in the same directory.