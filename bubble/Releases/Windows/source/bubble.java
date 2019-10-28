import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class bubble extends PApplet {

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

public void setup(){
  
   
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

public void draw(){
  
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

    }
    gameOver(); //display gameover screen
  
}

  public void drawBackground(){
    image(spaceBackground, 0, bgY); //draw background twice adjacent
    image(spaceBackground, 0, bgY+spaceBackground.height);
    bgY +=2;
   
    if(bgY == +spaceBackground.height-500){
    bgY=0; //wrap background
    }

  }

  public void shoot() {
    BULLETS.add(new BulletBubble(mouseX, mouseY,6, bulletPhase2, bulletPhase2)); // shoots normal bullet object
    
  }
  
  public void shootSpecial(){
    
    if(Special < specialAmmo){
      
      Special+=countSpecial; // counts each special move and stops adding the object after limit, to top null point exception error
      specialAmmoAmount -=1; //decreases ammo number in text form to up to zero
   }
   
  }
  
  public void powerUpSystem(){
    
    //ploymorfism
    
    //treats them as different objects
    for(int i = 0; i < 1; i++){
      
      float Num = random(1);
      
      if(Num < 0.5f){ //display random power up
  
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
  
  public void enermyGoal(){
    stroke(50,232,65);
    strokeWeight(10);
    text("Defend Satellites", 175,660);
    line(100, 670, 400, 670);
  
  }
  
  public void levelSystem(){
    
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
  
  
  public void gameOver(){  //displays game over screen
    
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

  public void mousePressed(){
    
    switch(mouseButton){
    
      case LEFT:
      shoot();
      break;
      
      case RIGHT:
      shootSpecial();
      break;
      
    }
  
  } 
  
  public void keyReleased(){
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
 
class Bubbles{
  
  float x,y;
  int moveY;
  PImage spaceShip;
  int lifeUpC = color(210,210,210);
  int specialMoveUp = color(255,255,150);
  
  Bubbles(int y, PImage spaceShip){
 
    this.y = y;
    this.spaceShip = spaceShip;
  }
  
  public void render(){
     direction();
     display();
   }
   
  private void direction(){
     x = lerp(x, mouseX, 0.05f);

   }
   
  private void display(){
     imageMode(CENTER);
     image(spaceShip,x,y,80,80);
   }
  
  public boolean lifePowerUp(){
    int detectedColour;
    for (float i = x; i < x+50; i++){
      detectedColour = get((int)i,(int)y-20);
      
      if(detectedColour == lifeUpC ){
        return true;
      }
    }
    return false;
  }
  
  public boolean specialPowerUp(){
    int detectedColour;
    for (float i = x; i < x+50; i++){
      detectedColour = get((int)i,(int)y-20);
      
      if(detectedColour == specialMoveUp ){
        return true;
      }
    }
    return false;
  }

}
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
class EnermyBubble{
 
  float x,y;
  float speed = 6;
  int normalBulletC = color(255,255,238); //orange bullet
  int specialBulletC = color(238,255,255); //blue bullet
  int fighterColor1 = color(0,82,1);
  int fighterColor2 = color(0,133,2);
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
  
  public void eRender(){
    
    moveDown();
    imageMode(CENTER);
    desplay();
    changeDirection();


  }

    private void changeDirection(){
  
    if(y > height/3){
      
      if(randomDirection < 0.5f){
      x = lerp(x, 20, 0.03f);
      
      }else{
      x = lerp(x, width-20, 0.03f);
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
  
  public void moveDown(){
    y += speed;
  }
  
  
  public boolean destroySatellite(){
    if(x < 80 && y > height -80 || x > width-80 && y > height -80){
      return true;
    }
    return false;
  }
  
  public boolean hitBullet(){
    
    int detectedColour;
    for (float i = x; i < x+80; i++){
      detectedColour = get((int)i-30,(int)y);
      if(detectedColour == normalBulletC || detectedColour == specialBulletC ){
        return true;
      }
    }
    return false;
    }
  
  public boolean hitFighter(){
    
    int detectedColour;
    for (float i = x; i < x+100; i++){
      detectedColour = get((int)i-50,(int)y+20);
    
      if(detectedColour == fighterColor1 || detectedColour == fighterColor2 ){
        return true;
      }
    }
    return false;
    }
  
  }
    
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
  
  public void render(){
    explosionAnimation();
  }
  
  public void explosionAnimation(){
  
    counter +=1;
    
    if(counter < 10){
      
      image(explosion1, x, y, size/2, size/2);
      
    }else if(counter < 15){
      image(explosion1, x, y, size, size);
    }
  }

}
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
  
  public void render(){
    
   imageMode(CENTER);
   display();
  
  }
  
  public void display(){
    
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
class SpecialMove extends BulletBubble {

  SpecialMove(int x, int y, int speedx){
  
  super(x,y,speedx, sbPhase1, sbPhase2);
  
  }
  
  public void display(){ 
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
  
  public void bulletAnimation(){
    counter +=1;
    if(counter < 10){
      image(bulletPhase1, x, y, size,size);
      
    }else if (counter < 20){
      image(bulletPhase2, x, y, size,size);
      
    }else{
      counter = 0;
    }
  
  }
  
  public void move(){
    y = y - speedx;
  }
  
  
  public boolean Range(){
    if (y < 0){
      return true;
    }
    return false;
  }
    
}
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
  
  public void mainTitle(){
    textSize(30);
    text("WABA-TRON INVADERS ", 90, 150);

  }
  
  public void spaceBar(){
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
  
  public void splashScreenAlien(){
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
class lifeUp extends PowerUps{
  
  int lifeUpC = color(210,210,210);
  
  lifeUp(int speed){
    super(speed);
  
  }
  
  public void desplay(){
    
    powerUpType();
    super.desplay(); //color
    fill(0,0,0);
    text("Life+", x-20, y);
 
  }
  
  public void powerUpType(){
    stroke(lifeUpC);
    fill(lifeUpC);
    textSize(17);

  }
  
  
  }
  
class specialUp extends PowerUps{
  
int specialMoveUpC = color(255,255,150);

  
  specialUp(int speed){
   super(speed);
  
  }
  
  public void desplay(){
    
    powerUpType(); //color
    super.desplay();
    fill(0,0,0);
    text("Special+", x-30, y);
  }
  
  public void powerUpType(){
    
    stroke(specialMoveUpC);
    fill(specialMoveUpC);
    textSize(15);

  }
  
}
  public void settings() {  size(500,700); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "bubble" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
