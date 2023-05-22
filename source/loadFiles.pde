// Copyright [2021] <Dany Rashwan>
import processing.sound.*;
import processing.video.*;
PImage gator, gnv, gatorMasked, field, menu;
SoundFile goGators, gatorChomp, celebration, lost;

//import all files needed
void loadFiles() {
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
