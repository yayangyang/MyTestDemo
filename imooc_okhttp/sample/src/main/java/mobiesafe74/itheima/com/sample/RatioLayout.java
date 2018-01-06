package mobiesafe74.itheima.com.sample;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

/**
 *	自定义控件,按照比例来决定布局高度
 */
public class RatioLayout extends FrameLayout {

	private float ratio;

	public RatioLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public RatioLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		//获取属性值
		//attrs.getAttributeFloatValue("", "ratio", -1);
		//当自定义属性时,系统会自动生成属性相关id,此id通过R.styleable来引用
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.RatioLayout);
		ratio = typedArray.getFloat(R.styleable.RatioLayout_ratio, -1);
		typedArray.recycle();//回收typedArray,提高性能
	}

	public RatioLayout(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//1.获取宽度
		//2.根据宽度和比例ratio,计算控件的高度
		//3.重新测量控件
		//widthMeasureSpec->二进制->10000000000000000000(前面为模式)10111010010(后面转为十进制为像素数)
		System.out.println("widthMeasureSpec:"+widthMeasureSpec);
		
		//MeasureSpec.AT_MOST;//至多模式,控件有多大显示多大,wrap_content
		//MeasureSpec.EXACTLY;//确定模式,雷士宽高写死成dip,match_parent
		//MeasureSpec.UNSPECIFIED;//未指定模式,
		int width = MeasureSpec.getSize(widthMeasureSpec);//获取宽度值
		int widthmode = MeasureSpec.getMode(widthMeasureSpec);//获取宽度模式
		int height = MeasureSpec.getSize(heightMeasureSpec);//获取宽度值
		int hieghtmode = MeasureSpec.getMode(heightMeasureSpec);//获取宽度模式
		
		//宽度确定,高度不确定,ratio合法,才计算高度值
		if(widthmode==MeasureSpec.EXACTLY&&heightMeasureSpec!=MeasureSpec.EXACTLY&&ratio>0){
			Log.e("ww","运行到了");
			//图片宽度=控件宽度-左侧内边距-右侧内边距
			int imageWidth=width-getPaddingLeft()-getPaddingRight();
			
			//图片高度=图片宽度/宽高比例
			int imageHeight=(int) (imageWidth/ratio+0.5f);
			
			//控件高度=图片高度+上侧内边距+下侧内边距
			height=imageHeight+getPaddingTop()+getPaddingBottom();
			
			//根据最新的高度来重新生成heightMeasureSpec(高度确定模式)tvDes
			heightMeasureSpec=MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
		}
		
		//按照最新的高度测量控件
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
