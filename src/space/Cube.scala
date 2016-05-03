package space

/**
 * @author eero
 * I guess that this is never used..
 */
class Cube(val space: Space, val position: Position) {
  
  private var wall = false
  
  def isWall=this.wall
  
  def makeWall() = {
    this.wall=true
  }
  def removeWall() = {
    this.wall=false
  }
  
  
}