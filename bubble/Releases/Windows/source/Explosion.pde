class Explosion{
  
  float x;
  float y;
  int size =90;
  PImage explosion1;
  PImage explosion2;
  int counter =0;
  
  Explosion(float x, float y, PImage explosion1, PImage explosion2){
    
    this.x = x;
    this.y = y;
    this.explosion1 = explosion1;
    this.explosion2 = explosion2;
    
  }
  
  void render(){
    explosionAnimation();
  }
  
  void explosionAnimation(){
  
    counter +=1;
    
    if(counter < 10){
      
      image(explosion1, x, y, size/2, size/2);
      
    }else if(counter < 15){
      image(explosion1, x, y, size, size);
    }
  }

}
