package com.lineage.server.templates;

import java.util.Calendar;

public class L1AuctionBoardTmp {
	
	public L1AuctionBoardTmp() {
	}

	private int _houseId;

	public int getHouseId() {
		return this._houseId;
	}

	public void setHouseId(final int i) {
		this._houseId = i;
	}

	private String _houseName;

	public String getHouseName() {
		return this._houseName;
	}

	public void setHouseName(final String s) {
		this._houseName = s;
	}

	private int _houseArea;

	public int getHouseArea() {
		return this._houseArea;
	}

	public void setHouseArea(final int i) {
		this._houseArea = i;
	}

	private Calendar _deadline;

	public Calendar getDeadline() {
		return this._deadline;
	}

	public void setDeadline(final Calendar i) {
		this._deadline = i;
	}

	private long _price;// 目前售價

	/**
	 * 傳回目前售價
	 * @return
	 */
	public long getPrice() {
		return this._price;
	}

	/**
	 * 設定目前售價
	 * @param i
	 */
	public void setPrice(final long i) {
		this._price = i;
	}

	private String _location;

	public String getLocation() {
		return this._location;
	}

	public void setLocation(final String s) {
		this._location = s;
	}

	private String _oldOwner;// 原所有人名稱

	/**
	 * 傳回原所有人名稱
	 * @return
	 */
	public String getOldOwner() {
		return this._oldOwner;
	}

	/**
	 * 設定原所有人名稱
	 * @param s
	 */
	public void setOldOwner(final String s) {
		this._oldOwner = s;
	}

	private int _oldOwnerId;// 原所有人OBJID

	/**
	 * 傳回原所有人OBJID
	 * @return
	 */
	public int getOldOwnerId() {
		return this._oldOwnerId;
	}

	/**
	 * 設定原所有人OBJID
	 * @param i
	 */
	public void setOldOwnerId(final int i) {
		this._oldOwnerId = i;
	}

	private String _bidder;// 

	/**
	 * 
	 * @return
	 */
	public String getBidder() {
		return this._bidder;
	}

	/**
	 * 
	 * @param s
	 */
	public void setBidder(final String s) {
		this._bidder = s;
	}

	private int _bidderId;// 目前競標者OBJID

	/**
	 * 傳回目前競標者OBJID
	 * @return
	 */
	public int getBidderId() {
		return this._bidderId;
	}

	/**
	 * 設定目前競標者OBJID
	 * @param i
	 */
	public void setBidderId(final int i) {
		this._bidderId = i;
	}

}