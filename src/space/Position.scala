package space

import scala.math.{sqrt, pow, abs, Pi, tan}
/**
 * @author eero
 * Position is the x y cordinates in the world bu cube units
 */
class Position(val x: Int, val y: Int) {
  
  def distance(other: Position) = sqrt(pow((other.x-this.x).toDouble,2)+pow((other.y-this.y).toDouble,2))
  def direction(other: Position) = ???
  
}