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

package org.xclcharts.renderer;

import android.graphics.Canvas;
import org.xclcharts.common.MathHelper;
import org.xclcharts.renderer.plot.PlotKey;
import org.xclcharts.renderer.plot.PlotKeyRender;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;

/**
 * @ClassName CirChart
 * @Description 圆形类图表，如饼图，刻度盘...类的图表的基类
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 *  
 */

public class CirChart extends XChart{
	
	//半径
	private float mRadius=0.0f;		
	
	//标签注释显示位置 [隐藏,Default,Center,Ouside,Line]
	private XEnum.ArcLabelLocation mLabelLocation;
	
	//开放标签画笔让用户设置
	private Paint mPaintLabel = null;
	
	//初始偏移角度
	protected int mOffsetAgent = 0;//180;
	
	//图的Key基类
	protected PlotKeyRender plotKey = null;
	
		
	public CirChart()
	{
		initChart();
	}
	
	private void initChart()
	{
		//标签显示位置
		mLabelLocation = XEnum.ArcLabelLocation.CENTER;
		
		//key值
		plotKey = new PlotKeyRender(this);
		
		mPaintLabel = new Paint();
		mPaintLabel.setColor(Color.BLACK);
		mPaintLabel.setTextSize(18);
		mPaintLabel.setAntiAlias(true);
	}
	
	
	@Override
	protected void calcPlotRange()
	{
		super.calcPlotRange();
		
		if(isVerticalScreen())
		{
			this.mRadius = this.plotArea.getWidth() / 2;
		}else{
			this.mRadius =  this.plotArea.getHeight() / 2;
		}
	}
	
	/**
	 * 开放图的key基类
	 * @return	基类
	 */
	public PlotKey getPlotKey()
	{
		return plotKey;
	}	

	
	/**
	 * 设置饼图(pie chart)的半径
	 * @param radius 饼图的半径
	 */
	public void setRadius(final float radius)
	{
		mRadius = radius;
	}
	
	/**
	 * 返回半径
	 * @return 半径
	 */
	public float getRadius()
	{
		return mRadius;
	}
	
	/**
	 * 设置饼图(pie chart)起始偏移角度
	 * @param agent 偏移角度
	 */
	public void setInitialAngle(final int agent)
	{
		mOffsetAgent = agent;
	}
	
	/**
	 * 返回图的起始偏移角度
	 * @return 偏移角度
	 */
	public int getInitialAngle()
	{
		return mOffsetAgent;
	}

	/**
	 * 设置标签显示在扇区的哪个位置(里面，外面，隐藏)
	 * @param location 显示位置
	 */
	public void setLabelLocation(XEnum.ArcLabelLocation location)
	{
		mLabelLocation = location;
	}
	
	/**
	 * 开放标签画笔
	 * @return 画笔
	 */
	public Paint getLabelPaint()
	{
		return mPaintLabel;
	}
	
	/**
	 * 绘制标签
	 * @param text	内容
	 * @param cirX	x坐标
	 * @param cirY	y坐标
	 * @param radius	半径
	 * @param offsetAgent	偏移角度
	 * @param curretAgentt	当前角度
	 */
	protected void renderLabel(Canvas canvas, String text,
			final float cirX,
			final float cirY,
			final float radius,		
			final float offsetAgent,
			final float curretAgentt)
	{
		if(XEnum.ArcLabelLocation.HIDE == mLabelLocation) return;
		
		float calcRadius = 0.0f;
		float calcAgent = 0.0f;
		
		mPaintLabel.setTextAlign(Align.CENTER);
		
		if(XEnum.ArcLabelLocation.CENTER == mLabelLocation)
		{			 
				//显示在扇形的中心
				calcRadius = radius - radius/2;
				calcAgent = offsetAgent + curretAgentt/2;
				//计算百分比标签
				MathHelper.getInstance().calcArcEndPointXY(cirX, cirY, calcRadius, calcAgent); 	
					 
				//标识
				canvas.drawText( text ,
						MathHelper.getInstance().getPosX(), MathHelper.getInstance().getPosY() ,mPaintLabel);
		}else if(XEnum.ArcLabelLocation.OUTSIDE == mLabelLocation){
				//显示在扇形的外部
				calcRadius = radius  + radius/10;
				calcAgent = offsetAgent + curretAgentt/2;
				//计算百分比标签
				MathHelper.getInstance().calcArcEndPointXY(cirX, cirY, calcRadius, calcAgent); 	
					 
				//标识
				canvas.drawText(text,
						MathHelper.getInstance().getPosX(), MathHelper.getInstance().getPosY() ,mPaintLabel);          	
		}else{
			return;
		}		 
	}
	
	
	protected void renderPlot()
	{
		try {
			
			
		}catch( Exception e){
			// throw e;
		
		}
	}

	
	@Override
	protected boolean postRender(Canvas canvas) throws Exception 
	{	
		try {
			super.postRender(canvas);
			
			//计算主图表区范围
			 calcPlotRange();
			//画Plot Area背景			
			 plotArea.render(canvas);
			//画奇偶行填充,横竖网格线			
			// plotGrid.render(canvas);
			 
			//绘制标题
			renderTitle(canvas);
		} catch (Exception e) {
			throw e;
		}
		return true;
	}
	
	

}
