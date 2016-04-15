package space

import space.Position
import GUI._
import scala.swing.Color
import scala.math.{Pi, tan, sin, cos, pow, sqrt, min, abs}
import scala.collection.mutable.Buffer


/**
 * @author eero
 */
class Camera (val space: Space, var position: Position){
  
  def cubeSize=this.space.cubeSize
  var renderingDistance = 20
  
  
  val wallHeight = 100 //TODO get this from space
  
  //var directionInWorld = Pi/2 // testing reasons
  
  def absolutePosX = this.position.x*cubeSize+(cubeSize.toDouble/2)
  def absolutePosY = this.position.y*cubeSize+(cubeSize.toDouble/2)  
  
 

 def shoot(sizeX: Int, sizeY: Int, fieldOfView: Int, facingDirection: Double) = {
   var directionInWorld=facingDirection
    //normalizing the direction
    while(directionInWorld>2*Pi)directionInWorld-=2*Pi
    while(directionInWorld<2*Pi)directionInWorld+=2*Pi    
    
    
    var canvas=Array.ofDim[Color](sizeX, sizeY)
    val FOV = fieldOfView.toRadians
    val dAngle = FOV/sizeX.toDouble
    var a = directionInWorld-(FOV/2)  
    
    val distanceToProjectionPlane=sizeX/(2*tan(FOV/2))
    
    
    for(i <-0 until sizeX){
      var distance=this.shootRay(a)*cos( (directionInWorld)-a ) // fishbowl efect correction, check if not working
      a+=dAngle
      //println("chosen distance: "+distance)
      //println()
      
      //TODO do something with the distance
      var slice=Array.ofDim[Color](sizeY)
      var sliceHeight=(this.wallHeight/distance)*distanceToProjectionPlane
      
      for(j <- 0 until slice.length){
        if( j<(sizeY/2)-(sliceHeight/2) || j>(sizeY/2)+(sliceHeight/2) ) canvas(sizeX-1-i)(j) = new Color(255*abs(sizeY/2-j)/sizeY,0,0)
        else canvas(sizeX-1-i)(j) = new Color(0,(min(255,100*(100/distance)).toInt),0)
      }
    }

  canvas  
  }
  
  
  def shootRay(shootingDirection: Double): Double={
  // first we check the horizontal intersections
    var direction=shootingDirection
   //normalizing the direction
    while(direction>2*Pi)direction-=2*Pi
    while(direction<0)direction+=2*Pi
   
    
    //println("current direction in rad: "+ direction.toString())
    
    var pointY = 0.0
    var pointX = 0.0
    
    var hDistance = 0.0
    
  //if 0 or 180 degrees there is no horizontal intercourses
  if(direction != 0 && direction != Pi) { 
    var dY=0
    
  //if the ray is facing up
    if(direction>0 && direction<Pi){ 
      pointY=this.position.y*this.cubeSize-1
      dY=(-this.cubeSize)
      //println("Up")
    }
    //if the ray is facing down
    else if(direction>Pi && direction < (2*Pi)) {
      pointY=this.position.y*cubeSize+cubeSize+1 // just to make sure it is in the lower cube
      dY=this.cubeSize
      //println("down") 
    }
   
    pointX=this.absolutePosX+((this.absolutePosY-pointY)/tan(direction))
    
    
    var dX= -dY/tan(direction) //minus added to fix some strange ghost walls appearing everywhere
      
      var n=1
    
    while(hDistance==0/* && n <= renderingDistance*/){  
            n+=1                  //here we had a bad mistake, do double chec if not working
      //println(this.space.isWall(pointX,pointY)+"in position: " + pointX +" " + pointY)
      if(this.space.isWall(pointX,pointY)) hDistance = abs(  (this.absolutePosY-pointY)/sin(direction)  )
      else {
        pointX+=dX
        pointY+=dY
      }
    }
    //println("Horizontal distance: "+hDistance)   
  } 
    
    
    //then we find the vertical intersection points
    pointX=0.0
    pointY=0.0
    var vDistance=0.0
    
    //if 90 or 270 degrees no intersections
    if(direction != Pi/2 && direction != (3*Pi)/2){
      
      var dX=0.0
      
      //facing right
      if(direction<(Pi/2) || direction>(3*Pi)/2){
        pointX=this.position.x*cubeSize+cubeSize+1 //+1 to make sure we are in the next cube  
        dX=this.cubeSize
        //println("right")  
      } else if(direction > (Pi/2) && direction < (3*Pi)/2){
        pointX=this.position.x*this.cubeSize-1
        dX=(-this.cubeSize)
        //println("left")
      }
      
      pointY = this.absolutePosY+(this.absolutePosX-pointX)*tan(direction)
      
      var dY = -dX*tan(direction)
      
      
      var n = 1
      
      while(vDistance==0 /*&& n<= this.renderingDistance*/){
        n+=1
        //println(this.space.isWall(pointX,pointY)+" in position: " + pointX +" " + pointY)
        if(this.space.isWall(pointX,pointY)) vDistance = abs(  (this.absolutePosX-pointX)/cos(direction)  )
        else {
          pointX+=dX
          pointY+=dY
          n+=1
        }
      }
      //println("vertical distance: "+ vDistance) 
    }
    
    if(hDistance==0) vDistance
    else if (vDistance==0) hDistance
    else min(hDistance,vDistance)
    
    
  }
  
  
  def drawMinimap(canvas: Array[Array[Color]],posX: Int, posY: Int, size: Int) = ???
 
  
  def moveTo(newPosition: Position)={
    if(!this.space.isWall(newPosition)) this.position=newPosition
  }
  
    def move(xChange: Int, yChange: Int)={
      var newPosition = new Position(this.position.x+xChange,this.position.y+yChange)
      this.moveTo(newPosition)
  }
  
  
  
  

  
  
  
  
}