package com.smartcold.manage.cold.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.smartcold.manage.cold.controller.BaseController;
import com.smartcold.manage.cold.dao.newdb.DevStatusMapper;
import com.smartcold.manage.cold.dao.newdb.StorageDataCollectionMapper;
import com.smartcold.manage.cold.dto.DataResultDto;
import com.smartcold.manage.cold.entity.newdb.CoeffMode;
import com.smartcold.manage.cold.entity.newdb.StorageDataCollectionEntity;
import com.smartcold.manage.cold.service.redis.DataCacheService;
import com.smartcold.manage.cold.service.task.CommDevService;
import com.smartcold.manage.cold.util.R;
import com.smartcold.manage.cold.util.SetUtil;
import com.smartcold.manage.cold.util.StringUtil;
import com.smartcold.manage.cold.util.TimeUtil;
import org.influxdb.InfluxDB;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * 北京中威DEV数据接口 第二代
 * @author Administrator
 *
 */
@Controller
public class DEVDataController extends BaseController {

	@Autowired
	private InfluxDB influxDB;



	private static Gson gson = new Gson();

	public static ConcurrentLinkedQueue<JsonElement[]> concurrentLinkedQueue = new ConcurrentLinkedQueue<JsonElement[]>();
	/**
	 * 项目测试，开始，结束
	 * @param response
	 * @param pmid
	 * @param state
	 * @return
	 */
	@RequestMapping(value = "/changingPMState", method = RequestMethod.POST)
	@ResponseBody
	public Object ChangingPMState(HttpServletResponse response, @RequestParam int pmid,@RequestParam int state) {
        //todo 数据解析
		return  R.newSuccess();
	} 
	/**
	 *http DEV数据上传接口
	 * @param data
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/dataCollection", method = RequestMethod.POST)
	@ResponseBody
	public Object storageDataCollection( HttpServletResponse response,@RequestBody String data) {


		return  R.newSuccess();
	}

	/**
	 * 执行设备数据保存
	 */
    //@Scheduled(fixedRate = 60000)
	public  void saveData(){
    	if(!concurrentLinkedQueue.isEmpty()){
			BatchPoints batchPoints = BatchPoints.database("verify").consistency(InfluxDB.ConsistencyLevel.ALL).build();
    		while (concurrentLinkedQueue.isEmpty()){
				Point point = Point.measurement("DevData").time(System.currentTimeMillis() / 1000, TimeUnit.SECONDS).tag("oid", "1").tag("key", "0").addField("value", "3.3").build();
				batchPoints.point(point);
			}
           this.influxDB.write(batchPoints);
		}
	}


}
