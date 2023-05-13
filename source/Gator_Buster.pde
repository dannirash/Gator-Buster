//Dany_Rashwan
Player player;
int n = 2;
int sizeX = 1360, sizeY = 840;

void setup() {
  size(1360, 840);
  player = new Player("Player1");
  loadFiles();
  menu();
}

void draw() {
  //if game is on, begin the game
  if (isGame) {
    game();
  }
}
