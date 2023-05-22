// Copyright [2021] <Dany Rashwan>
boolean isSelected, isHelp, isMenu;
int m = 0, l, lineX, lineY = 520;
void menu() {
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
void resetAll() {
  isGame = false;
  isSelected = false;
  isHelp = false;
  player.busted = 0; 
  player.missed = 0; 
  player.streak = 0; 
  player.best = 0; 
  acc = 0.1;
  player.gameCount = 0;
  gatorChomp.stop();
  celebration.stop();
  lost.stop();
}

//assigned buttons for navigation
void buttons() {
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
          acc = 0.1;
          select(1);
          print("TEST");
        }
      }
      //medium
      if (mouseX >650  && mouseX <850) {
        if (mouseY>400 && mouseY < 500) {
          acc = 0.2;
          select(2);
        }
      }
      //hard
      if (mouseX > 880 && mouseX < 990) {
        if (mouseY>400 && mouseY < 500) {
          acc = 0.3;
          select(3);
        }
      }
      //extreme
      if (mouseX > 1050 && mouseX < 1230) {
        if (mouseY>400 && mouseY < 500) {
          acc = 0.4;
          select(4);
        }
      }
    }
  }
}

//chooses difficulty 
void select(int lvl) {
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
void help() {
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
