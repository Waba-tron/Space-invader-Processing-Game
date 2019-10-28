PImage spaceBackground;
int bgY=0; //global variable background location

final boolean inGame = true;
final boolean gameOver = false;
boolean gamePlaying = gameOver;

int Special = 0;
int specialAmmo = 5; //special move ammo
int specialAmmoAmount = 5; // shows the ammount of special bullets in string form
int countSpecial = 1;
int numberOfEnemies = 3; // starting amount for number of enermies
int levelNumber = 1;

Bubbles b1;
Satellite Satellite1, Satellite2; 
SplashScreen splashScreenObject;
ArrayList<EnermyBubble> enermybubble;
ArrayList<BulletBubble> BULLETS;
ArrayList<Explosion> explosion;
ArrayList<PowerUps> Powers;
SpecialMove [] specialmove = new SpecialMove[2000000];

PImage spaceShip;
PImage alienPhase1;
PImage alienPhase2;
PImage bulletPhase1;
PImage bulletPhase2;
PImage sbPhase1;
PImage sbPhase2;
PImage explosion1;
PImage explosion2;
PImage sat1;
PImage sat2;
PImage sat3;
PImage sat4;

int lifes = 10;
int Score = 0;

void setup(){
  
   size(500,700);
   spaceBackground = loadImage("backgroundSpace.png");
   spaceShip = loadImage("PlayerShip.png");
   alienPhase1 = loadImage("test.PNG");
   alienPhase2 = loadImage("phase2.PNG");
   bulletPhase1 = loadImage("sb3.png");
   bulletPhase2 = loadImage("sb4.png");
   sbPhase1 = loadImage("sb1.png");
   sbPhase2 = loadImage("sb2.png");
   explosion1 = loadImage("ex2.png");
   explosion2 = loadImage("ex3.png");
   sat1 = loadImage("Sat1.png");
   sat2 = loadImage("Sat2.png");
   sat3 = loadImage("Sat3.png");
   sat4 = loadImage("Sat4.png");
   
   splashScreenObject = new SplashScreen();
   enermybubble = new ArrayList<EnermyBubble>();
   explosion = new ArrayList<Explosion>();
   BULLETS = new ArrayList<BulletBubble>();
   Powers = new ArrayList<PowerUps>();
   b1 = new Bubbles(600,spaceShip);
   Satellite1 = new Satellite(450,650,50,sat1,sat2,sat3,sat4);
   Satellite2 = new Satellite(50,650,50,sat1,sat2,sat3,sat4);
   
   for(int i = 0; i < specialmove.length; i++){
     specialmove[i] = new SpecialMove(mouseX, 600, 6);
     
   }

}

void draw(){
  
  splashScreenObject.Screen(); //Staring screen
  
  if(gamePlaying == inGame){
    drawBackground();
    textSize(20);
    fill(50,232,65);
    text("Score: "+Score, 330,50);
    text("Lifes: "+ lifes, 330, 80);
    text("Special Move: "+ specialAmmoAmount, 330, 110);
    enermyGoal();
    b1.render(); //Fighter object
    Satellite1.render();//Satellite objects
    Satellite2.render();
    levelSystem(); //Multiple level System

    //adds explosions in to the game
    for(Explosion Explosions : explosion){
      Explosions.render();
    }
    
    //Shooting bullets function
    for(int i=0; i < BULLETS.size(); i++){
      
      BulletBubble normalBullet = BULLETS.get(i);
      normalBullet.display();
      
    }   
      
     //display special move 
    for(int i = 0; i < Special; i++){
      specialmove[i].display();
    }

    //Funtions for when aliens spwarn
    for(int i =0; i < numberOfEnemies; i++){
      enermybubble.add(new EnermyBubble(alienPhase1, alienPhase2)); 
     
      EnermyBubble eb = enermybubble.get(i);
      eb.eRender();
    
    // When alien is hit, alien is then removed from the array
      if(eb.hitBullet()){    
        explosion.add(new Explosion(eb.x, eb.y, explosion1, explosion2)); //add explosion when bullet hits alien
        enermybubble.remove(i);
        Score+=10;
      }
      
      // When alien hits fighter object, you lose a life and alien is removed  
      if(eb.hitFighter()){
        explosion.add(new Explosion(b1.x, b1.y, explosion1, explosion2)); //add explosion when fighter hits alien
        enermybubble.remove(i);
        lifes -= 2;
        
      }
      // When alien hits Satellite, you lose a life and alien is removed  
      if(eb.destroySatellite()){
        explosion.add(new Explosion(eb.x, eb.y, explosion1, explosion2)); //add explosion when satellite hits alien
        enermybubble.remove(i);
        lifes -= 1;
      
      }
    
    }
 //<>//
    }
    gameOver(); //display gameover screen
  
}

  void drawBackground(){
    image(spaceBackground, 0, bgY); //draw background twice adjacent
    image(spaceBackground, 0, bgY+spaceBackground.height);
    bgY +=2;
   
    if(bgY == +spaceBackground.height-500){
    bgY=0; //wrap background
    }

  }

  void shoot() {
    BULLETS.add(new BulletBubble(mouseX, mouseY,6, bulletPhase2, bulletPhase2)); // shoots normal bullet object
    
  }
  
  void shootSpecial(){
    
    if(Special < specialAmmo){
      
      Special+=countSpecial; // counts each special move and stops adding the object after limit, to top null point exception error
      specialAmmoAmount -=1; //decreases ammo number in text form to up to zero
   }
   
  }
  
  void powerUpSystem(){
    
    //ploymorfism
    
    //treats them as different objects
    for(int i = 0; i < 1; i++){
      
      float Num = random(1);
      
      if(Num < 0.5){ //display random power up
  
        Powers.add(new lifeUp(3));
      }
      else{
        
        Powers.add(new specialUp(3));
      }
      
      PowerUps pu = Powers.get(i);
      pu.render();
      
      if(b1.lifePowerUp()){  //when fighter hits power up gains a +1 life
        
        lifes+=1;
        Powers.remove(i); 
        
      }
      if(b1.specialPowerUp()){
       
        specialAmmo +=1;     //when fighter hits power up gains a +1 special move
        specialAmmoAmount +=1;
        Powers.remove(i); 
      
      }
    
    }
    
  }
  
  void enermyGoal(){
    stroke(50,232,65);
    strokeWeight(10);
    text("Defend Satellites", 175,660);
    line(100, 670, 400, 670);
  
  }
  
  void levelSystem(){
    
    fill(50,232,65);
    textSize(20);
    text("Level "+levelNumber, 50,50); 
    
    if(Score >= 0){
      levelNumber =1;
      
    }
    if(Score >= 150){
      levelNumber = 2;
      numberOfEnemies =5;
      //Level 2
      
    }
    if(Score >= 300){
    
      levelNumber = 3;
      numberOfEnemies = 7;
      //Level 3
      powerUpSystem(); //power ups will be avaliable on level 3
     
    }
    
  }
  
  
  void gameOver(){  //displays game over screen
    
    if(lifes == 0){
        
        background(0,0,0);
        textSize(20);
        fill(50,232,65);
        text("Game Over", 200, 300);
        text("Score: "+Score, 200, 350);
        text("Press SpaceBar to play again", 130, 400);
        gamePlaying = gameOver;
      
      }
  
  }

  void mousePressed(){
    
    switch(mouseButton){
    
      case LEFT:
      shoot();
      break;
      
      case RIGHT:
      shootSpecial();
      break;
      
    }
  
  } 
  
  void keyReleased(){
    //Restart Method
    if(keyCode == ' ' && gamePlaying == gameOver){
      setup();  //resets all objects to where they orginally came from  
      gamePlaying = inGame; 
      Score =0;
      lifes = 10;
      Special = 0;
      specialAmmo = 5;
      specialAmmoAmount = 5;
      numberOfEnemies = 3;
   
    }
}
 
