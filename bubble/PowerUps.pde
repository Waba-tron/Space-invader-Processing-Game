class PowerUps{
  
  float x;
  float y;
  int speed =7;
  
  
  PowerUps(int speed){
    x = random(0+20, width-20);
    y = random(-1000, -100);
    this.speed = speed;

  }
  
  public void desplay(){
    ellipse(x,y,70,70);
 
  }
  
  private void move(){
    y += speed;
  }
  
  private void offScreen(){
    
    if(y > height){
      
      x = random(0+20, width-20);
      y = random(-1000, -100);
    
    }
  
  }
  
  public void render(){
  desplay();
  move();
  offScreen();
  }
  

}
