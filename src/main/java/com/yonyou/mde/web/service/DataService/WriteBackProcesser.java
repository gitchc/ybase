package com.yonyou.mde.web.service.DataService;

import cn.hutool.core.thread.ThreadUtil;
import com.yonyou.mde.constant.DataEventType;
import com.yonyou.mde.model.processor.BaseEventDataProcessor;
import com.yonyou.mde.queue.dto.DataEvent;
import com.yonyou.mde.web.utils.SpringUtil;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Description: 回写库
 * @Author chenghch
 * @Date 2021/1/20 10:47
 */
@Transactional
public class WriteBackProcesser extends BaseEventDataProcessor {
    @Override
    public DataEvent processEventSingle(String cubeName, DataEvent dataEvent) {
        this.processBatch(cubeName, dataEvent.getData(), dataEvent.getType());
        return dataEvent;
    }

    public List<Map<String, Object>> processBatch(String cubeName, List<Map<String, Object>> rawRows, byte eventType) {
        Iterator var3 = rawRows.iterator();
        while (var3.hasNext()) {
            Map<String, Object> rawRow = (Map) var3.next();
            processSingle(cubeName, rawRow, eventType);
        }

        return rawRows;
    }

    @Override
    public Map<String, Object> processSingle(String cubeName, Map<String, Object> rawRow) {
        return rawRow;
    }

    public void processSingle(String cubeName, Map<String, Object> rawRow, byte eventType) {
        CubeDataService dataService = SpringUtil.getBean(CubeDataService.class);
        switch (DataEventType.of(eventType)) {
            case UNKNOWN:
                break;
            case INSERT:
            case UPDATE:
                ThreadUtil.execAsync(() -> {//异步执行,不保证数据的准确性
                    dataService.insertOrUpdate(cubeName, rawRow);
                });
                break;
            case DELETE:
                ThreadUtil.execAsync(() -> {//异步执行,不保证数据的准确性,硬删除
                    dataService.deleteValue(cubeName, rawRow, false);
                });

                break;
            default:
                throw new IllegalStateException("Unexpected value: " + eventType);
        }

    }
}
