package com.example.yang.myapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.example.yang.myapplication.R;


/**
 * @文件名: PicAdaptorLayout
 * @创建者: 杨沛儒
 * @创建时间: 2015/11/27 00:56
 * @描述: 适配图片
 */


public class PicAdaptorLayout
        extends FrameLayout
{

    private float mRatio = 0f;//通常用宽高比,也可以是高宽比,像素有了就看你用谁当被除数

    //设置几个常量区别是相对于谁计算
    private static final int RELATIVE_WIDTH  = 0;//相对于宽度计算高度
    private static final int RELATIVE_HEIGHT = 1;//相对于高度计算宽度

    //设置一个默认相对于谁
    private int mRelative = RELATIVE_WIDTH;

    public PicAdaptorLayout(Context context) {
        this(context,null);
    }

    public PicAdaptorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PicAdaptorLayout);
        mRatio = ta.getFloat(R.styleable.PicAdaptorLayout_rlRatio, 0);//拿不到属性值就返回后面的值
        mRelative = ta.getInt(R.styleable.PicAdaptorLayout_rlRelative, RELATIVE_WIDTH);
        ta.recycle();//recycle:回收  相当于刷新+close
    }

    //提供对外常量赋值设置方法
    public void setRadio(float radio) {
        this.mRatio = radio;
    }

    public void setRelative(int relative) {
        //因为设置了只有0和1两个常量,所有当别人设置其他的时候,抛出错误并提示信息
        if (relative != RELATIVE_WIDTH && relative != RELATIVE_HEIGHT) {
            throw new RuntimeException(
                    "relative is only number zero or number one,see @PicAdaptorLayout#RELATIVE_WIDTH,@PicAdaptorLayout#RELATIVE_HEIGHT");
        }
        this.mRelative = relative;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //给第一种方法用的
        int widthSelf = MeasureSpec.getSize(widthMeasureSpec);//获得宽
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);//获得宽的模式

        //给第二种方法用的
        int heightSelf = MeasureSpec.getSize(heightMeasureSpec);//获得高
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);//获得高的模式

        //TODO: 1.已知 宽度确定的值，宽高的一个比例,计算出高度，对孩子的宽高产生一个期望
        //拿到模式后,判断模式是不是精确值,并且宽高比不等于0(不等于0的意思就是有宽高比),达成第一种方式条目
        if (widthMode == MeasureSpec.EXACTLY && mRatio != 0 && mRelative == RELATIVE_WIDTH) {//EXACTLY:精确的
            //计算高度,给孩子(ImageView)一个期望值
            float height = widthSelf / mRatio;//获得高

            //计算出孩子的宽高
            int childWidth  = widthSelf - getPaddingLeft() - getPaddingRight();
            int childHeight = (int) (height - getPaddingTop() - getPaddingBottom() + 0.5f);

            //获得期望的宽高 //两个参数代表,传入的孩子宽高和需要的模式
            int childWidthSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
            int chilHeightSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
            //得到期望就要测量,测量孩子
            measureChildren(childWidthSpec, chilHeightSpec);

            //给自己设置宽高
            setMeasuredDimension(widthSelf, (int) (height + 0.5f));

            //TODO: 2.已知 高度确定的值，宽高的一个比例,计算出宽度，对孩子的宽高产生一个期望
        } else if (heightSelf == MeasureSpec.EXACTLY && mRatio != 0 && mRelative == RELATIVE_HEIGHT) {
            float width = heightSelf / mRatio;//获得宽

            //计算孩子的宽高
            int childWidth  = (int) (width - getPaddingLeft() - getPaddingRight() + 0.5f);
            int childHeight = heightSelf - getPaddingTop() - getPaddingBottom();

            //获得孩子的期望宽高
            int childWidthSpec  = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
            int childHeightSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);

            //测量孩子
            measureChildren(childWidthSpec, childHeightSpec);

            //给自己测量
            setMeasuredDimension((int) (width + 0.5), heightSelf);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
