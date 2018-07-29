package com.main;

import com.vitea.server.ViteaServer;

/**
 * 服务器启动程序
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        int port=12145;
        try {
        
			new ViteaServer(port).run();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
