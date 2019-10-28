class Bubbles{
  
  float x,y;
  int moveY;
  PImage spaceShip;
  color lifeUpC = color(210,210,210);
  color specialMoveUp = color(255,255,150);
  
  Bubbles(int y, PImage spaceShip){
 
    this.y = y;
    this.spaceShip = spaceShip;
  }
  
  public void render(){
     direction();
     display();
   }
   
  private void direction(){
     x = lerp(x, mouseX, 0.05);

   }
   
  private void display(){
     imageMode(CENTER);
     image(spaceShip,x,y,80,80);
   }
  
  boolean lifePowerUp(){
    color detectedColour;
    for (float i = x; i < x+50; i++){
      detectedColour = get((int)i,(int)y-20);
      
      if(detectedColour == lifeUpC ){
        return true;
      }
    }
    return false;
  }
  
  boolean specialPowerUp(){
    color detectedColour;
    for (float i = x; i < x+50; i++){
      detectedColour = get((int)i,(int)y-20);
      
      if(detectedColour == specialMoveUp ){
        return true;
      }
    }
    return false;
  }

}
