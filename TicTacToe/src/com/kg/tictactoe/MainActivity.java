package com.kg.tictactoe;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private boolean noughtsTurn = false;
	private char board[][] = new char[3][3];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button newGameButton = (Button)findViewById(R.id.NewGameButton);
		Button btn1 = (Button)findViewById(R.id.Button1);
		Button btn2 = (Button)findViewById(R.id.Button2);
		Button btn3 = (Button)findViewById(R.id.Button3);
		Button btn4 = (Button)findViewById(R.id.Button4);
		Button btn5 = (Button)findViewById(R.id.Button5);
		Button btn6 = (Button)findViewById(R.id.Button6);
		Button btn7 = (Button)findViewById(R.id.Button7);
		Button btn8 = (Button)findViewById(R.id.Button8);
		Button btn9 = (Button)findViewById(R.id.Button9);
		
		newGameButton.setOnClickListener(onClickListener);
		btn1.setOnClickListener(onClickListener);
		btn2.setOnClickListener(onClickListener);
		btn3.setOnClickListener(onClickListener);
		btn4.setOnClickListener(onClickListener);
		btn5.setOnClickListener(onClickListener);
		btn6.setOnClickListener(onClickListener);
		btn7.setOnClickListener(onClickListener);
		btn8.setOnClickListener(onClickListener);
		btn9.setOnClickListener(onClickListener);
	}
	
	private OnClickListener onClickListener = new OnClickListener(){
		public void onClick(View v){
			switch(v.getId()){
			case R.id.NewGameButton:
				noughtsTurn = false;
				board = new char[3][3];
				TextView T = (TextView)findViewById(R.id.textView1);
				T.setText("Tic Tac Toe by Kumar Gautam");
				resetAllButtons();
				return;
			case R.id.Button1:
			{
				Button b = (Button)findViewById(v.getId());
				b.setText(noughtsTurn ? "O" : "X");
				b.setEnabled(false);				
				board[0][0] = noughtsTurn ? 'O' : 'X';
				noughtsTurn = !noughtsTurn;
			}
			break;
			case R.id.Button2:
			{
				Button b = (Button)findViewById(v.getId());
				b.setText(noughtsTurn ? "O" : "X");
				b.setEnabled(false);				
				board[0][1] = noughtsTurn ? 'O' : 'X';
				noughtsTurn = !noughtsTurn;
			}
			break;
			case R.id.Button3:
			{
				Button b = (Button)findViewById(v.getId());
				b.setText(noughtsTurn ? "O" : "X");
				b.setEnabled(false);				
				board[0][2] = noughtsTurn ? 'O' : 'X';
				noughtsTurn = !noughtsTurn;
			}
			break;
			case R.id.Button4:
			{
				Button b = (Button)findViewById(v.getId());
				b.setText(noughtsTurn ? "O" : "X");
				b.setEnabled(false);				
				board[1][0] = noughtsTurn ? 'O' : 'X';
				noughtsTurn = !noughtsTurn;
			}
			break;
			case R.id.Button5:
			{
				Button b = (Button)findViewById(v.getId());
				b.setText(noughtsTurn ? "O" : "X");
				b.setEnabled(false);				
				board[1][1] = noughtsTurn ? 'O' : 'X';
				noughtsTurn = !noughtsTurn;
			}
			break;
			case R.id.Button6:
			{
				Button b = (Button)findViewById(v.getId());
				b.setText(noughtsTurn ? "O" : "X");
				b.setEnabled(false);				
				board[1][2] = noughtsTurn ? 'O' : 'X';
				noughtsTurn = !noughtsTurn;
			}
			break;
			case R.id.Button7:
			{
				Button b = (Button)findViewById(v.getId());
				b.setText(noughtsTurn ? "O" : "X");
				b.setEnabled(false);				
				board[2][0] = noughtsTurn ? 'O' : 'X';
				noughtsTurn = !noughtsTurn;
				
			}
			break;
			case R.id.Button8:
			{
				Button b = (Button)findViewById(v.getId());
				b.setText(noughtsTurn ? "O" : "X");
				b.setEnabled(false);				
				board[2][1] = noughtsTurn ? 'O' : 'X';
				noughtsTurn = !noughtsTurn;
			}
			break;
			case R.id.Button9:
			{
				Button b = (Button)findViewById(v.getId());
				b.setText(noughtsTurn ? "O" : "X");
				b.setEnabled(false);				
				board[2][2] = noughtsTurn ? 'O' : 'X';
				noughtsTurn = !noughtsTurn;
			}
			break;
			default:
			break;
			}
			if(checkWin())
				disableAllButtons();
		}
	};
	
	private boolean checkWin(){
		char winner = '\0';
		if(checkWinner(board, 3, 'X'))
			winner = 'X';
		else if(checkWinner(board, 3, 'O'))
			winner = 'O';
		
		if(winner == '\0')
			return false;
		else{
			TextView T = (TextView)findViewById(R.id.textView1);
			T.setText(winner+ " wins");
			return true;
		}
	}
	
	private boolean checkWinner(char[][] board, int size, char player){
		for(int x = 0; x < size; x++){
			int total = 0;
			for(int y = 0; y < size; y++){
				if(board[x][y] == player)
					total++;
				if(total >= size)
					return true;
			}
		}
		
		for(int y = 0; y < size; y++){
			int total = 0;
			for(int x = 0; x < size; x++){
				if(board[x][y] == player)
					total++;
				if(total >= size)
					return true;
			}
		}
		
		int total = 0;
		for(int x = 0; x < size; x++){
			for(int y = 0; y < size; y++){
				if(x == y && board[x][y] == player){
					total++;
				}
			}
		}
		if(total >= size)
			return true;
		
		total = 0;
		for(int x = 0; x < size; x++){
			for(int y = 0; y < size; y++){
				if(x+y == size-1 && board[x][y] == player){
					total++;
				}
			}
		}
		if(total >= size)
			return true;
		
		return false;
	}
	
	private void disableAllButtons(){
		Button btn1 = (Button)findViewById(R.id.Button1);
		Button btn2 = (Button)findViewById(R.id.Button2);
		Button btn3 = (Button)findViewById(R.id.Button3);
		Button btn4 = (Button)findViewById(R.id.Button4);
		Button btn5 = (Button)findViewById(R.id.Button5);
		Button btn6 = (Button)findViewById(R.id.Button6);
		Button btn7 = (Button)findViewById(R.id.Button7);
		Button btn8 = (Button)findViewById(R.id.Button8);
		Button btn9 = (Button)findViewById(R.id.Button9);
		
		btn1.setEnabled(false);
		btn2.setEnabled(false);
		btn3.setEnabled(false);
		btn4.setEnabled(false);
		btn5.setEnabled(false);
		btn6.setEnabled(false);
		btn7.setEnabled(false);
		btn8.setEnabled(false);
		btn9.setEnabled(false);
	}
	
	private void resetAllButtons(){
		Button btn1 = (Button)findViewById(R.id.Button1);
		Button btn2 = (Button)findViewById(R.id.Button2);
		Button btn3 = (Button)findViewById(R.id.Button3);
		Button btn4 = (Button)findViewById(R.id.Button4);
		Button btn5 = (Button)findViewById(R.id.Button5);
		Button btn6 = (Button)findViewById(R.id.Button6);
		Button btn7 = (Button)findViewById(R.id.Button7);
		Button btn8 = (Button)findViewById(R.id.Button8);
		Button btn9 = (Button)findViewById(R.id.Button9);
		
		btn1.setText("");
		btn1.setEnabled(true);
		
		btn2.setText("");
		btn2.setEnabled(true);
		
		btn3.setText("");
		btn3.setEnabled(true);
		
		btn4.setText("");
		btn4.setEnabled(true);
		
		btn5.setText("");
		btn5.setEnabled(true);
		
		btn6.setText("");
		btn6.setEnabled(true);
		
		btn7.setText("");
		btn7.setEnabled(true);
		
		btn8.setText("");
		btn8.setEnabled(true);
		
		btn9.setText("");
		btn9.setEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
