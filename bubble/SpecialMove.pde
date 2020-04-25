class SpecialMove extends BulletBubble {

  SpecialMove(int x, int y, int speedx){
  
  super(x,y,speedx, sbPhase1, sbPhase2);
  
  }
  
  void display(){ 
   //inheritance 
   x = 10;
   size = 70;
   
   for(int i = 0; i < 20; i++){
     super.display();
     x+=size;
   }
   bulletAnimation();
   move();

  }
  
  void bulletAnimation(){
    counter +=1;
    if(counter < 10){
      image(bulletPhase1, x, y, size,size);
      
    }else if (counter < 20){
      image(bulletPhase2, x, y, size,size);
      
    }else{
      counter = 0;
    }
  
  }
  
  void move(){
    y = y - speedx;
  }
  
  
  boolean Range(){
    if (y < 0){
      return true;
    }
    return false;
  }
    
}
