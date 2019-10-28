class BulletBubble{
  int x,y;
  int speedx;
  int size=80;
  int counter = 0;
  
  PImage bulletPhase1;
  PImage bulletPhase2;
  
  BulletBubble(int x, int y, int speedx, PImage bulletPhase1, PImage bulletPhase2){
    this.x = x;
    this.y = y;
    this.speedx= speedx;
    this.bulletPhase1 = bulletPhase1;
    this.bulletPhase2 = bulletPhase2; 
  
  }
  
  public void display(){  
    bulletAnimation();
  }
  
  public void bulletAnimation(){
    counter +=1;
    if(counter < 10){
      image(bulletPhase1, x, y, size,size);
    }
  
  }
 
}
