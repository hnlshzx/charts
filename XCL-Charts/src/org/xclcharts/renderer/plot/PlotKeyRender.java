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
 * @version 1.0
 */
package org.xclcharts.renderer.plot;

import java.util.List;

import org.xclcharts.chart.BarData;
import org.xclcharts.chart.LnData;
import org.xclcharts.chart.PieData;
import org.xclcharts.common.DrawHelper;
import org.xclcharts.renderer.XChart;
import org.xclcharts.renderer.XEnum;
import org.xclcharts.renderer.line.PlotDot;
import org.xclcharts.renderer.line.PlotDotRender;
import org.xclcharts.renderer.line.PlotLine;

import android.graphics.Canvas;
import android.graphics.Paint.Align;
import android.util.Log;

/**
 * @ClassName PlotKeyRender
 * @Description 用于绘制图表的Key
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 *
 */
public class PlotKeyRender extends PlotKey{
	
	private static final String TAG = "PlotKeyRender";
	
	private PlotArea mPlotArea = null;
	private XChart mXChart = null;
		
	//柱形数据源
	private List<BarData> mDataSet;	

	public PlotKeyRender()
	{
		super();
	}
	
	
	public PlotKeyRender(XChart xChart)
	{
		super();
		mXChart = xChart;		
	}
		
	public void setXChart(XChart xChart)
	{
		mXChart = xChart;
	}
	

	private boolean validateParams()
	{
		if(null == mXChart)
		{
			Log.e(TAG, "图基类没有传过来。");
			return false;
		}
		return true;
	}
	

	/**
	 * 绘制柱形图的Key
	 * @param canvas	画布
	 * @param xChart	基类
	 * @param dataSet	数据集
	 */
	public void renderBarKey(Canvas 	canvas,
							XChart 		xChart,		
							List<BarData> dataSet)
	{
		setXChart(xChart);
		renderBarKey(canvas,dataSet);		
	}
		
	
	
	/**
	 * 绘制柱形键值对应的说明描述
	 * @param canvas	画布
	 * @param dataSet	数据集
	 * @return 是否成功
	 */
	public boolean renderBarKey(Canvas canvas,List<BarData> dataSet) {
		if (false == this.isShowKeyLabels())
			 return true;;
			 
	    if(null == dataSet) return false;
		
		if(!validateParams())return false;		
		if(null == mDataSet)mDataSet = dataSet;
		if(null == mPlotArea)mPlotArea = mXChart.getPlotArea();

		// 图表标题显示位置
		switch ( mXChart.getPlotTitle().getTitleAlign() ) { 
		case CENTER:
		case RIGHT:
			renderBarKeyLeft(canvas);
			break;
		case LEFT:
			renderBarKeyRight(canvas);
			break;
		}
		return false;
	}
	

	/**
	 * 在左边绘制key. <br/>单行可以显示多个Key说明，当一行显示不下时，会自动转到新行
	 * @param canvas 画布
	 */
	private void renderBarKeyLeft(Canvas canvas) {

		float keyTextHeight = DrawHelper.getInstance().getPaintFontHeight(this
				.getKeyLabelPaint());
		float keyLabelsX = mPlotArea.getLeft();
		float keyLabelsY = mPlotArea.getTop() - keyTextHeight;

		// 宽度是个小约定，两倍文字高度即可
		float rectWidth = 2 * keyTextHeight;
		float rectHeight = keyTextHeight;
		float rectOffset = getKeyLabelMargin();
		
		getKeyLabelPaint().setTextAlign(Align.LEFT);
		for (BarData cData : mDataSet) {
			String key = cData.getKey();
			if("" == key) continue;
			
			getKeyLabelPaint().setColor(cData.getColor());
			float strWidth = getKeyLabelPaint().measureText(key, 0,
					key.length());

			if (keyLabelsX + 2 * rectWidth + strWidth > mXChart.getRight()) {
				keyLabelsX = mPlotArea.getLeft();
				keyLabelsY = keyLabelsY + rectHeight * 2;
			}

			canvas.drawRect(keyLabelsX, keyLabelsY, keyLabelsX + rectWidth,
					keyLabelsY - rectHeight, getKeyLabelPaint());

			getKeyLabelPaint().setTextAlign(Align.LEFT);
			DrawHelper.getInstance().drawRotateText(
					key, keyLabelsX + rectWidth + rectOffset,
					keyLabelsY, 0, canvas, getKeyLabelPaint());

			keyLabelsX += rectWidth + strWidth + 2 * rectOffset;
		}

	}


	/**
	 *  在右边绘制key. <br/>显示在右边时，采用单条说明占一行的方式显示
	 * @param canvas
	 */
	private void renderBarKeyRight(Canvas canvas) {
		if (false == isShowKeyLabels())
			return;

		float keyTextHeight = DrawHelper.getInstance().getPaintFontHeight(
														getKeyLabelPaint());
		float keyLablesX = mPlotArea.getRight();
		float keyLablesY = (float) (mXChart.getTop() + keyTextHeight); //mPlotArea

		// 宽度是个小约定，两倍文字高度即可
		float rectWidth = 2 * keyTextHeight;
		float rectHeight = keyTextHeight;
		float rectOffset = getKeyLabelMargin();

		getKeyLabelPaint().setTextAlign(Align.RIGHT);
		for (BarData cData : mDataSet) {
			String key = cData.getKey();
			if("" == key) continue;
			getKeyLabelPaint().setColor(cData.getColor());

			canvas.drawRect(keyLablesX, keyLablesY, keyLablesX - rectWidth,
					keyLablesY + rectHeight, getKeyLabelPaint());

			DrawHelper.getInstance().drawRotateText(
								key, keyLablesX - rectWidth - rectOffset,
								keyLablesY + rectHeight, 0, canvas,
								getKeyLabelPaint());

			keyLablesY += keyTextHeight;
		}

	}
	
		
	/**
	 * 绘制线图的Key
	 * @param canvas	画布
	 * @param dataSet	数据集
	 */
	public void renderLineKey(Canvas canvas, List<LnData> dataSet) {
		if (isShowKeyLabels() == false)
				return;		
		if(null == dataSet) return ;
		if(null == mPlotArea)mPlotArea = mXChart.getPlotArea();
		
		float textHeight = DrawHelper.getInstance().getPaintFontHeight(
													getKeyLabelPaint());
		float rectWidth = 2 * textHeight;
		float currentX = 0.0f;
		float currentY = 0.0f;

		getKeyLabelPaint().setTextAlign(Align.LEFT);
		currentX = mPlotArea.getLeft();
		currentY = mPlotArea.getTop() - 5;

		int totalTextWidth = 0;
		for (LnData cData : dataSet) {
			String key = cData.getLineKey();				
			if("" == key) continue;
			//颜色	
			getKeyLabelPaint().setColor(cData.getLineColor());

			// 竖屏
			int keyTextWidth = DrawHelper.getInstance().getTextWidth(
													getKeyLabelPaint(), key);
			totalTextWidth += keyTextWidth;

			if (totalTextWidth > mPlotArea.getWidth()) {
				currentY -= textHeight;
				currentX = mPlotArea.getLeft();
				totalTextWidth = 0;
			}

            canvas.drawLine(currentX, currentY - textHeight / 2, currentX
					+ rectWidth, currentY - textHeight / 2, getKeyLabelPaint());

            canvas.drawText(cData.getLineKey(), currentX + rectWidth, currentY
					- textHeight / 3, getKeyLabelPaint());

			float dotLeft = currentX + rectWidth / 4;
			float dotRight = currentX + 2 * (rectWidth / 4);

			PlotLine pLine = cData.getPlotLine();

			if (!pLine.getDotStyle().equals(XEnum.DotStyle.HIDE)) {
				PlotDot pDot = pLine.getPlotDot();
				PlotDotRender.getInstance().renderDot(canvas, pDot, dotLeft, currentY, dotRight, currentY
						- textHeight / 2, pLine.getDotPaint()); // 标识图形
			}

			currentX += rectWidth + keyTextWidth + 10;

		}
	}
	
	
	/**
	 * 绘制pie图的Key
	 * @param canvas	画布
	 * @param dataset	数据集
	 */
	public void renderPieKey(Canvas canvas,List<PieData> dataset)
	{
		if (isShowKeyLabels() == false)
			return;		
		if(null == dataset) return ;
		if(null == mPlotArea)mPlotArea = mXChart.getPlotArea();
		    
			
			float textHeight = DrawHelper.getInstance().getPaintFontHeight(
														getKeyLabelPaint());
			float rectWidth = 2 *textHeight;		
			float currentX = 0.0f; 				
			float currentY = 0.0f;
			
			if(!mXChart.isVerticalScreen()) //横屏
			{
				getKeyLabelPaint().setTextAlign(Align.RIGHT);
				currentX = mPlotArea.getRight();
				currentY = this.mPlotArea.getTop() + textHeight;			
			}else{
				getKeyLabelPaint().setTextAlign(Align.LEFT);
				currentX = mPlotArea.getLeft();
				currentY = this.mPlotArea.getBottom();			
			}			
			
			int totalTextWidth = 0;
			for(PieData cData : dataset)
			{
				getKeyLabelPaint().setColor(cData.getSliceColor());							
				if( !this.mXChart.isVerticalScreen()) //横屏
				{								
					canvas.drawRect(currentX			 , currentY,
										  currentX - rectWidth, currentY - textHeight, 
										  getKeyLabelPaint());					
					
					canvas.drawText(cData.getKey(),currentX - rectWidth,
													currentY, getKeyLabelPaint());
					currentY += textHeight;
				
				}else{ //竖屏			
					int keyTextWidth = DrawHelper.getInstance().getTextWidth(
													getKeyLabelPaint(), cData.getKey());
					totalTextWidth += keyTextWidth;
					
					if(totalTextWidth > mPlotArea.getWidth())
					{
						currentY += textHeight;
						currentX = mPlotArea.getLeft();
						totalTextWidth = 0;
					}				
					canvas.drawRect(currentX			   , currentY,
									 currentX + rectWidth, currentY - textHeight, 
									 getKeyLabelPaint());						
					canvas.drawText(cData.getKey(), currentX + rectWidth,
														currentY, getKeyLabelPaint());
					currentX += rectWidth + keyTextWidth + 5;
				}									
			}	
	}


	
	
	
}
