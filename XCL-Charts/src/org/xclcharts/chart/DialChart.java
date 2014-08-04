/**
 * Copyright 2014  XCL-Charts
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @Project XCL-Charts 
 * @Description Android图表基类库
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 * @license http://www.apache.org/licenses/  Apache v2 License
 * @version v0.1
 */


package org.xclcharts.chart;

import java.util.ArrayList;
import java.util.List;

import org.xclcharts.common.MathHelper;
import org.xclcharts.renderer.CirChart;
import org.xclcharts.renderer.XEnum;
import org.xclcharts.renderer.axis.RoundAxis;
import org.xclcharts.renderer.axis.RoundAxisRender;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.util.Log;


/**
 * @ClassName DialChart
 * @Description  仪表盘基类
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 *  
 */

public class DialChart  extends CirChart{
	
	private String TAG = "DialChart";
	
	//指针
	private float mPointerAngle = 0.0f;		
	private Paint mPaintPointerLine = null;
	private Paint mPaintPinterCircle  = null;
	
	private static final int INIT_ANGLE = 135;
	private static final int FIX_TOTAL_ANGLE = 270;

	List<RoundAxis> mRoundAxis =  new ArrayList<RoundAxis>();
	 
	private float mPointerRadiusPercentage = 0.9f;	
	private List<XEnum.AttributeInfoLoction> mAttrInfoLocation = null;
	private List<String> mAttrInfo = null;
	private List<Float> mAttrInfoPostion = null;	
	private List<Paint> mAttrInfoPaint = null;
	
	private float mCurrentStatusPercentage = 0.0f;
	

	public DialChart()
	{
		super();	
		initPaint();
	}
	
	private void initPaint()
	{
		getLabelPaint().setTextSize(18);
		getLabelPaint().setColor(Color.BLUE);		
		getLabelPaint().setAntiAlias(true);	
		

		mPaintPointerLine = new Paint();
		mPaintPointerLine.setStyle(Style.FILL);
		mPaintPointerLine.setAntiAlias(true);	
		mPaintPointerLine.setColor(Color.BLACK);
		mPaintPointerLine.setStrokeWidth(3);
		
		mPaintPinterCircle = new Paint();
		mPaintPinterCircle.setStyle(Style.FILL);
		mPaintPinterCircle.setAntiAlias(true);	
		mPaintPinterCircle.setColor(Color.BLACK);
		mPaintPinterCircle.setStrokeWidth(8);
	}
	
	/**
	 * 返回图轴集合
	 * @return 集合
	 */
	public List<RoundAxis> getPlotAxis()
	{
		return mRoundAxis;
	}
	
	/**
	 * 返回附加信息方位集合
	 * @return 集合
	 */
	public List<XEnum.AttributeInfoLoction>  getPlotAttrInfoLocation()
	{
		return mAttrInfoLocation;
	}
	
	/**
	 * 返回附加信息集合
	 * @return 集合
	 */
	public List<String> getPlotAttrInfo()
	{
		return mAttrInfo;
	}
	
	/**
	 * 返回附加信息位置集合
	 * @return 集合
	 */
	public List<Float> getPlotAttrInfoPostion()
	{
		return mAttrInfoPostion;
	}
	
	/**
	 * 返回附加信息画笔集合
	 * @return 集合
	 */
	public List<Paint> getPlotAttrInfoPaint()
	{
		return mAttrInfoPaint;
	}
	
	
	
	/**
	 * 开放指针画笔
	 * @return 画笔
	 */
	public Paint getPinterCirclePaint()
	{
		return mPaintPinterCircle;
	}
	
	/**
	 * 开放指针底部圆画笔
	 * @return 画笔
	 */
	public Paint getPointerLinePaint()
	{
		return mPaintPointerLine;
	}

	/**
	 * 设置指针指向的角度
	 * @param Angle 角度
	 */
	//public void setCurrentAngle(float Angle)
	//{
	//	mPointerAngle = Angle;
	//}
	
	/**
	 * 设置指针指向的值，即当前比例(0 - 1)
	 * @param percentage 当前比例
	 */
	public void setCurrentPercentage(float percentage)
	{
		mPointerAngle = mul( FIX_TOTAL_ANGLE , percentage);
		
		mCurrentStatusPercentage = percentage;
	}
	 
	/**
	 * 设置指针长度
	 * @param radiusPercentage  占总半径的比例
	 */
	 public void setPointerLength(float radiusPercentage )
	 {		 
		 mPointerRadiusPercentage = radiusPercentage; //0.9f
	 }
	 
	 /**
	  * 返回指针长度占总半径的比例
	  * @return 比例
	  */
	 public float getPointerRadiusPercentage()
	 {
		 return mPointerRadiusPercentage;
	 }	 	 
	 
 	
	 /**
	  * 增加附加信息
	  * @param position		显示方位
	  * @param info			附加信息
	  * @param infoPosRadiusPercentage	信息显示在总半径指定比例所在位置
	  * @param paint		用来绘制用的画笔
	  */
    public void addAttributeInfo(XEnum.AttributeInfoLoction  position ,String info,
    		float infoPosRadiusPercentage  ,Paint paint) { 
    	
    	if(null == mAttrInfoLocation) mAttrInfoLocation = new ArrayList<XEnum.AttributeInfoLoction> ();
    	if(null == mAttrInfo) mAttrInfo = new ArrayList<String>();
    	
    	if(null == mAttrInfoPostion) mAttrInfoPostion = new ArrayList<Float>();    	
    	if(null == mAttrInfoPaint) mAttrInfoPaint = new ArrayList<Paint>();
    	    	
    	mAttrInfoLocation.add(position);
    	mAttrInfo.add(info);
    	mAttrInfoPostion.add(infoPosRadiusPercentage);
    	mAttrInfoPaint.add(paint);    	
    }
	    
	
    /**
     * 绘制指针
     * @param canvas	画布
     */
	private void renderPointerLine(Canvas canvas)
	{						
		float currentRadius = mul(this.getRadius() ,mPointerRadiusPercentage); 
		float cirX = plotArea.getCenterX();
		float cirY = plotArea.getCenterY();
		MathHelper.getInstance().calcArcEndPointXY(cirX, cirY, currentRadius,add(mPointerAngle , INIT_ANGLE));	
		float endX = MathHelper.getInstance().getPosX();
		float endY = MathHelper.getInstance().getPosY();	
        canvas.drawLine(cirX, cirY, endX,endY, mPaintPointerLine);     
	}
	
	/**
	 * 绘制指针底部的圆
	 * @param canvas 画布
	 */
	private void renderPinterCircle(Canvas canvas)
	{
		float cirX = plotArea.getCenterX();
		float cirY = plotArea.getCenterY();
		canvas.drawCircle(cirX, cirY, mul(this.getRadius() ,0.05f), mPaintPinterCircle);
	}
	

	/**
	 * 增加 标签环形轴
	 * @param radiusPercentage 显示在总半径的指定比例所在位置
	 * @param labels 标签集合
	 */
	public void addTicksAxis(float radiusPercentage,List<String> labels)
	{
		RoundAxisRender roundAxis = new RoundAxisRender();	
		roundAxis.setRoundAxisType(XEnum.RoundAxisType.TICKAXIS);	
		roundAxis.setRadiusPercentage(radiusPercentage);
		roundAxis.setAxisLabels(labels);		
		mRoundAxis.add(roundAxis);
	}
	
	/**
	 * 增加空心环形轴集合
	 * @param outerRadiusPercentage	外环显示在总半径的指定比例所在位置
	 * @param innerRadiusPercentage	内环显示在总半径的指定比例所在位置
	 * @param percentage	百分比
	 * @param color	颜色
	 */
	public void addStrokeRingAxis(float outerRadiusPercentage,float innerRadiusPercentage,
			List<Float> percentage,List<Integer> color)
	{	
		addRingAxis(outerRadiusPercentage,innerRadiusPercentage,percentage,color,null);		
	} 
			
	/**
	 * 增加空心环形轴集合
	 * @param outerRadiusPercentage	外环显示在总半径的指定比例所在位置
	 * @param innerRadiusPercentage	内环显示在总半径的指定比例所在位置
	 * @param percentage	百分比
	 * @param color	产色
	 * @param labels	标签集合
	 */
	public void addStrokeRingAxis(float outerRadiusPercentage,float innerRadiusPercentage,
									List<Float> percentage,List<Integer> color,List<String> labels)
	{		
		addRingAxis(outerRadiusPercentage,innerRadiusPercentage,percentage,color,labels);		
	} 
	
	
	/**
	 * 增加实心环形轴集合
	 * @param radiusPercentage	显示在总半径的指定比例所在位置
	 * @param percentage	百分比
	 * @param color	颜色
	 */
	public void addFillRingAxis(float radiusPercentage,List<Float> percentage,List<Integer> color)
	{
		addRingAxis(radiusPercentage,0.0f,percentage,color,null);
	}   
	
	/**
	 *  增加实心环形轴集合
	 * @param radiusPercentage 显示在总半径的指定比例所在位置
	 * @param percentage	百分比
	 * @param color	颜色
	 * @param labels	标签集合
	 */
	public void addFillRingAxis(float radiusPercentage,
										List<Float> percentage,List<Integer> color,List<String> labels)
	{
		addRingAxis(radiusPercentage,0.0f,percentage,color,labels);
	}   
	

	/**
	 * 增加环形轴集合
	 * @param outerRadiusPercentage	外环显示在总半径的指定比例所在位置
	 * @param innerRadiusPercentage	内环显示在总半径的指定比例所在位置
	 * @param percentage	百分比
	 * @param color	颜色
	 * @param labels	标签集合
	 */
	public void addRingAxis(float outerRadiusPercentage,float innerRadiusPercentage,
										List<Float> percentage,List<Integer> color,List<String> labels)
	{						
		RoundAxisRender roundAxis = new RoundAxisRender();	
		roundAxis.setRoundAxisType(XEnum.RoundAxisType.RINGAXIS);
		roundAxis.setRadiusPercentage(outerRadiusPercentage);
		roundAxis.setRingInnerRadiusPercentage(innerRadiusPercentage);	
		roundAxis.setAxisPercentage(percentage);		
		roundAxis.setAxisColor(color);
		roundAxis.setAxisLabels(labels);
		
		mRoundAxis.add(roundAxis);
	}
	
	/**
	 * 增加弧线轴
	 * @param radiusPercentage	显示在总半径的指定比例所在位置
	 */
	public void addArcLineAxis(float radiusPercentage)
	{
		RoundAxisRender roundAxis = new RoundAxisRender();	
		roundAxis.setRoundAxisType(XEnum.RoundAxisType.ARCLINEAXIS);
		roundAxis.setRadiusPercentage(radiusPercentage);
		mRoundAxis.add(roundAxis);
	}
	
	/**
	 * 增加填充环形轴
	 * @param radiusPercentage	显示在总半径的指定比例所在位置
	 * @param color	颜色
	 */
	public void addFillAxis(float radiusPercentage,int color)
	{
		RoundAxisRender roundAxis = new RoundAxisRender();	
		roundAxis.setRoundAxisType(XEnum.RoundAxisType.FILLAXIS);
		roundAxis.setRadiusPercentage(radiusPercentage);
		List<Integer> lstColor = new ArrayList<Integer>();
		lstColor.add(color);		
		roundAxis.setAxisColor(lstColor);
		mRoundAxis.add(roundAxis);
	}
	
	
	/**
	 * 增加填充圆轴
	 * @param radiusPercentage	显示在总半径的指定比例所在位置
	 * @param color	颜色
	 */
	public void addCircleAxis(float radiusPercentage,int color)
	{
		RoundAxisRender roundAxis = new RoundAxisRender();	
		roundAxis.setRoundAxisType(XEnum.RoundAxisType.CIRCLEAXIS);
		roundAxis.setRadiusPercentage(radiusPercentage);
		List<Integer> lstColor = new ArrayList<Integer>();
		lstColor.add(color);		
		roundAxis.setAxisColor(lstColor);
		mRoundAxis.add(roundAxis);
	}
	
	
	/**
	 * 清空当前所有轴数据集合
	 */
	public void clearData()
	{
		if(null != mRoundAxis) mRoundAxis.clear();	
		
		if(null != mAttrInfoLocation) mAttrInfoLocation.clear();	
		if(null != mAttrInfo) mAttrInfo.clear();	
		if(null != mAttrInfoPostion) mAttrInfoPostion.clear();	
		if(null != mAttrInfoPaint) mAttrInfoPaint.clear();	
		

		
	}

	
	private void renderAttrInfo(Canvas canvas)
	{		
		if(null == mAttrInfo) return ;
		if(null == mAttrInfoLocation) return ;
		float radius = 0.0f; 
		String info = "";				
		PointF pos = new PointF();
	
		for(int i=0;i<mAttrInfo.size();i++)
		{
			info = mAttrInfo.get(i);
			if("" == info) continue;
			
			if(null == mAttrInfoPostion || mAttrInfoPostion.size() < i)continue;	
			if(null == mAttrInfoPaint.get(i) || mAttrInfoPaint.size() < i) continue;
			
			pos.x =  plotArea.getCenterX();
			pos.y =  plotArea.getCenterY();
			
			radius = this.getRadius() * mAttrInfoPostion.get(i);
			switch(mAttrInfoLocation.get(i))
			{
				case TOP:
					pos.y =  plotArea.getCenterY() - radius;
					break;
				case BOTTOM:
					pos.y =  plotArea.getCenterY() + radius;
					break;
				case LEFT:
					pos.x =  plotArea.getCenterX() - radius;
					break;
				case RIGHT:
					pos.x =  plotArea.getCenterX() + radius;
					break;
			}	    							
			canvas.drawText(info, pos.x, pos.y, mAttrInfoPaint.get(i));
		}
	}
	
	
	/**
	 * 绘制图
	 */
	protected void renderPlot(Canvas canvas)
	{
		try{									
			//画上各组环形轴
			for(int i = 0; i < this.mRoundAxis.size(); i++)  
	        {  				
				RoundAxisRender roundAxis = (RoundAxisRender)mRoundAxis.get(i);
				roundAxis.setCenterXY(plotArea.getCenterX(), plotArea.getCenterY());
				roundAxis.setAngleInfo(FIX_TOTAL_ANGLE, INIT_ANGLE);	
				roundAxis.setOrgRadius(this.getRadius());
				roundAxis.render(canvas);
	        }  		
			
			//绘制附加信息
			renderAttrInfo(canvas);
			
			//最后再画指针
			 renderPointerLine(canvas);
			 //画上指针尾部的白色圆心
			 renderPinterCircle(canvas);			 
			 
		}catch( Exception e){
			Log.e(TAG,e.toString());
		}
		
	}

				
	@Override
	protected boolean postRender(Canvas canvas) throws Exception 
	{
		try {
			super.postRender(canvas);
			
			//绘制图表
			renderPlot(canvas);
		} catch (Exception e) {
			throw e;
		}
		return true;
	}
	
	
	

}
