package space

import scala.math.{sqrt, pow, abs, Pi, tan}
/**
 * @author eero
 * Position is the x y coordinates in the world by cube units
 */
class Position(val x: Int, val y: Int) {
  
  def distance(other: Position) = sqrt(pow((other.x-this.x).toDouble,2)+pow((other.y-this.y).toDouble,2))
  
}