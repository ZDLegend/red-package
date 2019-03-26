# 随机红包发送和抢红包功能（基于http）

## 接口说明

* 1.发送红包
    * url：/send
    * 请求方式：post
    * 参数（json格式）：
        ```
        {
            "remainSize": "10",
            "remainMoney": "200"
        }
        ```
    * 字段说明：
        * remainSize 发送红包个数
        * remainMoney 红包总共钱数          
    * 返回：
        ```
        {
            "errcode": 200,
            "errmsg": "ok",
            "result": "*********"
        }
        ```
    * 返回字段说明：
        * errcode 返回错误码（200为正确）
        * errmsg 错误原因
        * result 红包ID
              
* 2.抢红包
    * url：/accept/get/{id}
    * 请求方式：get
    * 参数（请求路径参数）：*********
    * 字段说明：id 红包ID
    * 正确返回：
         ```
         {
             "errcode": 200,
             "errmsg": "ok",
         }
         ```
    * 错误返回：
        ```
        {
            "errcode": 500,
            "errmsg": "红包已抢完"
        }
        ```
* 3.拆红包
    * url：/accept/open/{id}
    * 请求方式：get
    * 参数（请求路径参数）：*********
    * 字段说明：id 红包ID
    * 正确返回：
         ```
         {
            "errcode": 200,
            "errmsg": "ok",
            "result": "*********"
         }
         ```
    * 错误返回：
        ```
        {
            "errcode": 500,
            "errmsg": "红包已抢完"
        }
        ```
    * 返回字段说明：
        * errcode 返回错误码（200为正确）
        * errmsg 错误原因
        * result 抢到的红包金额
                  
## 业务说明

* 1.发红包：建立缓存和存数据库两步
* 2.抢红包：由两个接口完成，抢和拆。抢红包的时候入缓存队列，直到队列大于红包个数后停止。只有队列中的用户才可以进行拆红包，
拆红包过程在数据库中完成。