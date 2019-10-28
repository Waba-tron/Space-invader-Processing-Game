class specialUp extends PowerUps{
  
color specialMoveUpC = color(255,255,150);

  
  specialUp(int speed){
   super(speed);
  
  }
  
  void desplay(){
    
    powerUpType(); //color
    super.desplay();
    fill(0,0,0);
    text("Special+", x-30, y);
  }
  
  void powerUpType(){
    
    stroke(specialMoveUpC);
    fill(specialMoveUpC);
    textSize(15);

  }
  
}
