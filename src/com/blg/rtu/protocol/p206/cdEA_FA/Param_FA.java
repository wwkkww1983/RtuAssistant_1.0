package com.blg.rtu.protocol.p206.cdEA_FA;

import java.io.Serializable;


public class Param_FA implements Serializable{
	
	private static final long serialVersionUID = 201409240057001L;

	public static final String KEY = Param_FA.class.getName() ;


	private Integer enable_1 ;//1路AD (有效1 无效0)
	private Integer enable_2 ;//2路AD (有效1 无效0)
	private Integer enable_3 ;//3路AD (有效1 无效0)
	
	private Float value_1_n7999d99To7999d99 ;//井口高程
	private Float value_2_0To99d99 ;//水井深度
	private Float value_3_0To99d99 ;//探头埋深
	

	public String toString(){
		String s = "\n" ;
		s += "井口高程，水井深度，探头埋深：" + "\n" +
				" 井口高程：" + (enable_1.intValue() == 1?"有效":"无效") + " 校准值：" + value_1_n7999d99To7999d99 + "\n" +
				" 水井深度：" + (enable_2.intValue() == 1?"有效":"无效") + " 校准值：" + value_2_0To99d99 + "\n" +
				" 探头埋深：" + (enable_3.intValue() == 1?"有效":"无效") + " 校准值：" + value_3_0To99d99 + "\n" ;
		return s ;
	}


	public Integer getEnable_1() {
		return enable_1;
	}


	public void setEnable_1(Integer enable_1) {
		this.enable_1 = enable_1;
	}


	public Integer getEnable_2() {
		return enable_2;
	}


	public void setEnable_2(Integer enable_2) {
		this.enable_2 = enable_2;
	}


	public Integer getEnable_3() {
		return enable_3;
	}


	public void setEnable_3(Integer enable_3) {
		this.enable_3 = enable_3;
	}


	public Float getValue_1_n7999d99To7999d99() {
		return value_1_n7999d99To7999d99;
	}


	public void setValue_1_n7999d99To7999d99(Float value_1_n7999d99To7999d99) {
		this.value_1_n7999d99To7999d99 = value_1_n7999d99To7999d99;
	}


	public Float getValue_2_0To99d99() {
		return value_2_0To99d99;
	}


	public void setValue_2_0To99d99(Float value_2_0To99d99) {
		this.value_2_0To99d99 = value_2_0To99d99;
	}


	public Float getValue_3_0To99d99() {
		return value_3_0To99d99;
	}


	public void setValue_3_0To99d99(Float value_3_0To99d99) {
		this.value_3_0To99d99 = value_3_0To99d99;
	}


}
