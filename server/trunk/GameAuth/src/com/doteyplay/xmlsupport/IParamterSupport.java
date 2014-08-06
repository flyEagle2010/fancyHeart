package com.doteyplay.xmlsupport;


public interface IParamterSupport
{
	public void putParamter(ISimpleParamters element);

	public void endParamter(String paramtername);
	
	public void onComplete();
}
