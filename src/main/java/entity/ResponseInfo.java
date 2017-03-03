package entity;

/**
 * Created by zhonghaojian on 15/8/11.
 */
public class ResponseInfo {

    public long code;
    public String detail;

    //1表示成功
    public ResponseInfo() {
        this.code = 1;
        this.detail = "Success";
    }

    public ResponseInfo(long code, String detail){
        this.code = code;
        this.detail = detail;
    }

    public void setFailWithInfo(String reason){
        this.code = 0;
        this.detail = reason;
    }

    public void setSuccessWithInfo(String successInfo){
        this.code = 1;
        this.detail = successInfo;
    }

    public void addFailInfo(String reason){

        if (this.code == 0)
            this.detail = this.detail + ", " + reason;
        else
            this.detail = reason;
    }

    public void addSuccessInfo(String successInfo){

        if (this.code == 1)
            this.detail = this.detail + ", " + successInfo;
        else
            this.detail = successInfo;
    }
}
