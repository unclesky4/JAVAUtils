package org.unclesky4.Utils.date;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

/**
* 测试加载logback配置文件  slf4j+logback （须注释掉slf4j-log4j12依赖包）
* @author unclesky4
* @date Feb 23, 2018 12:49:05 AM
*
* slf4j+logback / slf4j+log4j 二选一
* Maven同时加载依赖包会提示：SLF4J: Class path contains multiple SLF4J bindings.并默认绑定调用log4j
*
*
*1.logback首先会试着查找logback.groovy文件;
2.当没有找到时，继续试着查找logback-test.xml文件;
3.当没有找到时，继续试着查找logback.xml文件;
4.如果仍然没有找到，则使用默认配置（打印到控制台）







slf4j是一系列的日志接口，而log4j logback是具体实现了的日志框架
--》log4j是apache实现的一个开源日志组件。（Wrapped implementations）
--》logback同样是由log4j的作者设计完成的，拥有更好的特性，用来取代log4j的一个日志框架。是slf4j的原生实现。
*/

public class Load_logback {
	
	public static void load (String externalConfigFileLocation) throws IOException, JoranException{  
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();  
          
        File externalConfigFile = new File(externalConfigFileLocation);  
        if(!externalConfigFile.exists()){  
            throw new IOException("Logback External Config File Parameter does not reference a file that exists");  
        }else{  
            if(!externalConfigFile.isFile()){  
                throw new IOException("Logback External Config File Parameter exists, but does not reference a file");  
            }else{  
                if(!externalConfigFile.canRead()){  
                    throw new IOException("Logback External Config File exists and is a file, but cannot be read.");  
                }else{  
                    JoranConfigurator configurator = new JoranConfigurator();  
                    configurator.setContext(lc);  
                    lc.reset();  
                    configurator.doConfigure(externalConfigFileLocation);  
                    StatusPrinter.printInCaseOfErrorsOrWarnings(lc);  
                }  
            }     
        }  
    }  
	public static void main(String[] args) throws IOException, JoranException {
		Load_logback.load("src/resources/logback.xml"); 
		Logger logger = LoggerFactory.getLogger(Load_logback.class);
		logger.error("测试slf4j+logback");
	}

}
