package com.test.alice;

import java.util.List;

/**
 * Created by Alice on 2018/3/9.
 */

public class PassportModel {


    /**
     * data : {"ret":[{"charset":[{"rect":{"top":150,"left":340,"width":25,"height":31},"word":"胡"},{"rect":{"top":150,"left":391,"width":25,"height":31},"word":"晓"},{"rect":{"top":148,"left":445,"width":25,"height":32},"word":"菲"}],"word_name":"姓名","word":"胡晓菲"},{"charset":[{"rect":{"top":85,"left":719,"width":21,"height":29},"word":"E"},{"rect":{"top":85,"left":751,"width":15,"height":29},"word":"9"},{"rect":{"top":85,"left":770,"width":14,"height":29},"word":"4"},{"rect":{"top":85,"left":797,"width":15,"height":29},"word":"4"},{"rect":{"top":85,"left":815,"width":15,"height":29},"word":"9"},{"rect":{"top":85,"left":843,"width":15,"height":29},"word":"4"},{"rect":{"top":85,"left":870,"width":15,"height":29},"word":"8"},{"rect":{"top":85,"left":889,"width":15,"height":29},"word":"3"},{"rect":{"top":85,"left":917,"width":14,"height":29},"word":"3"}],"word_name":"护照号码","word":"E94494833"},{"charset":[{"rect":{"top":333,"left":337,"width":21,"height":24},"word":"河"},{"rect":{"top":333,"left":361,"width":20,"height":24},"word":"南"}],"word_name":"签发地","word":"河南"},{"charset":[{"rect":{"top":265,"left":336,"width":19,"height":24},"word":"女"}],"word_name":"性别","word":"女"}],"isStructured":true,"logId":"152092267474343"}
     * error_code : 0
     * error_msg :
     */

    public DataBean data;
    public int error_code;
    public String error_msg;

    public static class DataBean {
        /**
         * ret : [{"charset":[{"rect":{"top":150,"left":340,"width":25,"height":31},"word":"胡"},{"rect":{"top":150,"left":391,"width":25,"height":31},"word":"晓"},{"rect":{"top":148,"left":445,"width":25,"height":32},"word":"菲"}],"word_name":"姓名","word":"胡晓菲"},{"charset":[{"rect":{"top":85,"left":719,"width":21,"height":29},"word":"E"},{"rect":{"top":85,"left":751,"width":15,"height":29},"word":"9"},{"rect":{"top":85,"left":770,"width":14,"height":29},"word":"4"},{"rect":{"top":85,"left":797,"width":15,"height":29},"word":"4"},{"rect":{"top":85,"left":815,"width":15,"height":29},"word":"9"},{"rect":{"top":85,"left":843,"width":15,"height":29},"word":"4"},{"rect":{"top":85,"left":870,"width":15,"height":29},"word":"8"},{"rect":{"top":85,"left":889,"width":15,"height":29},"word":"3"},{"rect":{"top":85,"left":917,"width":14,"height":29},"word":"3"}],"word_name":"护照号码","word":"E94494833"},{"charset":[{"rect":{"top":333,"left":337,"width":21,"height":24},"word":"河"},{"rect":{"top":333,"left":361,"width":20,"height":24},"word":"南"}],"word_name":"签发地","word":"河南"},{"charset":[{"rect":{"top":265,"left":336,"width":19,"height":24},"word":"女"}],"word_name":"性别","word":"女"}]
         * isStructured : true
         * logId : 152092267474343
         */

        public boolean isStructured;
        public List<RetBean> ret;

        public static class RetBean {
            /**
             * charset : [{"rect":{"top":150,"left":340,"width":25,"height":31},"word":"胡"},{"rect":{"top":150,"left":391,"width":25,"height":31},"word":"晓"},{"rect":{"top":148,"left":445,"width":25,"height":32},"word":"菲"}]
             * word_name : 姓名
             * word : 胡晓菲
             */

            public String word_name;
            public String word;
            public List<CharsetBean> charset;

            public static class CharsetBean {
                /**
                 * rect : {"top":150,"left":340,"width":25,"height":31}
                 * word : 胡
                 */

                public RectBean rect;
                public String word;

                public static class RectBean {
                    /**
                     * top : 150
                     * left : 340
                     * width : 25
                     * height : 31
                     */

                    public int top;
                    public int left;
                    public int width;
                    public int height;
                }
            }
        }
    }
}
