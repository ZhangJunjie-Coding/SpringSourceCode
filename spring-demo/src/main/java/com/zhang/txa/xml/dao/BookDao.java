package com.zhang.txa.xml.dao;

import org.springframework.jdbc.core.JdbcTemplate;

public class BookDao {
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 减库存，减去某本书的库存
	 *
	 * @param id
	 */
	public void updateStock(int id) {
		String sql = "update book_stock set stock = stock-1 where id=?";
		jdbcTemplate.update(sql, id);
		for (int i = 1; i >= 0; i--)
			System.out.println(10 / i);
	}

	/**
	 * 按照图书的id来获取图书的价格
	 *
	 * @param id
	 * @return
	 */
	public int getPrice(int id) {
		String sql = "select price from book where id=?";
		return jdbcTemplate.queryForObject(sql, Integer.class, id);
	}

	/**
	 * 减去某个用户的余额
	 *
	 * @param username
	 * @param price
	 */
	public void updateBalance(String username, int price) {
		String sql = "update account set balance = balance-? where username=?";
		jdbcTemplate.update(sql, price, username);
	}
}
