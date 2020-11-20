package cn.sunline.tiny.demo.entity;

import java.util.Date;
/**
 * 用于封装所需数据
 * @author MJC
 *
 */
public class UtilEntity {
	private String name;
	private double realp;
	private double rate;
	private String id;
	private String strSpace1;
	private String strSpace2;
	private double diubleSpace1;
	private double diubleSpace2;
	private Date dateSpace1;
	private Date dateSpace2;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getRealp() {
		return realp;
	}
	public void setRealp(double realp) {
		this.realp = realp;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStrSpace1() {
		return strSpace1;
	}
	public void setStrSpace1(String strSpace1) {
		this.strSpace1 = strSpace1;
	}
	public String getStrSpace2() {
		return strSpace2;
	}
	public void setStrSpace2(String strSpace2) {
		this.strSpace2 = strSpace2;
	}
	public double getDiubleSpace1() {
		return diubleSpace1;
	}
	public void setDiubleSpace1(double diubleSpace1) {
		this.diubleSpace1 = diubleSpace1;
	}
	public double getDiubleSpace2() {
		return diubleSpace2;
	}
	public void setDiubleSpace2(double diubleSpace2) {
		this.diubleSpace2 = diubleSpace2;
	}
	public Date getDateSpace1() {
		return dateSpace1;
	}
	public void setDateSpace1(Date dateSpace1) {
		this.dateSpace1 = dateSpace1;
	}
	public Date getDateSpace2() {
		return dateSpace2;
	}
	public void setDateSpace2(Date dateSpace2) {
		this.dateSpace2 = dateSpace2;
	}
	@Override
	public String toString() {
		return "UtilEntity [name=" + name + ", realp=" + realp + ", rate=" + rate + ", id=" + id + ", strSpace1="
				+ strSpace1 + ", strSpace2=" + strSpace2 + ", diubleSpace1=" + diubleSpace1 + ", diubleSpace2="
				+ diubleSpace2 + ", dateSpace1=" + dateSpace1 + ", dateSpace2=" + dateSpace2 + "]";
	}
}
