package org.lanit.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AlertsItem{
	@JsonProperty("timeFrame")
	private int timeFrame;
	@JsonProperty("percent")
	private int percent;



	public AlertsItem() {}

	public AlertsItem(int timeFrame, int percent) {
		this.timeFrame=timeFrame;
		this.percent=percent;
	}

	public void setPercent(int percent){
		this.percent = percent;
	}

	public int getPercent(){
		return percent;
	}

	public void setTimeFrame(int timeFrame){
		this.timeFrame = timeFrame;
	}

	public int getTimeFrame(){
		return timeFrame;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AlertsItem that = (AlertsItem) o;
		return timeFrame == that.timeFrame;
	}
}