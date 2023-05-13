import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.sound.*; 
import processing.video.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Gator_Buster extends PApplet {

//Dany_Rashwan
Player player;
int n = 2;
int sizeX = 1360, sizeY = 840;

public void setup() {
  
  player = new Player("Player1");
  loadFiles();
  menu();
}

public void draw() {
  //if game is on, begin the game
  if (isGame) {
    game();
  }
}
float gatorLoc, xGatorLoc, yGatorLoc, 
  loc, xLoc, yLoc, x, t;
int xT;
boolean rev, isGame;

//Player class contains user game info
class Player {
  int busted, missed, score, streak, best, gameCount;
  String name;
  Player(String _name) {
    name = _name;
  }
  public String getName() {
    return name;
  }
}

public void game() {
  //draws user interface
  drawUI();
  //pause music
  goGators.pause();
  //controls speed and direction of buster circle
  buster();
  //displays user stats
  stats();
}

//pointer 
public void buster() {
  strokeWeight(0);
  stroke(5);
  loc = map(x, 0, 12, 0, 2*PI);
  xLoc = 188*cos(loc-PI/2);
  yLoc = 188*sin(loc-PI/2);
  for (int i = 1; i <= 12; i++) {
    gatorLoc = map(i, 0, 12, 0, 2*PI);
    xGatorLoc = 200*cos(gatorLoc - PI/2);
    yGatorLoc = 200*sin(gatorLoc - PI/2);
    if (i == xT || xT == 0 && i == 12) {
      fill(255, 165, 0);
      image(gator, xGatorLoc, yGatorLoc);
    } else { 
      fill(0);
      image(gatorMasked, xGatorLoc, yGatorLoc);
    }
    ellipse(40*cos(gatorLoc - PI/2) + xLoc, 40*sin(gatorLoc - PI/2) + yLoc, 20, 20);
  }

  if (x>=12)x = 0.1f;
  if (x<=0) x = 12;
  x+=acc;
}

//Shows stats
public void stats() {
  player.score = player.busted - player.missed;
  if (player.score < 0) {
    player.busted = 0;
    player.missed = 0;
  }
  fill(0, 0, 255);
  textSize(30/n);
  text(player.busted, 280/n+ 200, 450/n-10);

  fill(255, 0, 0);
  text(player.missed, 150/n+ 200, 450/n - 10);

  if (player.score >= 0) {
    fill(0, 255, 0);
  } else {
    fill(255, 0, 0);
  }
  text(player.score, 410/n +  200, 450/n - 10);

  fill(255);
  textSize(50/n);
  text(nf((abs(acc)*10), 0, 1), -350, -200);

  fill(255);
  text("Level", -350, -200+25);
  textSize(35/n);
  text("Busted", 280/n+ 200, 470/n);
  text("Missed", 150/n+ 200, 470/n);
  text("Score", 410/n +  200, 470/n);
  textSize(40/n);
  text("Streak: " + player.streak, -405+120, 465/n);
  text("Best: " + player.best, -405, 465/n);
}

//Draws user interface
public void drawUI() {
  background(gnv);
  translate(width/2, height/2);
  //field
  image(field, 0, 0);
  noFill();
  strokeWeight(5);
  ellipse(0, 0, 500, 500);
  fill(0, 150);
  rect(340, 220, 230, 70, 200);
  rect(-340, 220, 230, 70, 200);
  rect(350, -200, 230, 100, 200);
  rect(-350, -200, 230, 100, 200);
  fill(255);
  textSize(40/n);
  text("\'Space\' to bust\n\'m\' for main menu", 350, -210);
}
float acc = 0.1f;
public void keyPressed() {
  //if user presses spacebar check for pointer location and decides whether player busted the gator without a mask or not
  if (key == ' ' && isGame == true) {
    player.gameCount++;
    acc = -acc;
    if ((float)xT >= (float)abs(x) - 0.75f && (float)xT <= (float)abs(x) + 0.75f) { 
      fill(0, 255, 0);
      gatorChomp.play();
      player.busted++;
      acc*= 1.01f;
      xT = (int)random(0, 12);
      player.streak++;
      if (player.best < player.streak) { 
        player.best = player.streak;
      }
      if (player.streak%10 == 0) {
        celebration.play();
      }
    } else {
      if (player.score > 0) {
        fill(255, 0, 0);
        player.missed++;
        lost.play();
        player.streak = 0;
        acc = acc - 0.01f*acc;
      }
    }
  } else if (key == 12) {
    exit();
  } else if (key == 'm') {
    menu();
  }
}

public void mouseReleased() {
  if (!isGame) {
    buttons();
  }
}


PImage gator, gnv, gatorMasked, field, menu;
SoundFile goGators, gatorChomp, celebration, lost;

//import all files needed
public void loadFiles() {
  goGators = new SoundFile(this, "Go_Gators.mp3");
  gatorChomp = new SoundFile(this, "Chomp_Sound.mp3");
  celebration = new SoundFile(this, "Celebration.mp3");
  lost = new SoundFile(this, "lost.mp3");
  gator = loadImage("Gator.png");
  gator.resize(100, 75);
  gatorMasked = loadImage("Gator_Masked.png");
  gatorMasked.resize(100, 75);
  gnv = loadImage("Gator_Field.jpg");
  gnv.resize((int)floor(sizeX), (int)ceil(sizeY));
  menu = loadImage("MainMenu.png");
  menu.resize((int)sizeX, (int)sizeY);
  gnv.filter(BLUR, 5);
  field = loadImage("Field_overhead.png");
  field.resize(740, 525);
  field.filter(BLUR, 1);
  imageMode(CENTER);
  rectMode(CENTER);
  textAlign(CENTER);
}
boolean isSelected, isHelp, isMenu;
int m = 0, l, lineX, lineY = 520;
public void menu() {
  background(menu);
  isMenu = true;
  if (m == 0) {
    goGators.play();
    m++;
  } else {
    goGators.stop();
  }
  resetAll();
}

//resets everthing when user goes to main menu
public void resetAll() {
  isGame = false;
  isSelected = false;
  isHelp = false;
  player.busted = 0; 
  player.missed = 0; 
  player.streak = 0; 
  player.best = 0; 
  acc = 0.1f;
  player.gameCount = 0;
  gatorChomp.stop();
  celebration.stop();
  lost.stop();
}

//assigned buttons for navigation
public void buttons() {
  if (!isHelp) {
    //bust
    if (mouseX > 220 && mouseX < 540) {
      if (mouseY>600 && mouseY < 800) {
        isGame = true;
      }
    }
    //help
    if (mouseX > width/2-200 && mouseX < width/2+200) {
      if (mouseY>600 && mouseY < 800) {
        help();
      }
    }
    //quit
    if (mouseX > 1000 && mouseX < 1200) {
      if (mouseY>600 && mouseY < 800) {
        isGame = false;
        exit();
      }
    }

    //difficulty
    if (!isSelected) {
      //easy
      if (mouseX >500  && mouseX < 600) {
        if (mouseY>400 && mouseY < 500) {
          acc = 0.1f;
          select(1);
          print("TEST");
        }
      }
      //medium
      if (mouseX >650  && mouseX <850) {
        if (mouseY>400 && mouseY < 500) {
          acc = 0.2f;
          select(2);
        }
      }
      //hard
      if (mouseX > 880 && mouseX < 990) {
        if (mouseY>400 && mouseY < 500) {
          acc = 0.3f;
          select(3);
        }
      }
      //extreme
      if (mouseX > 1050 && mouseX < 1230) {
        if (mouseY>400 && mouseY < 500) {
          acc = 0.4f;
          select(4);
        }
      }
    }
  }
}

//chooses difficulty 
public void select(int lvl) {
  strokeWeight(10);
  stroke(204, 102, 0);

  switch(lvl) {
  case 1:
    lineX = 520;
    l = 100;
    isSelected = true;
    gatorChomp.play();
    break;
  case 2:
    lineX = 670;
    l = 170;
    isSelected = true;
    gatorChomp.play();
    break;
  case 3:
    lineX = 890;
    isSelected = true;
    gatorChomp.play();
    l = 100;
    break;
  case 4:
    lineX = 1050;
    l = 190;
    isSelected = true;
    gatorChomp.play();
    break;
  }
  line(lineX, lineY, lineX + l, lineY);
}

//Previews help
public void help() {
  goGators.pause();
  isGame = false;
  isSelected = false;
  isHelp = true;
  isMenu = false;
  background(0);
  textSize(50/n);
  String s = "Gator Buster is an addictive casual game that is inspired by me getting kicked out of Broward hall for not wearing a mask while eating. The goal is to bust the gator that is not wearing a mask. The more gators you bust, the higher the difficulty is. Relax and get a high score.\n\n\n\'Space\' to bust \n\'esc\' to exit\n\'m\' for main menu";
  textAlign(CENTER);
  text(s, width/n, height/n, 1000/n, 1000/n);
}
  public void settings() {  size(1360, 840); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#F7EDED", "--stop-color=#cccccc", "Gator_Buster" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
