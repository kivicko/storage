package com.kivilcimeray.storage.read;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kivilcimeray.storage.util.AnnotationHelper;
import com.kivilcimeray.storage.util.CommonUtils;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoSocketOpenException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

@SuppressWarnings("unchecked")
@Component
@Scope("prototype")
public class ConfigurationReader {
	private static String applicationName = "";
	private static Long refreshTimeIntervalInMs = -1L;
	private static String connectionString = "";
	protected static Map<String, Map<String, String>> cachedData;

	private static final Logger log = LoggerFactory.getLogger(ConfigurationReader.class);

	public ConfigurationReader(String appName, String connectionStr, Long refreshTimeInMs) {
		log.info("inside ConfigurationReader constructor. appName = " + appName + "// connectionStr = " + connectionStr
				+ "// refreshTimeInMs = " + refreshTimeInMs != null ? refreshTimeInMs.toString() : null);
		this.applicationName = appName;
		this.connectionString = connectionStr;
		this.refreshTimeIntervalInMs = refreshTimeInMs;

		try {
			Method method = ConfigurationReader.class.getMethod("cacheDocuments", null);
			final Scheduled sch = method.getAnnotation(Scheduled.class);
			if(!refreshTimeIntervalInMs.equals(new Long(-1L))) {
				AnnotationHelper.changeAnnotationValue(sch, "fixedRate", refreshTimeIntervalInMs);
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Scheduled(fixedRate = 5000l)
	public void cacheDocuments() {
		if(connectionString.isEmpty()) {
			log.info("cacheDocuments method waiting for constructor parameters..");
			return;
		}
		log.info("cacheDocuments method run.");
		try {
			MongoClientURI mongoClientURI = new MongoClientURI(connectionString);
			MongoClient client = new MongoClient(mongoClientURI);
			MongoDatabase database = client.getDatabase(mongoClientURI.getDatabase());
			if (!database.getName().isEmpty()) {
				FindIterable<Document> findIterable = database.getCollection("configuration")
						.find(and(eq("applicationName", this.applicationName), eq("isActive", true)));
				cachedData = new HashMap<String, Map<String, String>>();
				Map<String, String> valueMap = null;

				for (Document document : findIterable) {
					valueMap = new HashMap<String, String>();
					valueMap.put("type", document.get("type").toString());
					valueMap.put("value", document.get("value").toString());
					cachedData.put(document.get("name").toString(), valueMap);
				}
			}
			client.close();
		} catch (MongoSocketOpenException e) {
			log.info("exception inside cacheDocuments");
			return;
		}
	}

	/**
	 * 
	 * @param key
	 * @return T , if not found than null
	 * @author kivi This method can return any data from 'configuration' collection.
	 *         Search with name, give return type.
	 */
	public <T> T GetValue(String key) {
		log.info("inside GetValue method. key = " + key);
		if (cachedData == null || cachedData.isEmpty()) {
			cacheDocuments();
		}
		Map<String, String> map = cachedData.get(key);

		if (map == null) {
			return null;
		}

		return (T) CommonUtils.objToTargetType(map.get("value").toString(), map.get("type").toString());
	}
}