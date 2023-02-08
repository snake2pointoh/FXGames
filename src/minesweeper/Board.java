package minesweeper;

import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * This class Represent a MineSweeper Game board
 * @author nick
 *
 */
public class Board extends Group
{
	
	private Tile[][] tiles;
	private Tile[] bombs;
	private double tileSize;
	private int bombCount;
	private GameState gameState;
	
	private static final double BOMB_PERCENT = 0.10;
	
	public Board(double nPosX, double nPosY, double nSize, int nTileCount)
	{
		super();
		
		//position the game board
		this.setTranslateX(nPosX);
		this.setTranslateY(nPosY);
		
		this.gameState = GameState.NotStarted;
		
		//setup the base tiles
		tiles = new Tile[nTileCount][nTileCount];
		tileSize = nSize/nTileCount;
				
		for (int x = 0; x < tiles.length; x++)
		{
			for (int y = 0; y < tiles[x].length; y++)
			{
				tiles[x][y] = new Tile(tileSize*x, tileSize*y, tileSize, tileSize);
				this.getChildren().add(tiles[x][y]);
			}
		}
		
		bombCount = (int) (Math.ceil(tiles.length * tiles[0].length * BOMB_PERCENT)); // <--- TODO remove after testing //
		
		//bind the events
		this.setOnMouseClicked(event -> doClickEvent(event));
//		this.setOnMouseClicked(this::doClickEvent);
	}
	
	private void genNewRound(int nBombs)
	{
		if(nBombs >= tiles.length*tiles[0].length || nBombs == 0)
		{
			//sets number of bombs to default %10 of the tiles
			nBombs = (int) (tiles.length * tiles[0].length * BOMB_PERCENT);
		}
		
		setupCleanBoard();
		placeBombs(genBombsPosArray(nBombs));
		gameState = GameState.Started;
	}
	
	/**
	 * This method resets all of the tiles on the board
	 */
	private void setupCleanBoard() 
	{
		for (Tile[] tiles : tiles)
		{
			for (Tile tile : tiles)
			{
				tile.reset();
			}
		}
	}
	
	/**
	 * This Method will generate a 2d Array of bomb Positions and setup the this.bombs array to the correct length
	 * @param nBombs
	 * @return
	 */
	private int[][] genBombsPosArray(int nBombs)
	
	{
		int[][] aBombs = new int[nBombs][2];
		this.bombs = new Tile[nBombs];
		
		//these loops generate the given number of bombs and save their positions;
		for (int i = 0; i < aBombs.length; i++)
		{
			boolean isDuplicate = true;
			int xPos = 0;
			int yPos = 0;
			while(isDuplicate)
			{
				xPos = (int) (Math.random() * tiles.length);
				yPos = (int) (Math.random() * tiles[0].length);
				isDuplicate = false;
				
				for (int[] bombPos : aBombs)
				{
					if(xPos == bombPos[0] && yPos == bombPos[1]) 
					{
						isDuplicate = true;
					}
				}
			}
			aBombs[i][0] = xPos;
			aBombs[i][1] = yPos;
			
		}
		return aBombs;
	}
	
	/**
	 * This method will place all of the bombs onto the board and set the values of all the surrounding tiles
	 * @param bombsArray
	 */
	private void placeBombs(int[][] bombsArray)
	{
		int nCounter = 0;
		for (int[] bombPos : bombsArray)
		{
			int xPos = bombPos[0];
			int yPos = bombPos[1];
			
			this.tiles[xPos][yPos].setIsBomb(true);
			this.bombs[nCounter++] = this.tiles[xPos][yPos];
			
			//adds 1 to the value of all surrounding tiles
			for (int x = xPos-1; x <= xPos+1 ; x++)
			{
				for (int y = yPos-1; y <= yPos+1 ; y++)
				{
					if(y == yPos && x == xPos) 
					{
						continue;
					}
					//if out of range of the table array then skip current tile
					if(x < 0 || x > tiles.length-1 || y < 0 || y > tiles[0].length-1) 
					{
						continue;
					}
					Tile currentTile = tiles[x][y];
					currentTile.setTileValue(TileValue.values()[currentTile.getTileValue().ordinal()+1]);
				}
			}
			
		}
	}

	/**
	 * This method will update all of the zero value tiles when one of them is clicked (uses recursive calls)
	 * @param nXPos
	 * @param nYPos
	 */
	private void updateNeighbors(int nXPos, int nYPos) 
	{
		for (int x = nXPos-1; x <= nXPos+1; x++)
		{
			for (int y = nYPos-1; y <= nYPos+1; y++)
			{
				if(y == nYPos && x == nXPos) 
				{
					continue;
				}
				//if out of range of the table array then skip current tile
				if(x < 0 || x > tiles.length-1 || y < 0 || y > tiles[0].length-1) 
				{
					continue;
				}
				Tile obCurrentTile = tiles[x][y];
				if(obCurrentTile.getState() == TileState.Uncleared) 
				{
					obCurrentTile.clicked(MouseButton.PRIMARY);
					if(obCurrentTile.getTileValue() == TileValue.Zero) 
					{
						updateNeighbors(x,y);
					}					
				}
			}
		}
	}
	
	/**
	 * Handles all of the logic of clicking on the game board
	 * @param event
	 */
	private void doClickEvent(MouseEvent event) 
	{
		Tile clickedTile = getTileAtPos(event.getX(), event.getY());

		if(clickedTile == null) 
		{
			return;
		}
		
		if(gameState == GameState.Win || gameState == GameState.Lose) 
		{
			setupCleanBoard();
			gameState = GameState.NotStarted;
			return;
		}
		
		if(gameState == GameState.NotStarted)
		{
			if(event.getButton() != MouseButton.PRIMARY)
			{
				return;
			}
			genNewRound(bombCount);
			//set first clicked tile to not be a bomb//
			while(clickedTile.getIsBomb()) 
			{
				genNewRound(bombCount);
			}
		}		
		
		//send click event to clicked tile if the game is started
		clickedTile.clicked(event.getButton());
		
		if(event.getButton() == MouseButton.PRIMARY) 
		{
			//Lose Condition//
			if(clickedTile.getIsBomb()) 
			{
				gameState = GameState.Lose;
				for (Tile obBomb : bombs)
				{
					obBomb.clicked(MouseButton.PRIMARY);
				}
			}
			else if(clickedTile.getTileValue() == TileValue.Zero)
			{
				int[] tilePos = getPositionOfTIle(clickedTile);
				updateNeighbors(tilePos[0],tilePos[1]);
			}			
		}
	}
	
	/**
	 * This will return and int[] with the x and y position of a given tile in the tiles array
	 * @param obCompTile
	 * @return
	 */
	private int[] getPositionOfTIle(Tile obCompTile)
	{
		int[] aReturn = {-1,-1};
		
		for (int i = 0; i < tiles.length; i++)
		{
			for (int j = 0; j < tiles[i].length; j++)
			{
				if(tiles[i][j].equals(obCompTile)) 
				{
					aReturn[0] = i;
					aReturn[1] = j;
					return aReturn;
				}
			}
		}
		
		return aReturn;
	}
	
	/**
	 * Returns the tile at a given location relative to the main 
	 * @param xPos
	 * @param yPos
	 * @return
	 */
	private Tile getTileAtPos(double xPos, double yPos) 
	{
		int tileX = (int) (xPos/tileSize);
		int tileY = (int) (yPos/tileSize);
		
		//return null if the position is out of range// 
		if(tileX < 0 || tileY < 0 || tileX > tiles.length-1 || tileY > tiles[0].length-1) 
		{
			return null;
		}
		
		return tiles[tileX][tileY];
	}
	
	public int getBombCount() 
	{
		return this.bombCount;
	}
	
	public void setBombCount(int nBombs) 
	{
		// TODO
	}
}
