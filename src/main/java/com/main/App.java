package com.main;

import com.vitea.server.DiscardServer;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        int port=9999;
        try {
        
			new DiscardServer(port).run();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
