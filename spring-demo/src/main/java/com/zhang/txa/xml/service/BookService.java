package com.zhang.txa.xml.service;

import com.zhang.txa.xml.dao.BookDao;
public class BookService {
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
	public void checkout(String  username, int id) {
		try {
			bookDao.updateStock(id);
		}catch (Exception e){
			e.printStackTrace();
		}
//		int price = bookDao.getPrice(id);
//		bookDao.updateBalance(username,price);
	}
}
