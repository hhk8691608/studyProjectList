package com.mark.dockerproject.utils;

import java.util.Random;
import java.util.UUID;

public class GeneralUtil {


    //得到指定数量的UUID，以数组的形式返回
    private static String[] getUUID(int num){

        if( num <= 0)
            return null;

        String[] uuidArr = new String[num];

        for (int i = 0; i < uuidArr.length; i++) {
            uuidArr[i] = getUUID32();
        }

        return uuidArr;
    }

    //得到32位的uuid
    public static String getUUID32(){
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

    //得到64位的uuid
    public static String getUUID64(){

        String[] strArr = (String[]) getUUID(64);
        StringBuffer stringBuffer = new StringBuffer();
        for(String str:strArr){
            stringBuffer.append(str);
        }
      return stringBuffer.toString();
    }


    public static void main(String[] args) {

//        String uuid = getUUID64();
////        System.out.printf("uuid = "+ uuid);

//        for(int i=0;i<10;i++){
//            int len = new Random().nextInt(100);
//            System.out.println(len);
//        }




    }


    public static int ramdom9(){
        Random random = new Random();
        int value = random.nextInt(1000000000);
        return value;
    }




}
