#!/bin/bash
if [[ $1 == "build" ]] ; then
	#javac -d . ICommand/ICommand.java && javac -d . IRotatable/IRotatable.java && javac -d . IMovable/IMovable.java && javac dz2.java && java m
	#javac ICommand/ICommand.java && javac IRotatable/IRotatable.java && javac IMovable/IMovable.java && javac dz2.java && java m
	javac ICommand/*.java && javac IRotatable/*.java && javac IMovable/*.java && javac uobject/*.java 
	
	if [ $? -eq 0 ] ; then 
		echo 'dz6' && javac dz6.java && java m 
		#echo 'dz5' && javac dz5.java && java m 
		#echo 'dz4' && javac dz4.java && java m 
		#echo 'dz3' && javac dz3.java && java m
		#echo 'dz2' && javac dz2.java && java m
		#echo 'dz1' && javac dz1.java && java m 
	fi
fi

if [[ $1 == "clean" ]] ; then
	rm -rf *.class
	rm -rf ICommand/*.class
	rm -rf IRotatable/*.class
	rm -rf IMovable/*.class
	rm -rf IBurnable/*.class
	rm -rf UObject/*.class
fi

if [[ $1 == "rebuild" ]] ; then
	bash make.sh clean
	bash make.sh build
fi
