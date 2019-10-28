class Satellite{
  
  int x;
  int y;
  int size;
  int counter=0;
  PImage SatellitePhase1;
  PImage SatellitePhase2;
  PImage SatellitePhase3;
  PImage SatellitePhase4;
        
  Satellite(int x, int y, int size, PImage SatellitePhase1, PImage SatellitePhase2, PImage SatellitePhase3,PImage SatellitePhase4){
    
    this.x =x;
    this.y =y;
    this.size = size;
    this.SatellitePhase1 =SatellitePhase1;
    this.SatellitePhase2 =SatellitePhase2;
    this.SatellitePhase3 =SatellitePhase3;
    this.SatellitePhase4 =SatellitePhase4;
    
  }
  
  void render(){
    
   imageMode(CENTER);
   display();
  
  }
  
  void display(){
    
    if(counter < 10 ){
      image(SatellitePhase1,x,y,60,60);
     
    }else if(counter < 20){
      image(SatellitePhase2,x,y,60,60);
    
    }else if(counter < 20){
      image(SatellitePhase3,x,y,60,60);
    
    }else if(counter < 30){
      image(SatellitePhase4,x,y,60,60);
     
    }else{
      counter = 0;
    }
    counter+= 1;
  
  }

}
