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

import android.graphics.Color;
import android.graphics.Paint;

/**
 * @ClassName PlotKey
 * @Description 用于设定key的属性
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 *
 */
public class PlotKey {
	
	//数据集的说明描述与图这间的空白间距
		private float mDataKeyMargin  = 10f;	
		//数据集的说明描述画笔
		private Paint mDataKeyPaint = null;
			
		//是否显示Key
		private boolean mKeyLabelVisible = false;
		
		public PlotKey() {
			initChart();		
		}
		/**
		 * 初始化设置
		 */
		private void initChart()
		{	
			mDataKeyPaint = new Paint();
			mDataKeyPaint.setColor(Color.BLACK);
			mDataKeyPaint.setAntiAlias(true);			
			mDataKeyPaint.setTextSize(15);
			//mDataKeyPaint.setStyle(Style.FILL);
			
		}

		/**
		 * 在图的上方显示键值(key)的标签说明
		 * 
		 */
		public void showKeyLabels()
		{
			mKeyLabelVisible = true;
		}
		
		/**
		 * 在图的上方不显示键值(key)的标签说明
		 */
		public void hideKeyLabels()
		{
			mKeyLabelVisible = false;
		}
		
		
		
		/**
		 * 是否需绘制图的key
		 * @return 是否显示
		 */
		public boolean isShowKeyLabels()
		{
			return mKeyLabelVisible;
		}
		 
		 /**
		  * 开放Key绘制画笔
		  * @return 画笔
		  */
		 public Paint getKeyLabelPaint()
		 {		 
			 return mDataKeyPaint;
		 }
		 
		 /**
		  * 设置Key间距
		  * @param margin Key间距
		  */
		 public void setKeyLabelMargin(float margin)
		 {		 
			 mDataKeyMargin = margin;
		 }
		 
		 /**
		  * 返回Key间距
		  * @return Key间距
		  */
		 public float getKeyLabelMargin()
		 {
			 return mDataKeyMargin;
		 }

		 
}
