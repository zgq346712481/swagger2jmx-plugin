package com.lyl.plugin;


import com.lyl.plugin.generate.MyDefaultGenerator;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * 生成器，程序主入口
 * @author yunlong.liu
 * @date 2020-11-03 19:42:43
 */

public class JmxGenerator {

    /**
     * get请求
     * @param url
     * @return
     * @throws IOException
     */
    public static String getHttpByOkHttp(String url) throws IOException {
        Response response=null;
        String responseStr="";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        try{
            response = call.execute();
//            System.out.println("get="+response.body().string());
            responseStr = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseStr;
    }


    public static void main(String[] args) {

        //url---下载swagger-json
//        String responseStr= getHttpByOkHttp(swaggerSourceLocation);
//        System.out.println("responseStr----------"+responseStr);


        String JMX_FILE_DIR="/media/zgq/data/code/git.youlu.com/dev-test-team-project/api-test/swaggerTest/swagger2jmx-plugin/src/main/resources/jmx";
        String moudle="hr"; //hr时，文件不健全，有bug
//        String SWAGGER_LOCATION ="http://47.97.154.153:8201/"+moudle+"/v2/api-docs";
        //json文件路径  在线演示　https://petstore.swagger.io/v2/swagger.json
//        String SWAGGER_LOCATION ="/media/zgq/data/code/git.youlu.com/dev-test-team-project/api-test/swaggerTest/swagger2jmx-plugin/src/main/resources/swaggerJson/swagger.json";
        String SWAGGER_LOCATION ="/media/zgq/data/code/git.youlu.com/dev-test-team-project/api-test/swaggerTest/swagger2jmx-plugin/src/main/resources/swaggerJson/jxc_Swagger.json";

        // generate -i swagger.json -g jmeter

//        EnvArgs envArgs= parseArgs(args);

        MyDefaultGenerator myDefaultGenerator=new MyDefaultGenerator();
        try {
//            myDefaultGenerator.generate(envArgs.getSwaggerAddr(),envArgs.getFileOutput());
            myDefaultGenerator.generate(SWAGGER_LOCATION,JMX_FILE_DIR);
            System.out.println("++++++++生成好了 ,文件地址:"+JMX_FILE_DIR+"/auto_test.jmx");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


     static EnvArgs parseArgs(String [] args){

//       if(args==null ||  args.length <2){
//           System.err.println("请通过参数swagger地址和脚本输出目录");
//           System.exit(1);
//       }
        // "--i=http://localhost:18083/v2/api-docs";
        // "--o=D:/";
          EnvArgs envArgs=new  EnvArgs();
        for(int i=0;i<args.length;i++){
            if(args[i].startsWith("--i=")){
                String [] source= StringUtils.split(args[i],"=");
                if(source.length==2) {
                    envArgs.setSwaggerAddr(source[1].trim());
                }
            }

            if(args[i].startsWith("--o=")){
                String [] source= StringUtils.split(args[i],"=");
                if(source.length==2) {
                    envArgs.setFileOutput(source[1].trim());
                }
            }

            if(StringUtils.isNotBlank(envArgs.getSwaggerAddr()) && StringUtils.isNotBlank(envArgs.getFileOutput()) ){
                break;
            }
        }

        if(StringUtils.isBlank(envArgs.getSwaggerAddr())){
            System.err.println("请通过[--i=]方式声明swagger地址");
            System.exit(1);
        }

        if(StringUtils.isBlank(envArgs.getFileOutput())){
              System.err.println("请通过[--o=]方式声明生成jmeter脚本目录");
              System.exit(1);
        }

          return envArgs;
    }
}
