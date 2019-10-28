class lifeUp extends PowerUps{
  
  color lifeUpC = color(210,210,210);
  
  lifeUp(int speed){
    super(speed);
  
  }
  
  void desplay(){
    
    powerUpType();
    super.desplay(); //color
    fill(0,0,0);
    text("Life+", x-20, y);
 
  }
  
  void powerUpType(){
    stroke(lifeUpC);
    fill(lifeUpC);
    textSize(17);

  }
  
  
  }
  
