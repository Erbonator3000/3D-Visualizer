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
  
 /* val picture = Array.ofDim[Color](100,100)
  picture(3)(5) = new Color(0.5f, 0.5f, 0.5f)
  picture(4)(5) = new Color(0.5f, 0.5f, 0.5f)  
  */
    val maailma = Space.generateFromFile("testMap")
   /* maailma.addWall(1, 0)
    maailma.addWall(1, 1)
    maailma.addWall(1, 2)
    maailma.addWall(2, 0)
    maailma.addWall(2, 2)
    maailma.addWall(3, 0)
    maailma.addWall(3, 1)
    maailma.addWall(3, 2) 
    
   
        
    val kamera=new Camera(maailma, new Position(2,1))
*/
  /*  
   for(i <- 1 to 10){
     maailma.addWall(0,i)
     maailma.addWall(2, i) 
   }
    maailma.addWall(0,0)
    maailma.removeWalls(0, 3)
    maailma.addWall(1, 9)
    maailma.removeWalls(2, 5)
    maailma.addWall(3,4)
    maailma.addWall(3,6)
    maailma.addWall(4, 4)
    maailma.addWall(5, 5)
    maailma.addWall(5, 6)    
    maailma.addWall(5, 7)*/
    
    val kamera = new Camera(maailma, new Position(2,2))
    
    var currentAngle=Pi-0.5
    
    var picture = kamera.shoot(500, 400, 60, currentAngle)

  
      minimumSize   = new Dimension(width,fullHeight)
    preferredSize = new Dimension(width,fullHeight)
    maximumSize   = new Dimension(width,fullHeight)
  
    val canvas: Canvas = new Canvas(picture){
      focusable=true
      listenTo(keys)
      reactions+={
        case KeyPressed(_,Key.Left,_,_)=>
          turn(Pi/16)
        
       case KeyPressed(_,Key.Right,_,_)=>
          turn(-(Pi/16))
          
        case KeyPressed(_,Key.Up,_,_)=>
          walk(1)
        
       case KeyPressed(_,Key.Down,_,_)=>
          walk(-1)
      }
    }
        
  contents=canvas
  
      def walk(speed: Int)={
      if(3*Pi/4>currentAngle && currentAngle>=Pi/4) kamera.move(0,-1*speed)
      else if(3*Pi/4<=currentAngle && currentAngle<5*Pi/4) kamera.move(-1*speed,0)
      else if(7*Pi/4>currentAngle && currentAngle>=5*Pi/4) kamera.move(0,1*speed) 
      else if(currentAngle>=7*Pi/4 || currentAngle<Pi/4) kamera.move(1*speed,0)
        
      
      picture = kamera.shoot(200, 200, 60, currentAngle)
      this.canvas.update(picture)
      this.canvas.repaint()
  }
  
      def turn(direction: Double)={
        currentAngle+=direction
        while(currentAngle<0)currentAngle+=2*Pi
        while(currentAngle>2*Pi)currentAngle-=2*Pi

        println(currentAngle)
        picture = kamera.shoot(200, 200, 60, currentAngle)
        this.canvas.update(picture)
        this.canvas.repaint() 
  }
  
  
 
        // Tämä tapahtumankuuntelija ja swing timer mahdollistavat määräajoin toistuvan
    // toiminnan tapahtumankuuntelusäikeessä. Kello on riittävän kevyt piirtää
    // säikeessä ilman lisäpuskureita tai lisäsäikeitä
    
    val listener = new ActionListener(){
      def actionPerformed(e : java.awt.event.ActionEvent) = {
        currentAngle+=Pi/32
        picture = kamera.shoot(200, 200, 60, currentAngle)
        canvas.update(picture)
       // println("repaint")
        canvas.repaint() 
      }  
    }
/*
    // Timer lähettää ActionListenerille ActionEventin n. 500ms välein,
    // jolloin avaruus liikahtaa eteenpäin ja ruutu piirretään uudelleen.
    // Tämä koodi siis mahdollistaa animaation
    val timer = new javax.swing.Timer(150, listener)
    timer.start()  
  */
  
  

  }
  def top = this.window
  
 
  
}