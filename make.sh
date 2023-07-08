#!/bin/bash
if [[ $1 == "build" ]] ; then
	#javac -d . ICommand/ICommand.java && javac -d . IRotatable/IRotatable.java && javac -d . IMovable/IMovable.java && javac dz2.java && java m
	#javac ICommand/ICommand.java && javac IRotatable/IRotatable.java && javac IMovable/IMovable.java && javac dz2.java && java m
	javac ICommand/*.java && javac IRotatable/*.java && javac IMovable/*.java && javac uobject/*.java && javac dz4.java && java m
fi

if [[ $1 == "clean" ]] ; then
	rm -rf *.class
	rm -rf ICommand/*.class
	rm -rf IRotatable/*.class
	rm -rf IMovable/*.class
	rm -rf UObject/*.class
fi

if [[ $1 == "rebuild" ]] ; then
	bash make.sh clean
	bash make.sh build
fi