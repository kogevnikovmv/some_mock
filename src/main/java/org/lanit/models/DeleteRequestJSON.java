package org.lanit.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteRequestJSON {
	@JsonProperty("info")
	private Info info;
	@JsonProperty("delete")
	private Delete delete;
	@JsonProperty("uuid")
	private String uuid;
	@JsonProperty("lastUpdate")
	private String lastUpdate;


	public void setLastUpdate(String lastUpdate){
		this.lastUpdate = lastUpdate;
	}

	public String getLastUpdate(){
		return lastUpdate;
	}

	public void setDelete(Delete delete){
		this.delete = delete;
	}

	public Delete getDelete(){
		return delete;
	}

	public void setUuid(String uuid){
		this.uuid = uuid;
	}

	public String getUuid(){
		return uuid;
	}

	public void setInfo(Info info){
		this.info = info;
	}

	public Info getInfo(){
		return info;
	}
}