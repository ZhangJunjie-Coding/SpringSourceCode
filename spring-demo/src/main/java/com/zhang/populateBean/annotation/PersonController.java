package com.zhang.populateBean.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonController {


	@Autowired
	PersonService personService;

}
