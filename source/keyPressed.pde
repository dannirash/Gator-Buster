float acc = 0.1;
void keyPressed() {
  //if user presses spacebar check for pointer location and decides whether player busted the gator without a mask or not
  if (key == ' ' && isGame == true) {
    player.gameCount++;
    acc = -acc;
    if ((float)xT >= (float)abs(x) - 0.75 && (float)xT <= (float)abs(x) + 0.75) { 
      fill(0, 255, 0);
      gatorChomp.play();
      player.busted++;
      acc*= 1.01;
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
        acc = acc - 0.01*acc;
      }
    }
  } else if (key == 12) {
    exit();
  } else if (key == 'm') {
    menu();
  }
}

void mouseReleased() {
  if (!isGame) {
    buttons();
  }
}
