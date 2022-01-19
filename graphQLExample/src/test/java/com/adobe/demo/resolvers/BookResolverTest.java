package com.adobe.demo.resolvers;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.adobe.demo.dao.BookDao;
import com.adobe.demo.entity.Book;
import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookResolverTest {
	@Autowired
	private GraphQLTestTemplate template;
	
	@MockBean
	private BookDao bookDao;
	
	@Test
	public void getBook() throws Exception {
		String expected = "{\"data\":{\"bookById\":{\"title\":\"Some title\",\"rating\":5.52}}}";
		
		Book book = new Book();
		book.setId(5);
		book.setTitle("Some title");
		book.setRating(5.52);
		
		Optional<Book> opt = Optional.of(book);
		when(bookDao.findById(5)).thenReturn(opt);
		
		GraphQLResponse response = template.postForResource("get-book.graphqls");
		
		System.out.println(response.getRawResponse().getBody());
//		assertThat(response.isOk(), equalTo(true));
		assertEquals(expected, response.getRawResponse().getBody());
	}
}
