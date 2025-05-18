package com.zhang.txa.annotation.service;

import com.zhang.txa.annotation.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class BookService {
	@Autowired
	private BookDao bookDao;

	public BookDao getBookDao() {
		return bookDao;
	}

	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	/**
	 * 结账：传入那个用户买了哪本书
	 * @param username
	 * @param id
	 */
	@Transactional
	public void checkout(String  username, int id) {
		bookDao.updateStock(id);
		int price = bookDao.getPrice(id);
		bookDao.updateBalance(username,price);
	}
}
