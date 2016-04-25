package space

import scala.io.Source

/**
 * @author eero
 */
class Space(val dimX: Int, val dimY: Int){
  
  var cubeSize=100
  
  
  
  //val grid = Array.ofDim[Cube](dimX, dimY) //Blocks in the world
  val grid = Array.ofDim[Boolean](dimX, dimY)
  
  
  
  
  def addWall(x: Int, y: Int) = {
    grid(x)(y)=true
    //grid(x)(x)=new Cube(this, new Position(x,y))
    //grid(x)(y).makeWall() //TODO make sure not to go out of the world, or make the world expand
  }
  
  
  def removeWalls(x: Int, y: Int) = {
   grid(x)(y)=false
    // grid(x)(y).removeWall()
  }
  
  
  def isWall(position: Position) = if(position.x<0 || position.y<0 || position.x > dimX-1 || position.y > dimY-1) true else grid(position.x)(position.y) //grid(position.x)(position.y).isWall
  
  def isWall(x: Double, y: Double): Boolean = {
    if(x<0 || y<0) true else this.isWall(new Position((x/this.cubeSize).toInt,(y/this.cubeSize).toInt))
   
    //if(x<0 || y<0 || x > (dimX-1)*cubeSize || y > (dimY-1)*cubeSize) true else grid(((x/this.cubeSize)).toInt)(((y/this.cubeSize)).toInt) // toInt round down, right?
  
  //old method:  grid(((x/this.cubeSize)+1).toInt)(((y/this.cubeSize)+1).toInt).isWall  
  }
  
  /**for testing purposes*/
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
    //TODO generate from file
  def generateFromFile(fileName: String): Space={
    
    val mapFile = Source.fromFile(fileName)
    val rows=mapFile.getLines().toArray
    val newSpace = new Space(rows.maxBy(_.length).length(),rows.length)
    
    for(i <- 0 until rows.length){
      for(j <- 0 until rows(i).length){
       if(rows(i)(j)=='1') newSpace.addWall(j, i) 
      }
    }
    mapFile.close()
    newSpace
  } 
  
}