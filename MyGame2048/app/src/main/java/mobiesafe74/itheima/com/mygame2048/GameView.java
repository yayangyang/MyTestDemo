package mobiesafe74.itheima.com.mygame2048;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.GridLayout;
import android.widget.TextView;

import mobiesafe74.itheima.com.mygame2048.utils.UIUtils;


public class GameView extends GridLayout{
	
	private Card[][] cardsMap=new Card[4][4];
	private List<Point> emptyPoints=new ArrayList<Point>();
	
	private double startX;
	private double startY;
	private double endX;
	private double offsetX;
	private double offsetY;
	private double endY;

	private int fenshu=0;
	private listener mListener=null;

	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initGameView();
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initGameView();
	}

	public GameView(Context context) {
		super(context);
		initGameView();
	}

	private void initGameView() {
		setColumnCount(4);
		setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
					startX = arg1.getX();
					startY = arg1.getY();
				} else if (arg1.getAction() == MotionEvent.ACTION_UP) {
					endX = arg1.getX();
					endY = arg1.getY();
					offsetX = endX - startX;
					offsetY = endY - startY;
					if (Math.abs(offsetX) > Math.abs(offsetY)) {
						if (offsetX < -5) {
							swipLeft();
						} else if (offsetX > 5) {
							swipRight();
						}
					} else {
						if (offsetY < -5) {
							swipTop();
						} else if (offsetY > 5) {
							swipBottom();
						}
					}
					isEnd();
				}
				return true;
			}
		});

		getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				GameView.this.getViewTreeObserver()
						.removeGlobalOnLayoutListener(this);
				requestLayout();
			}
		});
	}

	private void update() {
		addRandomNum();
		addRandomNum();
	}

	private void addRandomNum() {
		emptyPoints.clear();
		for(int y=0;y<4;y++){
			for(int x=0;x<4;x++){
				if(cardsMap[x][y].getNum()==0){
					emptyPoints.add(new Point(x,y));
				}
			}
		}
		int pos=(int)(Math.random()*emptyPoints.size());
		Point p = emptyPoints.remove(pos);
		cardsMap[p.x][p.y].setNum(Math.random()>0.1?2:4);
		cardsMap[p.x][p.y].setCardColor(cardsMap[p.x][p.y].getNum());

	}

	private void addCards(int cardWidth, int cardHeight ) {
		for(int y=0;y<4;y++){
			for(int x=0;x<4;x++){
				if(cardsMap[x][y]==null){
					cardsMap[x][y]=new Card(getContext());
				}
				addView(cardsMap[x][y],cardWidth,cardHeight);
			}
		}
	}

	public void isEnd() {
		int count=0;
		All:
			for(int i=0;i<4;i++){
				for(int j=0;j<4;j++){
					if(cardsMap[i][j].getNum()==0){
						count++;
						break All;
					}
					if(i-1>0){
						if(cardsMap[i-1][j].getNum()==cardsMap[i][j].getNum()){
							count++;
							break All;
						}
					}
					if(i<cardsMap.length-1){
						if(cardsMap[i+1][j].getNum()==cardsMap[i][j].getNum()){
							count++;
							break All;
						}
					}
					if(j>0){
						if(cardsMap[i][j-1].getNum()==cardsMap[i][j].getNum()){
							count++;
							break All;
						}
					}
					if(j<cardsMap.length-1){
						if(cardsMap[i][j+1].getNum()==cardsMap[i][j].getNum()){
							count++;
							break All;
						}
					}
				}
			}
		if(fenshu>=2048){
            if(mListener!=null){
                mListener.success(fenshu);
            }
        }
		Log.e("count",count+"");
		if(count<=0){
			if(mListener!=null){
                mListener.fail();
            }
		}
	}

	private void updateFenshu(int fenshu) {
		this.fenshu+=fenshu;
		if(mListener!=null){
			mListener.fenshu(this.fenshu);
		}
	}

	private void swipLeft() {
		boolean merge=false;
		for(int y=0;y<4;y++){
			for(int x=0;x<4;x++){
				for(int x1=x+1;x1<4;x1++){
					if(cardsMap[x1][y].getNum()>0){
						if(cardsMap[x][y].getNum()<=0){
							cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
							cardsMap[x][y].setCardColor(cardsMap[x1][y].getNum());
							cardsMap[x1][y].setNum(0);
							cardsMap[x1][y].setCardColor(cardsMap[x1][y].getNum());
							x--;
							merge=true;
						}else if(cardsMap[x][y].getNum()==cardsMap[x1][y].getNum()){
							updateFenshu(cardsMap[x1][y].getNum()*2);
							cardsMap[x][y].setNum(cardsMap[x1][y].getNum()*2);
							cardsMap[x][y].setCardColor(cardsMap[x1][y].getNum());
							cardsMap[x1][y].setNum(0);
							cardsMap[x1][y].setCardColor(cardsMap[x1][y].getNum());
							merge=true;
						}
						break;
					}
				}
			}
		}
		if(merge){
			addRandomNum();
		}
	}

	private void swipRight() {
		boolean merge=false;
		for(int y=0;y<4;y++){
			for(int x=3;x>=0;x--){
				for(int x1=x-1;x1>=0;x1--){
					if(cardsMap[x1][y].getNum()>0){
						if(cardsMap[x][y].getNum()<=0){
							cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
							cardsMap[x][y].setCardColor(cardsMap[x1][y].getNum());
							cardsMap[x1][y].setNum(0);
							cardsMap[x1][y].setCardColor(cardsMap[x1][y].getNum());
							x++;
							merge=true;
						}else if(cardsMap[x][y].getNum()==cardsMap[x1][y].getNum()){
							updateFenshu(cardsMap[x1][y].getNum()*2);
							cardsMap[x][y].setNum(cardsMap[x1][y].getNum()*2);
							cardsMap[x][y].setCardColor(cardsMap[x1][y].getNum());
							cardsMap[x1][y].setNum(0);
							cardsMap[x1][y].setCardColor(cardsMap[x1][y].getNum());
							merge=true;
						}
						break;
					}
				}
			}
		}
		if(merge){
			addRandomNum();
		}
	}

	private void swipBottom() {
		boolean merge=false;
		for(int y=0;y<4;y++){
			for(int x=3;x>=0;x--){
				for(int x1=x-1;x1>=0;x1--){
					if(cardsMap[y][x1].getNum()>0){
						if(cardsMap[y][x].getNum()<=0){
							cardsMap[y][x].setNum(cardsMap[y][x1].getNum());
							cardsMap[y][x].setCardColor(cardsMap[y][x1].getNum());
							cardsMap[y][x1].setNum(0);
							cardsMap[y][x1].setCardColor(cardsMap[y][x1].getNum());
							x++;
							merge=true;
						}else if(cardsMap[y][x].getNum()==cardsMap[y][x1].getNum()){
							updateFenshu(cardsMap[y][x1].getNum()*2);
							cardsMap[y][x].setNum(cardsMap[y][x1].getNum()*2);
							cardsMap[y][x].setCardColor(cardsMap[y][x1].getNum());
							cardsMap[y][x1].setNum(0);
							cardsMap[y][x1].setCardColor(cardsMap[y][x1].getNum());
							merge=true;
						}
						break;
					}
				}
			}
		}
		if(merge){
			addRandomNum();
		}
	}

	private void swipTop() {
		boolean merge=false;
		for(int y=0;y<4;y++){
			for(int x=0;x<4;x++){
				for(int x1=x+1;x1<4;x1++){
					if(cardsMap[y][x1].getNum()>0){
						if(cardsMap[y][x].getNum()<=0){
							cardsMap[y][x].setNum(cardsMap[y][x1].getNum());
							cardsMap[y][x].setCardColor(cardsMap[y][x1].getNum());
							cardsMap[y][x1].setNum(0);
							cardsMap[y][x1].setCardColor(cardsMap[y][x1].getNum());
							x--;
							merge=true;
						}else if(cardsMap[y][x].getNum()==cardsMap[y][x1].getNum()){
							updateFenshu(cardsMap[y][x1].getNum()*2);
							cardsMap[y][x].setNum(cardsMap[y][x1].getNum()*2);
							cardsMap[y][x].setCardColor(cardsMap[y][x1].getNum());
							cardsMap[y][x1].setNum(0);
							cardsMap[y][x1].setCardColor(cardsMap[y][x1].getNum());
							merge=true;
						}
						break;
					}
				}
			}
		}
		if(merge){
			addRandomNum();
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		Log.e("onSizeChanged", "22222");
		super.onSizeChanged(w, h, oldw, oldh);
		int cardWidth=(Math.min(w, h)-10)/4;
		addCards(cardWidth,cardWidth);
		update();
	}

	@Override
	protected void onMeasure(int widthSpec, int heightSpec) {
		Log.e("onMeasure", "3333");
		super.onMeasure(widthSpec, heightSpec);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		Log.e("onLayout", "5554");
		super.onLayout(changed, left, top, right, bottom);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Log.e("onDraw", "4444");
		super.onDraw(canvas);
	}

	public void setListener(listener l){
		mListener=l;
	}

	public interface listener{
		public void fenshu(int fenshu);
        public void success(int fenshu);
        public void fail();
	}

}
