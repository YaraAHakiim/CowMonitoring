package com.example.veto.cowmonitoring;

import java.sql.Date;

public class NodeData {
	
	String tempData;
	String soundData;
	String time;
	String status;
		
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getTempData() {
		return tempData;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public void setTempData(String tempData) {
		this.tempData = tempData;
	}
	public String getSoundData() {
		return soundData;
	}
	public void setSoundData(String soundData) {
		this.soundData = soundData;
	}
	
}
