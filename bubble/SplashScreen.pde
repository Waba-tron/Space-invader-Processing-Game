class SplashScreen{
  
  int counter=0;
  int counter2 =0;
  String Play ="Press SpaceBar to Play";
  int Size = 200;


  public void Screen(){
    background(0,0,0);
    fill(50,232,65);
    mainTitle();
    spaceBar();
    splashScreenAlien();
      
  }
  
  void mainTitle(){
    textSize(30);
    text("WABA-TRON INVADERS ", 90, 150);

  }
  
  void spaceBar(){
    textSize(20);
    if(counter < 10){
      Play ="Press SpaceBar to Play";
      text(Play, 150, 500);
      
    }else if(counter < 20){
      Play ="";
      
    }else{
      counter = 0;
    }
    counter+=1;
  }
  
  void splashScreenAlien(){
    if(counter2 < 10){
      image(alienPhase1,150,250,Size,Size);
      
    }else if(counter2 < 30){
      image(alienPhase2,150,250,Size,Size);
      
    }else{
      counter2 =0;
    }
    counter2+=1;
    
  
  }
}
