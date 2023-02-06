package minesweeper;

import javafx.scene.input.MouseButton;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.image.ImageView;

/**
 * This class represents one tile in a MineSweeper game.
 * @author nick
 *
 */
public class Tile extends Group
{	
	
	private TileValue tileValue;
	private boolean isBomb = false;
	
	private TileState state; 
	
	private final ImageView[] tileImages = {
			new ImageView(Assets.BASE_IMG),
			new ImageView(Assets.CLEARED_IMG),
			new ImageView(Assets.FLAG_IMG),
			new ImageView(Assets.BOMB_IMG)};
	
	private Text tileText;
	
	private double tileHeight;
	private double tileWidth;
	
	public Tile(double nPosX, double nPosY, double nWidth, double nHeight)
	{
		super();
		this.state = TileState.Uncleared;
		this.tileValue = TileValue.Zero;
		
		this.tileWidth = nWidth;
		this.tileHeight = nHeight;
		
		this.setTranslateX(nPosX);
		this.setTranslateY(nPosY);
		
		//set the proper image
		for (int i = 0; i < tileImages.length; i++)
		{
			this.getChildren().add(tileImages[i]);
			tileImages[i].setFitWidth(nWidth);
			tileImages[i].setFitHeight(nHeight);
		}
		setupText();
		updateTileImage();
	}
	
	/**
	 * Updates the image being shown on the Tile based on the state the tile is in
	 */
	private void updateTileImage()
	{
		for (int i = 0; i < tileImages.length; i++)
		{
			tileImages[i].setVisible(false);
		}
		tileText.setVisible(false);
		
		switch (this.state)
		{
			case Uncleared:
			{
				tileImages[0].setVisible(true);
				break;
			}
			case Cleared:
			{
				tileImages[1].setVisible(true);
				tileText.setVisible(true);
				break;
			}
			case Flagged:
			{
				tileImages[2].setVisible(true);
				break;
			}
			case Bomb:
			{
				tileImages[3].setVisible(true);
				break;
			}	
		}
		
	}

	/**
	 * This Method will setup the text for showing the number of bombs around a tile
	 */
	private void setupText() 
	{	
		String textValue = "";
		if(tileValue.value != 0) 
		{
			textValue += tileValue.value;
		}
		
		tileText = new Text(tileWidth/2, tileHeight/2, textValue);
		tileText.setFill(tileValue.color);
		tileText.setFont(Font.font("Verdana", tileHeight/2));
		tileText.setTextOrigin(VPos.CENTER);

		//This code will center the text horizontally
		double textWidth = tileText.prefWidth(-1); //this gets the width of the text
		tileText.setX(tileWidth/2 - textWidth/2);
		
		this.getChildren().add(tileText);
	}
	
	/**
	 * Used to set/update the tiles bomb count value
	 * @param obValue
	 */
	public void setTileValue(TileValue obValue) 
	{
		this.tileValue = obValue;
		String sTextValue = "";
		
		if(tileValue.value >= 1)
		{
			sTextValue += tileValue.value;
		}
		
		//the following updates the tiles text//
		tileText.setText(sTextValue);
		tileText.setFill(tileValue.color);
		double textWidth = tileText.prefWidth(-1); //this gets the width of the text
		tileText.setX(tileWidth/2 - textWidth/2);
	}
	
	public TileValue getTileValue() 
	{
		return this.tileValue;
	}
	
	public void clicked(MouseButton button) 
	{
		switch (button)
		{
			case PRIMARY: {
				if(this.state == TileState.Bomb || this.state == TileState.Cleared || this.state == TileState.Flagged) 
				{
					break;
				}
				
				if(this.isBomb) 
				{
					setState(TileState.Bomb);
				}
				else 
				{
					setState(TileState.Cleared);
				}
				
				break;
			}	
			case SECONDARY: {
				if(this.state == TileState.Bomb || this.state == TileState.Cleared)
				{
					break;
				}
				
				if(this.state != TileState.Flagged) 
				{
					setState(TileState.Flagged);
				}
				else 
				{
					setState(TileState.Uncleared);
				}
				
				break;
			}
			
			default:
				break;
		}
		
	}
	
	public void reset()
	{
		setState(TileState.Uncleared);
		setTileValue(TileValue.Zero);
		isBomb = false;
		
	}
	
	private void setState(TileState newState) 
	{
		this.state = newState;
		updateTileImage();
	}
	
	public TileState getState() 
	{
		return this.state;
	}
	
	public void setIsBomb(boolean bVal) 
	{
		this.isBomb = bVal;
	}
	
	public boolean getIsBomb() 
	{
		return this.isBomb;
	}

	
}
