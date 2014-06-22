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
package org.xclcharts.renderer.line;

import org.xclcharts.renderer.XEnum;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;

/**
 * @ClassName PlotLines
 * @Description  用于处理线条
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 *  * MODIFIED    YYYY-MM-DD   REASON
 */

public class PlotLine {
	
	private Paint mPaintLine = null;
	private Paint mPaintLabel = null;
	
	private PlotDot mPlotDot = null;
	
	public PlotLine()
	{
		init();
	}
	
	private void init()
	{
		mPaintLine = new Paint();
		mPaintLine.setColor(Color.BLUE);
		mPaintLine.setAntiAlias(true);
		mPaintLine.setStrokeWidth(5);
		
		
		mPaintLabel= new Paint();
		mPaintLabel.setColor(Color.BLUE);
		mPaintLabel.setTextSize(18);
		mPaintLabel.setTextAlign(Align.CENTER);
		mPaintLabel.setAntiAlias(true);
		
		mPlotDot = new PlotDot();
		
	}
	
	/**
	 * 开放线画笔
	 * @return 画笔
	 */
	public Paint getLinePaint()
	{
		return mPaintLine;
	}
	
	/**
	 * 开放标签画笔
	 * @return 画笔
	 */
	public Paint getDotLabelPaint()
	{
		return mPaintLabel;
	}
	
	/**
	 * 开放点画笔
	 * @return 画笔
	 */
	public Paint getDotPaint()
	{
		return mPlotDot.getDotPaint();
	}
	
	/**
	 * 开放点绘制类
	 * @return 点绘制类
	 */
	public PlotDot getPlotDot()
	{
		return mPlotDot;
	}

	/**
	 * 设置点的显示风格
	 * @param style 风格
	 */
	public void setDotStyle( XEnum.DotStyle style)
	{
		mPlotDot.setDotStyle(style);
	}	
	
	/**
	 * 返回点的显示风格
	 * @return 风格
	 */
	public XEnum.DotStyle getDotStyle()
	{
		return mPlotDot.getDotStyle();
	}


}
