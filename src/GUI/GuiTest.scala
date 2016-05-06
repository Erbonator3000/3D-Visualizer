package GUI

import scala.swing._
import scala.swing.Color
import scala.swing.event._
import java.awt.Event
import space._
import GUI._
import scala.math.Pi

import java.awt.event.ActionListener

/**
 * @author eero
 */
object GuiTest extends SimpleSwingApplication {
  
  val width      = 600
  val height     = 600
  val fullHeight = 610
  
 
  
  val window = new MainFrame{
  
    //generate the world
    val maailma = Space.generateFromFile("Map")

    //set the camera
    val kamera = new Camera(maailma, this.maailma.startingPosition)
    
    //set camera facing up
    var currentAngle=Pi-0.5
    
    //shoot the starting screen
    var picture = kamera.shoot(500, 400, 60, currentAngle)

    
    minimumSize   = new Dimension(width,fullHeight)
    preferredSize = new Dimension(width,fullHeight)
    maximumSize   = new Dimension(width,fullHeight)
    
    //generate the canvas to be drawn
    val canvas: Canvas = new Canvas(picture){
      
      focusable=true
      
      listenTo(keys)
      reactions+={
        case KeyPressed(_,Key.Left,_,_)=>
          turn(Pi/16)
        
       case KeyPressed(_,Key.Right,_,_)=>
          turn(-(Pi/16))
          
        case KeyPressed(_,Key.Up,_,_)=>
          walk(1,0)
        
       case KeyPressed(_,Key.Down,_,_)=>
          walk(-1,0)
          
        case KeyPressed(_,Key.W,_,_)=>
          walk(1,0)
        
       case KeyPressed(_,Key.S,_,_)=>
          walk(-1,0)
          
       case KeyPressed(_,Key.A,_,_)=>
          walk(0,-1)
        
       case KeyPressed(_,Key.D,_,_)=>
          walk(0,1)
      }
    }
        
    contents=canvas
  
     
  
     //perform walking operation  
    def walk(speed: Int, sideStep: Int)={
      if(3*Pi/4>currentAngle && currentAngle>=Pi/4) kamera.move(sideStep,-1*speed) //facing up
      else if(3*Pi/4<=currentAngle && currentAngle<5*Pi/4) kamera.move(-1*speed,-1*sideStep) //facing left
      else if(7*Pi/4>currentAngle && currentAngle>=5*Pi/4) kamera.move(-1*sideStep,speed) //facing down
      else if(currentAngle>=7*Pi/4 || currentAngle<Pi/4) kamera.move(speed,sideStep) //facing right
        
      //after moved, shoot a new picture of the world
      picture = kamera.shoot(200, 200, 60, currentAngle)
      this.canvas.update(picture)
      this.canvas.repaint()
    }
 
    //perform turning operation
    def turn(direction: Double)={
      currentAngle+=direction
      while(currentAngle<0)currentAngle+=2*Pi
      while(currentAngle>2*Pi)currentAngle-=2*Pi

      picture = kamera.shoot(200, 200, 60, currentAngle)
      this.canvas.update(picture)
      this.canvas.repaint() 
    }
  
  

  }
  def top = this.window
 
}