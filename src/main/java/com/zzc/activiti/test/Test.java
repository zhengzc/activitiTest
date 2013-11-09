package com.zzc.activiti.test;

import org.springframework.stereotype.Service;

@Service(value="test")
public class Test {
	public void printMessage(){
		System.out.println("-----------------hello activiti------------------------>");
	}
}
