package com.blg.rtu.frmFunction;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blg.rtu.MainActivity;
import com.blg.rtu.R;
import com.blg.rtu.protocol.RtuData;
import com.blg.rtu.protocol.p206.Code206;
import com.blg.rtu.protocol.p206.CommandCreator;
import com.blg.rtu.protocol.p206.cd46_76.Data_46;
import com.blg.rtu.protocol.p206.cd46_76.Data_76;
import com.blg.rtu.protocol.p206.cd46_76.Param_46;
import com.blg.rtu.util.Constant;
import com.blg.rtu.util.DialogAlarm;
import com.blg.rtu.util.ImageUtil;
import com.blg.rtu.util.Preferences;
import com.blg.rtu.vo2xml.Vo2Xml;

public class F_08_060  extends FrmParent {
	
	private final static int requestLen_4 = 4 ; //输入长度
	private final static int requestLen_1 = 1 ; //输入长度
	private final static int requestLen_9 = 9 ; //输入长度
	private TextView title ;

	private EditText item01  ;
	private EditText item02 ;
	private EditText item03 ;
	

	private ImageView btnSet ;
	private ImageView btnRead ;
	

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.act = (MainActivity)activity ;
		this.queryCommandCode = Code206.cd_76 ;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		cntFrmOpened = false ;
		loading = false ;
	}

	@Override
	public View onCreateView(
			LayoutInflater inflater, 
			ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.f_func_08_060, container, false);

		title = (TextView)view.findViewById(R.id.f_08_060_Title) ;
		funcFrm = (FrameLayout)view.findViewById(R.id.f_08_060_Frm) ;
		cover = (LinearLayout)view.findViewById(R.id.f_08_060_Load) ;
		
		item01 = (EditText)view.findViewById(R.id.func_08_060_item01);
		item01.setFilters(new InputFilter[]{new InputFilter.LengthFilter(requestLen_4)});
		item01.addTextChangedListener(new MyTextWatcher(Constant.func_vk_08_060_01));
		
		String str = Preferences.getInstance().getString(Constant.func_vk_08_060_01) ;
		if(!str.equals(Constant.errorStr)){
			item01.setText(str); 
		}
		
		item02 = (EditText)view.findViewById(R.id.func_08_060_item02);
		item02.setFilters(new InputFilter[]{new InputFilter.LengthFilter(requestLen_1)});
		item02.addTextChangedListener(new MyTextWatcher(Constant.func_vk_08_060_02));
		
		str = Preferences.getInstance().getString(Constant.func_vk_08_060_02) ;
		if(!str.equals(Constant.errorStr)){
			item02.setText(str); 
		}
		
		item03 = (EditText)view.findViewById(R.id.func_08_060_item03);
		item03.setFilters(new InputFilter[]{new InputFilter.LengthFilter(requestLen_9)});
		item03.addTextChangedListener(new MyTextWatcher(Constant.func_vk_08_060_03));
		
		str = Preferences.getInstance().getString(Constant.func_vk_08_060_03) ;
		if(!str.equals(Constant.errorStr)){
			item03.setText(str); 
		}
		
		btnSet = (ImageView)view.findViewById(R.id.btn_set);
		btnRead = (ImageView)view.findViewById(R.id.btn_read);
		resultDt = (TextView)view.findViewById(R.id.resultDatetime);
		
		//设置监听器
		title.setOnClickListener(titleClickLisn) ;
		btnSet.setOnClickListener(btnSetLisn);
		btnRead.setOnClickListener(btnReadLisn);
		
		str = Preferences.getInstance().getString(Constant.func_vk_08_060_dt) ;
		if(!str.equals(Constant.errorStr)){
			this.resultDt.setText(str) ;
		}

		return view ;
	}

	
	/**
	 * 查询命令前进行检查
	 * @return
	 */
	@Override
	protected boolean checkBeforeQuery(boolean showDialog){
		return true ;
	}
	
	/**
	 * 设置命令前进行检查
	 * @return
	 */
	@Override
	protected boolean checkBeforeSet(boolean showDialog){
		String value = item01.getText().toString() ;//整数部分
		
		if(value == null || value.equals("")){
			if(showDialog)new DialogAlarm().showDialog(act, "密码必须填写！") ;
			return false ;
		} 
		
		int v = Integer.valueOf(value) ;
		if(v < 0 || v > 9999){
			if(showDialog)new DialogAlarm().showDialog(act, "密码范围必须是0~9999的数字！") ;
			return false ;
		}
		/////////////////////////////////////
		value = item02.getText().toString() ;//整数部分
		if(value == null || value.equals("")){
			if(showDialog)new DialogAlarm().showDialog(act, "Lora通道必须填写！") ;
			return false ;
		} 
		
		v = Integer.valueOf(value) ;
		if(v < 1 || v > 8){
			if(showDialog)new DialogAlarm().showDialog(act, "通道范围必须是1~8的数字！") ;
			return false ;
		}
		/////////////////////////////////////
		value = item03.getText().toString() ;//整数部分
		
		if(value == null || value.equals("")){
			if(showDialog)new DialogAlarm().showDialog(act, "正积流量必须填写！") ;
			return false ;
		} 
		
		v = Integer.valueOf(value) ;
		if(v < 0 || v > 999999999){
			if(showDialog)new DialogAlarm().showDialog(act, "正积流量范围必须是0~999999999的数字！") ;
			return false ;
		}
		return true ;
	}
	
	/**
	 * 查询命令
	 */
	@Override
	protected void queryCommand(){
		this.sendRtuCommand(new CommandCreator().cd_76(null), false) ;
	}
	
	/**
	 * 设置命令
	 */
	@Override
	protected void setCommand(){
		Param_46 p = new Param_46() ;
		String value = item01.getText().toString() ;//整数部分
		if(value == null || value.equals("")){
			p.setPassword("") ;
		}else{
			p.setPassword(value) ;
		}
		//////
		value = item02.getText().toString() ;//整数部分
		if(value == null || value.equals("")){
			p.setLoraChannel(0) ;
		}else{
			p.setLoraChannel(Integer.valueOf(value)) ;
		}
		value = item03.getText().toString() ;//整数部分
		if(value == null || value.equals("")){
			p.setLoraChannel(0) ;
		}else{
			p.setWaterPlus(Long.valueOf(value)) ;
		}
		
		this.sendRtuCommand(new CommandCreator().cd_46(p, null), false) ;
	}
	
	/**
	 * 查询或设置命令发送前检查出问题后的回调
	 */
	@Override
	public void commandHasProblem(){
		this.title.setCompoundDrawables(ImageUtil.getTitlLeftImg_item006(this.act), null, ImageUtil.getTitlRightImg_problem(this.act), null);
	}
	
	/**
	 * 命令已经成功发送给后台服务后的回调
	 */
	@Override
	public void commandSended(){
		this.title.setCompoundDrawables(ImageUtil.getTitlLeftImg_item006(this.act), null, ImageUtil.getTitlRightImg_blue(this.act), null);
	}
	
	/**
	 * 命令已经由后台服务通过网络成功发送后的回调
	 */
	@Override
	public void commandSendedCallBack(){
		this.title.setCompoundDrawables(ImageUtil.getTitlLeftImg_item006(this.act), null, ImageUtil.getTitlRightImg_red(this.act), null); 
	}
	/**
	 * 功能项右侧图标复原
	 */
	@Override
	public void resetLabelImg(){
		this.title.setCompoundDrawables(ImageUtil.getTitlLeftImg_item006(this.act), null, ImageUtil.getTitlRightImg_gray(this.act), null); 
	}

	/**
	 * 收到数据
	 * @param d
	 */
	@Override
	public void receiveRtuData(RtuData d){
		long waterPlus;
		super.receiveRtuData(d) ;
		this.title.setCompoundDrawables(ImageUtil.getTitlLeftImg_item006(this.act), null, ImageUtil.getTitlRightImg_green(this.act), null); 
//		super.scrollTo(this.btnRead) ;
		Object subD = d.subData ;
		if(subD != null){
			if(subD instanceof Data_76){
				Data_76 sd = (Data_76)subD ;
				waterPlus = sd.getWaterPlus().longValue();
				if(waterPlus < 0) {
					waterPlus = - waterPlus;
					if ((waterPlus / 1000) > 0) {
						item03.setText(("-" + waterPlus / 1000) + "." + (waterPlus + 1000) % 1000) ;
					}else if((waterPlus / 1000) == 0){
						item03.setText("-0." + (waterPlus + 1000) % 1000) ;
					}
				}else{
					if ((waterPlus / 1000) > 0) {
						item03.setText((waterPlus / 1000) + "." + (waterPlus + 1000) % 1000) ;
					}else if((waterPlus / 1000) == 0){
						item03.setText("0." + (waterPlus + 1000) % 1000) ;
					}
				}
				
				
				item01.setText("") ;
				item02.setText(sd.getLoraChannel()+"") ;
			}else if(subD instanceof Data_46){
				Data_46 sd = (Data_46)subD ;
				
				waterPlus = sd.getWaterPlus().longValue();
				if(waterPlus < 0) {
					waterPlus = - waterPlus;
					if ((waterPlus / 1000) > 0) {
						item03.setText(("-" + waterPlus / 1000) + "." + (waterPlus + 1000) % 1000) ;
					}else if((waterPlus / 1000) == 0){
						item03.setText("-0." + (waterPlus + 1000) % 1000) ;
					}
				}else{
					if ((waterPlus / 1000) > 0) {
						item03.setText((waterPlus / 1000) + "." + (waterPlus + 1000) % 1000) ;
					}else if((waterPlus / 1000) == 0){
						item03.setText("0." + (waterPlus + 1000) % 1000) ;
					}
				}
				item01.setText(sd.getPassword()) ;
				item02.setText(sd.getLoraChannel()+"") ;
			}
		}
		Preferences.getInstance().putString(Constant.func_vk_08_060_dt, this.resultDt.getText().toString()) ;
	}
	
	/**
	 * 导出设置数据
	 */
	public void outSetData(Vo2Xml vo) {
		//vo.setV_05_070_item01(item01.getText().toString()) ;
	}
	/**
	 * 导入设置数据
	 */
	public void inSetData(Vo2Xml vo) {
		//item01.setText(vo.getV_04_110_item01()) ;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
	
}