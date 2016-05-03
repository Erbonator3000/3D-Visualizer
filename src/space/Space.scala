package space

import scala.io.Source
import java.io.FileNotFoundException
import java.io.IOException

/**
 * @author eero
 */
class Space(val dimX: Int, val dimY: Int){
  
  var cubeSize=100
  var wallHeight=100
  var startingPosition=new Position(1,1)
  
  def setStart(position: Position)= if(!this.isWall(position)) startingPosition=position
  
  val grid = Array.ofDim[Boolean](dimX, dimY)
  
  
  
  
  def addWall(x: Int, y: Int) = {
    grid(x)(y)=true
  }
  
  
  def removeWalls(x: Int, y: Int) = {
   grid(x)(y)=false
  }
  
  
  def isWall(position: Position) = {
    if(position.x<0 || position.y<0 || position.x > dimX-1 || position.y > dimY-1) true //if outside the map then always true
    else grid(position.x)(position.y)
  }
  
  def isWall(x: Double, y: Double): Boolean = {
    if(x<0 || y<0) true else this.isWall(new Position((x/this.cubeSize).toInt,(y/this.cubeSize).toInt))
  }
  
  /**for testing purposes
   *if you want to print the map as character graphics*/
  def printWalls={
    for(row <- this.grid){
      for(cube <- row){
        if(cube) print("1") else print("0")
      }
      println()
      
    }
  }
  
  

}






object Space{
   
  def generateFromFile(fileName: String): Space={
    var newSpace=new Space(10,10) //if fail to read file, return an empthy map
    
    try{
      val mapFile = Source.fromFile(fileName)
      val rows=mapFile.getLines().toVector
      newSpace = new Space(rows.maxBy(_.length).length(),rows.length)
    
      for(i <- 0 until rows.length){
        for(j <- 0 until rows(i).length){
          rows(i)(j) match{
            case '1' => newSpace.addWall(j, i)
            case '2' => newSpace.setStart(new Position(j,i))
            case _   => //do nothing
          }
          
        }
            mapFile.close()
      }
    }catch{
      case e:FileNotFoundException => println("map file \""+fileName+"\" does not exist")
      case e:UnsupportedOperationException => println("map is too small")
      case e:IOException => println("something went wrong")
      
    }
    
    newSpace
  } 
  
}