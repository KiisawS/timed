package tf56.timed.job;

import com.dangdang.ddframe.job.api.dataflow.DataflowJob;

/***
 * 流式处理基类,默认为false
 * @ClassName MyDataflowJob
 * <p>Description: </p>
 * @author TF015582
 * @date 2018/5/21 16:13
 * <p>Company: 杭州传化货嘀科技有限公司</p> 
 */
abstract class MyDataflowJob<T> implements DataflowJob<T> {

    /**
     * 流式
     * 流式需要记录目前处理的offset,或对已处理的标记
     * 可以该成流式，但是要记录下结算成功/失败的订单号 对已经结算的不再进行处理
     * 这个过滤要在fetchData中处理，根据返回 list是否为空，判断处理是否继续，否则会无线循环
     *
     * 默认为false,非流式
     * @return
     */
    public boolean isStream(){
        return false;
    }

}
