class EnermyBubble{
 
  float x,y;
  float speed = 6;
  color normalBulletC = color(255,255,238); //orange bullet
  color specialBulletC = color(238,255,255); //blue bullet
  color fighterColor1 = color(0,82,1);
  color fighterColor2 = color(0,133,2);
  int counter =0;
  float randomDirection = random(1);
  private PImage alienPhase1;
  private PImage alienPhase2;
  
  EnermyBubble(PImage alienPhase1, PImage alienPhase2){
    
    this.alienPhase1 = alienPhase1;
    this.alienPhase2 = alienPhase2;
    x = random(0+50, width-50);
    y = random(-1000, -100);

  }
  
  void eRender(){
    
    moveDown();
    imageMode(CENTER);
    desplay();
    changeDirection();


  }

    private void changeDirection(){
  
    if(y > height/3){
      
      if(randomDirection < 0.5){
      x = lerp(x, 20, 0.03);
      
      }else{
      x = lerp(x, width-20, 0.03);
      }
      
    }
    
    // make enemies move towards the goal
    
  }
  
  private void desplay(){
    
    if(counter < 10 ){
      image(alienPhase1,x,y,60,60);
      
    }else if(counter < 20){
      image(alienPhase2,x,y,60,60);
    
    }else{
      counter = 0;
    }
    
    counter+= 1;
   
  }
  
  void moveDown(){
    y += speed;
  }
  
  
  boolean destroySatellite(){
    if(x < 80 && y > height -80 || x > width-80 && y > height -80){
      return true;
    }
    return false;
  }
  
  boolean hitBullet(){
    
    color detectedColour;
    for (float i = x; i < x+80; i++){
      detectedColour = get((int)i-30,(int)y);
      if(detectedColour == normalBulletC || detectedColour == specialBulletC ){
        return true;
      }
    }
    return false;
    }
  
  boolean hitFighter(){
    
    color detectedColour;
    for (float i = x; i < x+100; i++){
      detectedColour = get((int)i-50,(int)y+20);
    
      if(detectedColour == fighterColor1 || detectedColour == fighterColor2 ){
        return true;
      }
    }
    return false;
    }
  
  }
    
