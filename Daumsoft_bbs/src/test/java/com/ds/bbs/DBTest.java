package com.ds.bbs;

import java.sql.Connection;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/root-context.xml" })
public class DBTest {
    
    @Inject
    private DataSource ds;
 
    @Test
    public void DBConnection() throws Exception {
        try (Connection con = ds.getConnection()) {
            System.out.println("＃＃＃＃＃＃＃＃＃＃DB연결 성공＃＃＃＃＃＃＃＃＃＃");
        } catch (Exception e) {
        	System.out.println("＃＃＃＃＃＃＃＃＃＃DB연결 실패＃＃＃＃＃＃＃＃＃＃");
            e.printStackTrace();
        }
    }
    
}
