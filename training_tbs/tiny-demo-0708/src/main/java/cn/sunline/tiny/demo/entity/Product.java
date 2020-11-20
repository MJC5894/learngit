package cn.sunline.tiny.demo.entity;
public class Product{//product

	private String productid;//productid

	

	public void setProductid(String productid){
		this.productid=productid;
	}

	public String getProductid(){
		return this.productid;
	}
	private String name;//name

	public void setName(String name){
		this.name=name;
	}

	public String getName(){
		return this.name;
	}
	private String title;//title

	public void setTitle(String title){
		this.title=title;
	}

	public String getTitle(){
		return this.title;
	}
	private java.util.Date creatdate;//creatdate

	public void setCreatdate(java.util.Date creatdate){
		this.creatdate=creatdate;
	}

	public java.util.Date getCreatdate(){
		return this.creatdate;
	}
	private java.util.Date modifydate;//modifydate

	public void setModifydate(java.util.Date modifydate){
		this.modifydate=modifydate;
	}

	public java.util.Date getModifydate(){
		return this.modifydate;
	}
	private Double saletotal;//saletotal

	public void setSaletotal(Double saletotal){
		this.saletotal=saletotal;
	}

	public Double getSaletotal(){
		return this.saletotal;
	}
	private Double minpp;//minpp

	public void setMinpp(Double minpp){
		this.minpp=minpp;
	}

	public Double getMinpp(){
		return this.minpp;
	}
	private Double maxpp;//maxpp

	public void setMaxpp(Double maxpp){
		this.maxpp=maxpp;
	}

	public Double getMaxpp(){
		return this.maxpp;
	}
	private String describe;//describe

	public void setDescribe(String describe){
		this.describe=describe;
	}

	public String getDescribe(){
		return this.describe;
	}
	private Double rate;//rate

	public void setRate(Double rate){
		this.rate=rate;
	}

	public Double getRate(){
		return this.rate;
	}
	private String cyclicity;//cyclicity

	public void setCyclicity(String cyclicity){
		this.cyclicity=cyclicity;
	}

	public String getCyclicity(){
		return this.cyclicity;
	}
	private String state;//state

	public void setState(String state){
		this.state=state;
	}

	public String getState(){
		return this.state;
	}
	private java.util.Date star;//star

	public void setStar(java.util.Date star){
		this.star=star;
	}

	public java.util.Date getStar(){
		return this.star;
	}
	private java.util.Date stop;//stop

	public void setStop(java.util.Date stop){
		this.stop=stop;
	}

	public java.util.Date getStop(){
		return this.stop;
	}
	private String step;//step

	public void setStep(String step){
		this.step=step;
	}

	public String getStep(){
		return this.step;
	}
	private String space1;//space1

	public void setSpace1(String space1){
		this.space1=space1;
	}

	public String getSpace1(){
		return this.space1;
	}
	private String space2;//space2

	public void setSpace2(String space2){
		this.space2=space2;
	}

	public String getSpace2(){
		return this.space2;
	}
	private String space3;//space3

	public void setSpace3(String space3){
		this.space3=space3;
	}

	public String getSpace3(){
		return this.space3;
	}
	private String space4;//space4

	public void setSpace4(String space4){
		this.space4=space4;
	}

	public String getSpace4(){
		return this.space4;
	}
	@Override
	public String toString() {
		return "Product [productid=" + productid + ", name=" + name + ", title=" + title + ", creatdate=" + creatdate
				+ ", modifydate=" + modifydate + ", saletotal=" + saletotal + ", minpp=" + minpp + ", maxpp=" + maxpp
				+ ", describe=" + describe + ", rate=" + rate + ", cyclicity=" + cyclicity + ", state=" + state
				+ ", star=" + star + ", stop=" + stop + ", step=" + step + ", space1=" + space1 + ", space2=" + space2
				+ ", space3=" + space3 + ", space4=" + space4 + "]";
	}
}
