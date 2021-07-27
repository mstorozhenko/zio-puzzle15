# Puzzle15
https://en.wikipedia.org/wiki/15_puzzle

## **Controls**
- w,a,s,d - move tile to empty cell
- q - quit game

## **Prerequisites**
- sbt 1.5.5
- scala 2.13.6

## **How to run**
`sbt run`

## **Structure**
Project consists of 3 packages: \
`package game` - contains all logic of board generation and moving tiles.
There is `trait Board` inside that we can extend for new 
game variations(for example: Photo puzzle). Currently, there is only
one implementation at that time - `IntegerBoard`.
`IntegerBoard` also can be initialized with size more than 4x4.
\
\
`package console` - contains logic for board rendering and console IO operations. 
`class IntegerBoardView` child of `BoardView` which can be extended for other type
of boards.
\
\
`package controller` - contains logic for combining functionality and interacting with
user.