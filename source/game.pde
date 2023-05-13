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
  String getName() {
    return name;
  }
}

void game() {
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
void buster() {
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

  if (x>=12)x = 0.1;
  if (x<=0) x = 12;
  x+=acc;
}

//Shows stats
void stats() {
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
void drawUI() {
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
