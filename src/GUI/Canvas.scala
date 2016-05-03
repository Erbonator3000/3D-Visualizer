package GUI

import scala.swing._
/**
 * @author eero
 * Simple canvas to display Color arrays
 */
class Canvas(var pixels: Array[Array[Color]]) extends Panel {
  
  var backroundColor = new Color(0,0,0)
  
  def update(canvas: Array[Array[Color]]){
    pixels=canvas
  }
  
  def dimX = pixels.length
  def dimY = pixels.map(_.length).max
  
  override def paintComponent(g: Graphics2D){
   val dx = g.getClipBounds.width.toFloat  / dimX
   val dy = g.getClipBounds.height.toFloat / dimY
    
    for(x <- 0 until dimX; y <- 0 until dimY){
      pixels(x)(y) match{
        case c: Color => g.setColor(c)
        case _ => g.setColor(backroundColor)
      }
      
      g.fillRect((x*dx).toInt, (y*dy).toInt, ((x+1)*dx).toInt, ((y+1)*dy).toInt)

    } 
  }  
}

